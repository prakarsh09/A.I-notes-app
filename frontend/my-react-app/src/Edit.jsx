import React from 'react'
import {  useLocation } from 'react-router-dom';
import { useState } from "react";
import axios from "axios";

const Edit = () => {

    const location=useLocation();
    console.log(location.state);
    const titleval=location.state.note.title;
    const contentval=location.state.note.content;
    console.log(titleval);

    const[title,settitle]=useState(titleval);
    const[content,setcontent]=useState(contentval);
   
   
    

    async function save(event){
    
        event.preventDefault();
        try{
            
            await axios.post("http://localhost:7070/saveNotes",  {
                title:title,
                content:content,
                id:location.state.note.id,
               
              
            },{
                 withCredentials:true
            } );
            alert("success");
        }
        catch(err){
            alert(err);
        }
    }

    return (
        <div className="">

            <div>
                <div className="Newnote" style={{ marginTop: 100 }} >


                    <form className="" >

                        <div >
                            <textarea
                                id="title" placeholder="Enter Title" style={{ height: "10vh", width: "50vw", marginLeft: 25, borderRadius: 5, }}

                                value={title}
                                onChange={(event)=>{
                                    settitle(event.target.value)
                                }} />
                        </div>
                        <div class="Content">
                            <textarea
                                id="content" placeholder="Enter Content" required style={{ height: "50vh", width: "50vw" }}

                                value={content}
                onChange={(event)=>{
                    setcontent(event.target.value)
                }} />
                        </div>


                        <div class="btt">
                            <button class="signupbutt" type="submit" onClick={save}>Save</button>
                        </div>

                    </form>
                </div>
            </div>

        </div>

    );
}

export default Edit
