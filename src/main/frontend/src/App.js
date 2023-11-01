import Header from './common/Header';

import Sidebar from './common/Sidebar';

import './App.css';
import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';
import React from 'react';
function App() {
  return (
    <div className="App">
	<header>
		<Header/>
	</header>
	<div className="main-content">
		<div className="sidebar">
			<Sidebar/>
		</div>
		<div className="content">
			멀티룸 리스트
		</div>
	</div>
    </div>
  );
}

export default App;
