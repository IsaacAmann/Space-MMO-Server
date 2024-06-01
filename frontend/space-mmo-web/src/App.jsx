import {createContext, useContext, useState, useEffect} from 'react'
import CssBaseline from '@mui/material/CssBaseline';

import ReactDOM from "react-dom/client"
import { BrowserRouter, Routes, Route, useSearchParams } from "react-router-dom"
import {colors, ThemeProvider} from "@mui/material"
import {createTheme} from "@mui/material/styles"
import { jwtDecode } from "jwt-decode";

import {getToken} from "./AuthService.js";

import './App.css'

import HomePage from "./Pages/HomePage/HomePage.jsx";

export const LoginInfoContext = createContext({token: null, setToken: () => {}});



export const mainTheme = createTheme({
	palette: {
    primary: {
      main: '#96e6bd',
    },
    secondary: {
      main: '#5e1f86',
      dark: '#5e1f86',
    },
    divider: '#9b77ff',
    text: {
      primary: '#e2f8ec',
      disabled: '#0a2918',
      secondary: '#145230'
    },
    background: {
      default: '#020a07',
      paper: '#11553c'
    },
    
  }
}
);


function App() {
	const [token, setToken] = useState(null);
	const [username, setUsername] = useState(null);
	const [userRole, setUserRole] = useState(null);
	const loginInfo = useContext(LoginInfoContext);

	//Check for token
	useEffect(() =>
	{
	    /*
		var currentToken = APICallContainer.getLoginInfo();
		if(currentToken != null)
		{
			//Decode token and set fields
			var decodedToken = jwtDecode(currentToken);
			setUsername(decodedToken.username);
			setToken(currentToken);
			setUserRole(decodedToken.userRole);
		}
		*/
		var currentToken = sessionStorage.getItem("accessToken");
		var queryString = window.location.search;
		var urlParams = new URLSearchParams(queryString);
		var code = urlParams.get("code");
		if(code != null)
		{
            //Request JWT from backend
            getToken(code).then(
                function(value)
                {
                    console.log(value);
                    if(value.loggedIn == true)
                    {
                        sessionStorage.setItem("accessToken", value.accessToken);
                        sessionStorage.setItem("idToken", value.idToken);
                        sessionStorage.setItem("refreshToken", value.refreshToken);
                    }
                    window.location.replace("http://localhost:5173");
                }
            );
        }
        //Check for existing token in sessionStorage
		else
		{

        }

	}, []);
	
	return (
	<ThemeProvider theme={mainTheme}>
	<CssBaseline/>
		<LoginInfoContext.Provider value={{token: token, setToken: setToken, username: username, setUsername: setUsername, userRole: userRole, setUserRole: setUserRole}}>
			<BrowserRouter>
				<Routes>
					<Route path="/">
						<Route index element={<HomePage />} />

					</Route>
				</Routes>
			</BrowserRouter>
		</LoginInfoContext.Provider>
	</ThemeProvider>
  );
}

export default App
