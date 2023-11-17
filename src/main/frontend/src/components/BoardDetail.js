import React,{useEffect, useState} from 'react';
import {useLocation, Link} from 'react-router-dom';
import axios from 'axios';

const BoardDetail=()=>{
	const location = useLocation();
	const num = location.state;
	//console.log(num);
	const [board, setBoard] = useState({});
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
			<Link to="/updateBoard" state={board}><button>수정</button></Link>
		</div>
		
	);
}

export default BoardDetail;