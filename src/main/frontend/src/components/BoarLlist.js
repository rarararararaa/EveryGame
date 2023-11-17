import React,{useState,useEffect} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';

const BoardList =()=>{
	//시간 설정[게시글 등록 시간]
	const detailDate=(a)=>{
		const milliSeconds = new Date();
		const dbDate = new Date(a);
		//milliSeconds.setHours(milliSeconds.getHours()-a);
		const seconds = (milliSeconds-dbDate) /1000;
		if(seconds < 60) return '방금 전';
		const minutes = seconds/60;
		if(minutes < 60) return Math.floor(minutes)+'분 전';
		const hours = minutes / 60;
		//console.log(hours);
		if(hours < 24) return  Math.floor(hours)+'시간 전';
		else return '종료';
	}

	const [list, setList] = useState([]);
	
	useEffect(()=>{
		axios.get('/api/BoardList').then((res)=>{
			setList(res.data);
			console.log(res.data);
		})		
	},[])//deps(=[])가 없으면 컴포넌트가 랜더링 될 때마다 실행, []를 표시하면 처음 랜더링 될때만 실행
	//[]를 쓰지 않으면 부한 루프가 되는 이유는 useState때문 state를 수정하면 컴포넌트가 다시 랜더링 된다.
	return(
		<div className="Rooms">
			{
				list.map((i)=>{
					return(
						<div className="Room">
							<div className="Room-title">
								<Link to="/boardDetail" state={i.BOARD_NUM}> {i.BOARD_TITLE} </Link>
								{i.BOARD_TEAM_NOW}/{i.BOARD_TEAM_NUM}
							</div>
							<div className="Room-content">
								{i.BOARD_CONTENT}
								{detailDate(i.BOARD_REG_DATE)}
							</div>
						</div>						
					)
				})
			}
		</div>		
	);
}
export default BoardList;