import React from 'react';
import axios from 'axios'

const Eg_Content=()=>{
	
	const BoardCheck =()=>{
		axios({
			url:'api/loginCheck'
		}).then(function(res){
			console.log(res.headers.authorization);
			if(res.data){
				alert('로그인 완료');
			} 
			if(res.headers.authorization == 'false' && res.headers.authorization != null){//res.headers[헤더이름(소문자)]
				alert('로그인 후 이용할 수 있습니다.');
			}
			
		})
	}
	
	return(
		<div>
			<div>
				<button onClick={BoardCheck}>글쓰기</button>
			</div>
		</div>
	);
}

export default Eg_Content;