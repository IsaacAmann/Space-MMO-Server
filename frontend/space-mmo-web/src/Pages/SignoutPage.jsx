
import React from 'react'
import {useContext, useEffect} from 'react'
import {useState} from 'react'

import {LoginInfoContext} from "../App.jsx";
import Navbar from "../Components/Navbar/Navbar.jsx";

function SignoutPage()
{
    const loginInfo = useContext(LoginInfoContext);

useEffect(() =>
	{
	    sessionStorage.setItem("accessToken", null);
        sessionStorage.setItem("idToken", null);
        sessionStorage.setItem("refreshToken", null);
        loginInfo.setUsername(null);
        loginInfo.setUserRole(null);

	}, []);
	return(
		<>
		    <Navbar/>
			<p>You have been signed out </p>
		</>
	);
}


export default SignoutPage;