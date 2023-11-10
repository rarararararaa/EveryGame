import React, {useState} from 'react';
import Register from '../components/Register';
import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';
import {useForm} from 'react-hook-form';
import axios from 'axios'

const SideBar =()=>{
	const {register, handleSubmit, formState:{errors}} = useForm({mode: "onSubmit"})
	let session = window.sessionStorage.getItem('memInfo');
	console.log('세션:'+session);
	const [memInfo, setMemInfo] = useState("");
	const onSubmit =(data)=>{
		let {email, passwd} = data;
		axios({
			url:'/api/login',
			method:'post',
			data:{
				email:email,
				passwd:passwd
			},
		}).then(function(response){
			if(!response.data){
				alert('이메일 또는 비밀번호가 틀렸습니다.');
			}else{
				console.log(decodeURI(response.headers.memberinfo));
				sessionStorage.setItem('memInfo',decodeURI(response.headers.memberinfo));
				setMemInfo(decodeURI(response.headers.memberinfo));
			}
		})
		
	}
	const onClick=()=>{
		setMemInfo(null);
		sessionStorage.clear();
		axios.delete('/api/logout');
		window.location.href="/";
	}
	
	return(
		<div className="EG-sidebar">
		<BrowserRouter>
		{session === null ? //로그인 하지 않았을 경우
		<div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<input type="email" name="email" placeholder="example@test.com" {...register("email",{
					required:"이메일을 입력해주세요."
				})}/>
				<p>{errors.email && errors.email.message}</p>
				<input type="password" name="passwd" placeholder="비밀번호" {...register("passwd",{
					required:"비밀번호를 입력해주세요."
				})}/>
				<p>{errors.passwd && errors.passwd.message}</p>
				<input type="submit" value="로그인"/>
			</form>
			<Link to="/register">회원가입</Link>
				<Routes>
					<Route path="/register" element={<Register/>}/>
				</Routes>
		</div>
		: //로그인할 경우
		<div>
		 	{session}님<br/>
			로그인 되었습니다.
			<button onClick={onClick}>로그아웃</button>
		</div>
		}
			</BrowserRouter>
		</div>
	);
}

export default SideBar;