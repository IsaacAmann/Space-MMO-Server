
import React from 'react'
import {useContext} from 'react'
import {useState} from 'react'

import {LoginInfoContext} from "../../App.jsx";
import Navbar from "../../Components/Navbar/Navbar.jsx";

import {url} from "../../App.jsx"

import {loginUrl} from "../../App.jsx"
import {signoutUrl} from "../../App.jsx"

function HomePage() {
    const loginInfo = useContext(LoginInfoContext);

    function GameWindow() {
        var token = sessionStorage.getItem("accessToken")
        console.log("TOKEN: " + token)
        if (token !== "null") {
            console.log("f")
            return (
                <iframe
                    src="./godot/Godot-client.html"
                    style={{
                        width: "100%",
                        height: "100%",
                        border: "none",
                        position: "absolute",
                        top: 0,
                        left: 0,
                    }}
                >
                </iframe>
            );
        } else {
            return null;
        }
    }

    return (
        <>
            <Navbar />
            
            <div style={{ position: "absolute", width: "100%", height: "94vh" }}>
                <GameWindow />
            </div>
            <p>Kosmos Copyright 2024</p>
        </>
    );
}

export default HomePage;