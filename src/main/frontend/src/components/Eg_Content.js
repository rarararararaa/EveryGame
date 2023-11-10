import React, {useState} from 'react';
import axios from 'axios'
import WriteBoard  from './WriteBoard';
const Eg_Content=()=>{
	
	const memInfo = sessionStorage.getItem('memInfo');
	
	const [check, setCheck] = useState(0);
	
	const BoardCheck =()=>{
		axios({
			url:'api/loginCheck'
		}).then(function(res){
			console.log(res.headers.authorization);
			if(res.data){
				alert('글 쓰기');
				setCheck(1);
			} 
			if(res.headers.authorization == 'false' && res.headers.authorization != null){//res.headers[헤더이름(소문자)]
				alert('로그인 후 이용할 수 있습니다.');
				setCheck(0);
			}
			
		})
	}
	
	return(
		<div>
		{memInfo === null || check === 0 ? 
			<div>
				<button onClick={BoardCheck}>글쓰기</button>
			</div>
		 :
			<div>
				<WriteBoard/>
			</div>
		}
		</div>
	);
}

export default Eg_Content;