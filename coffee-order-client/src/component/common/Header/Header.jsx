import React from "react"

import {NavLink} from "react-router-dom";

import "../../../App.css"

const Header = () => {
    return(
        <div className="header">
            <NavLink to="/" className="logo">
                좋은 커피
            </NavLink>
        </div>
    )
}

export default Header