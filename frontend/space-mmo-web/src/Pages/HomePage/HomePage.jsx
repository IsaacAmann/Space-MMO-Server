
import React from 'react'
import {useContext} from 'react'
import {useState} from 'react'

import {LoginInfoContext} from "../../App.jsx";
import Navbar from "../../Components/Navbar/Navbar.jsx";

function HomePage()
{

	return(
		<>
		    <Navbar/>
			<p>test </p>
			<iframe src="./godot/Godot-client.html" width="100%" height="600"> </iframe>
		</>
	);
}


export default HomePage;
