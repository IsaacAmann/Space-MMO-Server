
import React from 'react'
import {useContext} from 'react'
import {useState} from 'react'

import {LoginInfoContext} from "../../App.jsx";
import Navbar from "../../Components/Navbar/Navbar.jsx";

import {url} from "../../App.jsx"

import {loginUrl} from "../../App.jsx"
import {signoutUrl} from "../../App.jsx"

function HomePage()
{
    const loginInfo = useContext(LoginInfoContext);

    function GameWindow()
    {
        var token = sessionStorage.getItem("accessToken")
        console.log("TOKEN: " + token)
        if(token != "null")
        {
            console.log("f")
            return (<iframe src="./godot/Godot-client.html" width="100%" height="600"> </iframe>)
        }
        else
        {
            return null;
        }
    }

	return(
		<>
		    <Navbar/>
		    <GameWindow/>
			<p>test </p>
		</>
	);
}


export default HomePage;
