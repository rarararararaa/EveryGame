import React from 'react';
import {useForm} from 'react-hook-form';
import axios from 'axios';

const Register =()=>{
	
	const {register, handleSubmit, formState:{errors}} = useForm({mode:"onBlur"});
	
	const onSubmit=(data)=>{
		let {email, passwd} = data;
		axios({
			url:'/api/register',
			method:'post',
			data:{
				email:email,
				passwd:passwd
			},
			baseURL:'http://localhost:8080'
		}).then(function(response){
			alert(response.data);
			document.location.href="/";
		})
	}
	
	return(
		<div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<input type="email" name="email" placeholder="example@test.com" {...register("email",{
					required: "이메일은 필수 항목입니다."
				})}/>
				<p>{errors.email && errors.email.message}</p>
				<input type="password" name="passwd" placeholder="비밀번호" {...register("passwd",{
					required: "비밀번호는 필수 항목입니다.",
					pattern: {
						value:/^(?=.*[A-Za-z])(?=.*[!@#$%^&*+=-_])(?=.*[0-9]).{8,30}$/,
						message:"숫자&영문자&특수문자 포함 8자이상 30자 이하로 입력해주세요."
					}
				})}/>
				<p>{errors.passwd && errors.passwd.message}</p>
				<input type="submit" value="회원가입"/>
			</form>		
		</div>
	);
}

export default Register;