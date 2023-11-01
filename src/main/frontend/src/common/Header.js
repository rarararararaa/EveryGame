import logo from '../logo.png';
import React from 'react';

import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';

const Header =()=>{
	
	return(
		<div className="EG-header">
			<img src={logo} className="EG-logo"/>
			<nav className="EG-nav">
				
			</nav>	
		</div>
	);
}

export default Header;