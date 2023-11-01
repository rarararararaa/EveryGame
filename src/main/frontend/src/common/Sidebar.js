import React from 'react';
import Register from '../components/Register';
import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';

const SideBar =()=>{
	
	return(
		<div className="EG-sidebar">
			<BrowserRouter>
				<Link to="/register">회원가입</Link>
				<Routes>
					<Route path="/register" element={<Register/>}/>
				</Routes>
			</BrowserRouter>
		</div>
	);
}

export default SideBar;