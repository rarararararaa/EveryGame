import React from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useLocation} from 'react-router-dom';
import axios from 'axios';

const UpdateBoard =()=>{
	
	const {register, handleSubmit, formState:{errors}} = useForm({mode: "onTouched"})
	const navigate = useNavigate();
	const board = useLocation().state;
	//console.log(board);
	const onSubmit=(data)=>{
		//console.log(data);
		axios({
			url:'/api/UpdateBoard',
			method:'post',
			data:data
		}).then(function(res){
			if(res.data)
				alert('수정되었습니다.');
			else if(!res.data)
				alert('수정 권한이 없습니다.')
			navigate('/boardDetail',{state:board.board_num});
		})
	}	
	
	return(
		<div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<input type="text" placeholder="제목을 입력하세요" defaultValue={board.board_title} maxLength='300' className={errors.title && errors.title.message} 
				{...register("board_title",{
					required: 'denied_red'
				})}/>
				<input type="hidden" value={board.board_num} {...register("board_num")}/>
				<input type="hidden" value={board.mem_num} {...register("mem_num")}/>
				<select defaultValue={board.board_game_type} {...register("board_game_type",{
					required:"필수 선택사항입니다."
				})}>
					<option value="">게임 종류를 선택하세요.</option>
					<option value="1">프로세카</option>
					<option value="2">뱅드림</option>
					<option value="3">원신</option>
					<option value="4">기타</option>
				</select>
				<span>{errors.game_type && errors.game_type.message}</span>
				<select defaultValue={board.board_country_type} {...register("board_country_type",{
					required:"필수 선택사항입니다."
				})}>
					<option value="">서버 국가를 선택하세요.</option>
					<option value="1">본섭</option>
					<option value="2">한국</option>
					<option value="3">글로벌</option>
					<option value="4">기타</option>
				</select>
				<input type="radio" value="1" {...register("board_type")} defaultChecked={board.board_type === 1 ? true : false}/>베테랑
				<input type="radio" value="2" {...register("board_type")} defaultChecked={board.board_type === 2 ? true : false}/>일반
				<input type="radio" value="3" {...register("board_type")} defaultChecked={board.board_type === 3 ? true : false}/>기타
				<span>{errors.board_type && errors.board_type.message}</span>
				<label>인원</label>
				<input type="number" placeholder={errors.team_num && errors.team_num.message}
				defaultValue={board.board_team_num} {...register("board_team_num",{
					required:'필수 입력사항입니다.',
					pattern:{
						value:/^[1-99]{1,2}$/,
						message:'최대 인원은 99명입니다.'
					}
				})}/>
				<label>멀티시간</label>
				<input type="number" defaultValue={board.board_time} {...register("board_time",{
					required:'필수 입력사항입니다.',
					pattern:{
						value:/^[1-8]{1}$/,
						message:"최소 1시간에서 최대 8시간입니다."
					}
				})}/>
				<span>{errors.board_time && errors.board_time.message}</span>
				<textarea maxLength='1000' defaultValue={board.board_content} {...register("board_content")}></textarea>
				<input type="submit" value="완료"/>
			</form>
		</div>
		
	);
}

export default UpdateBoard;
