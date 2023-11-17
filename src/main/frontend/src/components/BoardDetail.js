import React,{useEffect, useState} from 'react';
import {useLocation, Link, useNavigate} from 'react-router-dom';
import axios from 'axios';

const BoardDetail=()=>{
	const location = useLocation();
	const navigate = useNavigate();
	const num = location.state;
	//console.log(num);
	const [board, setBoard] = useState({});
	//게시글 상세 정보
	useEffect(()=>{
		axios.get('/api/BoardDetail',{params:{
			num:num
		}}).then((res)=>{
			setBoard(res.data);
			console.log(res.data);
			//console.log(board.BOARD_NUM);
		}).catch(()=>{
			alert('네드워크 오류');
		})
	},[])
	
	const BoardCheck =()=>{
		axios({
			url:'api/loginCheck'
		}).then(function(res){
			console.log(res.headers.authorization);
			if(res.headers.authorization == 'false' && res.headers.authorization != null){//res.headers[헤더이름(소문자)]
				alert('로그인 후 이용할 수 있습니다.');
				sessionStorage.clear();
				navigate('/');
			}
			
		})
	}
	
	return(
		<div>
			<div className="board-Title">
			<ul>
				<li>{board.BOARD_TITLE}</li>
				<li>{board.BOARD_REG_DATE}</li>
			</ul>
			</div>
			<div>
				{board.BOARD_CONTENT}
			</div>
			<Link to="/updateBoard" state={board}><button onClick={BoardCheck}>수정</button></Link>
		</div>
		
	);
}

export default BoardDetail;