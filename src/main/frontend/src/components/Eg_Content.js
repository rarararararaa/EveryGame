import React, {useState} from 'react';
import axios from 'axios'

import BoardList from './BoarLlist';

import { Routes, Route, useNavigate} from 'react-router-dom';
const Eg_Content=()=>{
	
	const memInfo = sessionStorage.getItem('memInfo');
	
	const navigate = useNavigate();
	
	const BoardCheck =()=>{
		axios({
			url:'api/loginCheck'
		}).then(function(res){
			console.log(res.headers.authorization);
			if(res.data){
				navigate('/writeBoard')
			} 
			if(res.headers.authorization == 'false' && res.headers.authorization != null){//res.headers[헤더이름(소문자)]
				alert('로그인 후 이용할 수 있습니다.');
				sessionStorage.clear();
				navigate('/');
			}
			
		})
	}
	
	return(
		<div>
			<button onClick={BoardCheck}>글쓰기</button>
			<BoardList/>
		</div>
	);
}

export default Eg_Content;