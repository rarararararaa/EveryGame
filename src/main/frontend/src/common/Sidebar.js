import React from 'react';
import Register from '../components/Register';
import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';
import {useForm} from 'react-hook-form';
import axios from 'axios'

const SideBar =()=>{
	
	const {register, handleSubmit, formState:{errors}} = useForm({mode: "onSubmit"})
	
	const onSubmit =(data)=>{
		let {email, passwd} = data;
		axios({
			url:'/api/login',
			method:'post',
			data:{
				email:email,
				passwd:passwd
			},
			baseURL:'http://localhost:8080'
		}).then(function(response){
			if(response.data == true){
				
				document.location.href="/";
			}else{
				alert('이메일 또는 비밀번호가 틀렸습니다.');
			}
		})
		
	}
	
	return(
		<div className="EG-sidebar">
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
			<BrowserRouter>
				<Link to="/register">회원가입</Link>
				<Routes>
					<Route path="/register" element={<Register/>}/>
				</Routes>
			</BrowserRouter>
		</div>
	);
}

export default SideBar;