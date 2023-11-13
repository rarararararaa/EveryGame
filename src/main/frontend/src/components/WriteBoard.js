import React from 'react';
import {useForm} from 'react-hook-form';
import axios from 'axios';

const WriteBoard =()=>{
	
	const {register, handleSubmit, formState:{errors}} = useForm({mode: "onTouched"})
	
	const onSubmit=(data)=>{
		console.log(data);
		axios({
			url:'/api/WriteBoard',
			method:'post',
			data:{data}
		}).then(function(res){
			alert(res.data);
		})
	}	
	
	return(
		<div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<input type="text" placeholder="제목을 입력하세요" maxLength='300' className={errors.title && errors.title.message} 
				{...register("title",{
					required: 'denied_red'
				})}/>
				<select {...register("game_type",{
					required:"필수 선택사항입니다."
				})}>
					<option value="">게임 종류를 선택하세요.</option>
					<option value="1">프로세카</option>
					<option value="2">뱅드림</option>
					<option value="3">원신</option>
					<option value="4">기타</option>
				</select>
				<span>{errors.game_type && errors.game_type.message}</span>
				<select {...register("contry_type",{
					required:"필수 선택사항입니다."
				})}>
					<option value="">서버 국가를 선택하세요.</option>
					<option value="1">본섭</option>
					<option value="2">한국</option>
					<option value="3">글로벌</option>
					<option value="4">기타</option>
				</select>
				<input type="radio" value="1" {...register("type")} checked/>베테랑
				<input type="radio" value="2" {...register("type")}/>일반
				<input type="radio" value="3" {...register("type")}/>기타
				<span>{errors.contry_type && errors.contry_type.message}</span>
				<input type="number" placeholder={errors.team_num && errors.team_num.message}{...register("team_num",{
					required:'필수 입력사항입니다.',
					pattern:{
						value:/^[2-99]{1,2}$/,
						message:'최대 인원은 99명입니다.'
					}
				})}/>
				<input type="number" {...register("limit_time",{
					required:'필수 입력사항입니다.',
					pattern:{
						value:/^[1-8]{1}$/,
						message:"최소 1시간에서 최대 8시간입니다."
					}
				})}/>
				<textarea maxLength='1000' {...register("content")}></textarea>
				<input type="submit" value="완료"/>
			</form>
		</div>
		
	);
}

export default WriteBoard;
