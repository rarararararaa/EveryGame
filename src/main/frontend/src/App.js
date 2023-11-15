import './App.css';
import {BrowserRouter, Routes, Route, Link, useNavigate} from 'react-router-dom';
import React from 'react';
import Home from './common/Home';
function App() {
  return (
	<BrowserRouter>
		<div className="App">
			<Home/>
		</div>
	</BrowserRouter>
 );
}

export default App;
