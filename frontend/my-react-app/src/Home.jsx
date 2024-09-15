import React from 'react'
import { Link } from 'react-router-dom'


const Home = () => {
  return (
    <div className='home'>
     <p className='appname'>Notes App</p>
     <Link to="/addnote"><button id='createbut'>Create </button></Link>
     <Link to="/viewnotes"><button id='viewbut'><p class="text-center">View</p></button></Link>
     
     <div className='logout'><a href='http://localhost:7070/logout'><button className='logoutbut'>Logout</button></a></div>
    </div>
  )
}

export default Home
