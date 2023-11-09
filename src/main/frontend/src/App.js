import Header from './common/Header';
import Sidebar from './common/Sidebar';

import Eg_Content from './components/Eg_Content';
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
			<Eg_Content/>
		</div>
	</div>
    </div>
  );
}

export default App;
