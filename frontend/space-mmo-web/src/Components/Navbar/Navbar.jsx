
import React from 'react';
import {useContext} from 'react';
import {useState} from 'react';


import AppBar from "@mui/material/AppBar";
import Avatar from "@mui/material/Avatar";
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from "@mui/material/Toolbar";
import Box from "@mui/material/Box";
import Button from '@mui/material/Button';
import { jwtDecode } from "jwt-decode";
import Divider from '@mui/material/Divider';


import GitHubIcon from '@mui/icons-material/GitHub';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

import {LoginInfoContext} from "../../App.jsx"
import Backdrop from '@mui/material/Backdrop';
import Container from "@mui/material/Container"
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Drawer from '@mui/material/Drawer';
import LogoutIcon from '@mui/icons-material/Logout';
import { Link } from 'react-router-dom';
import Snackbar from '@mui/material/Snackbar';


import {signIn} from "../../AuthService.js";

import {url} from "../../App.jsx"
import {loginUrl} from "../../App.jsx"
import {signoutUrl} from "../../App.jsx"



function Navbar()
{
	const loginInfo = useContext(LoginInfoContext);
	const [loginUp, setLoginUp] = useState(false);
	const [profileDisplayUp, setProfileDisplayUp] = useState(false);
	const [loginNotify, setLoginNotify] = useState(false);

	const [username, setUsername] = useState(null);
	const [password, setPassword] = useState(null);
	const [loginError, setLoginError] = useState(null);

	const usernameRef = React.useRef(null);
    const passwordRef = React.useRef(null);


	function ProfileDisplayDrawer()
	{
		const drawerList = (
			<Box sx={{width: 250, bgcolor:'#020a07', minHeight: '100%'}} role="presentation" >
				<Typography variant="h4">Hello {loginInfo.username}!</Typography>
				<Typography variant="h5">User Role: {loginInfo.userRole}</Typography>
				<Divider />

				<Button component={Link} to={signoutUrl} variant="contained" >Sign Out</Button>
			</Box>
		);
		return(
			<Drawer anchor="right" open={profileDisplayUp} sx={{}} onClose={() => setProfileDisplayUp(false)}>
				{drawerList}
			</Drawer>
		);
	}


	function LoginButton()
	{
		if(loginInfo.token == null)
		{
			return(
				<>
					<Button component={Link} to={loginUrl} variant="contained" >Login</Button>
				</>
			);
		}
		else
		{
			return(
				<>
					<IconButton onClick={() => setProfileDisplayUp(true)} >
						<AccountCircleIcon fontSize="large"/>
					</IconButton>
				</>
			);
		}
	}

	return(
	<Box sx={{ flexGrow: 1 }}>
		<AppBar position="static">

			<Toolbar>
				<Typography component="div" sx={{mr: 2}}>
                    Kosmos
                </Typography>
				<Box sx={{flexGrow: 1}}>
					<Button component={Link} to="/" variant="contained" color="secondary" sx={{mx: 1}}>
						Home
					</Button>

				</Box>

				<LoginButton/>
			</Toolbar>
			<ProfileDisplayDrawer/>
		</AppBar>

	</Box>
	);
}

export default Navbar;
