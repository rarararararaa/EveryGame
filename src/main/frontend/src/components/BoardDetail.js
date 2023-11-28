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
			//console.log(res.data);
			//console.log(board.BOARD_NUM);
		}).catch(()=>{
			alert('네드워크 오류');
		})
	},[])
	
	const BoardCheck =(num)=>{
		axios({
			url:'api/loginCheck',
			post:'get',
			params:{board_num:num}
		}).then(function(res){
			if(res.headers.authorization == 'false' && res.headers.authorization != null){//res.headers[헤더이름(소문자)]
				alert('로그인 후 이용가능합니다.');
				sessionStorage.clear();
				navigate('/');
			}else if(!res.data){
				alert('수정 권한이 없습니다.')
				navigate('/');				
			}
		})
	}
	
	return(
		<div>
			<div className="board-Title">
			<ul>
				<li>{board.board_title}</li>
				<li>{board.board_reg_date}</li>
			</ul>
			</div>
			<div>
				{board.board_content}
			</div>
			<Link to="/updateBoard" state={board}><button onClick={()=>BoardCheck(board.board_num)}>수정</button></Link>
		</div>
		
	);
}

export default BoardDetail;