import React from 'react'

import Header from './Header';
import Sidebar from './Sidebar';
import { Routes, Route} from 'react-router-dom';

import Eg_Content from '../components/Eg_Content';
import WriteBoard  from '../components/WriteBoard';
import BoardDetail from '../components/BoardDetail';
import UpdateBoard from '../components/UpdateBoard';
const Home=()=>{
	return(
	<div>
	<header>
		<Header/>
	</header>
	<div className="main-content">
		<div className="sidebar">
			<Sidebar/>
		</div>
		<div className="content">
		<Routes>
			<Route path="/" element={<Eg_Content/>}/>
			<Route path="/writeBoard" element={<WriteBoard/>}/>
			<Route path="/boardDetail" element={<BoardDetail/>}/>
			<Route path="/updateBoard" element={<UpdateBoard/>}/>
		</Routes>
		</div>
	</div>
	</div>
	);
}

export default Home;