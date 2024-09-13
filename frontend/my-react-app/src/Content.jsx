import React from 'react'
import { useState, useEffect } from "react"
import axios from "axios"
import { useNavigate } from 'react-router-dom';


import Edit from './Edit';



const Content = () => {
    const [notes, setNotes] = useState([]);
    const navigate = useNavigate()

    async function del(id) {


        try {
            const delid = id;
            await axios.post("http://localhost:7070/delete", {
                id: delid
            }, {
                withCredentials: true
            });
            window.location.reload();




        }
        catch (err) {
            alert(err);
        }
    }

    function sendtoedit(notetoedit) {
        navigate("/editnote", { state: { note: notetoedit } })
    }


    useEffect(() => {
        (async () => {
            try {
                const response = await axios.get("http://localhost:7070/viewNotes",
                    {
                        withCredentials: true
                    })
                    
                console.log(response.data)
                setNotes(response.data)
            } catch (error) {
 // Check if the error is due to a redirect (which indicates the user is not authenticated)
 
    // Redirect the user to the login page (Google OAuth2 flow)
    window.location.href = "http://localhost:7070";

            }
        })()

    }, []);


    //   console.log(notes)


    return (
        <div className='Viewnote'>



            {notes.map((notes) => (

                <div className="note" key={notes._id} >
                    <div className="card-header">{notes.updateDate}

                    </div>
                    <div className="notesbutton">

                        <button className="edit" onClick={() => sendtoedit(notes)} >edit</button>

                        <button className="delete" type="submit" onClick={() => del(notes.id)}>delete</button>
                    </div>
                    <div className="card-body">
                        <h5 className="card-title">{notes.title}</h5>
                        <p className="card-text">{notes.content}</p>
                    </div>
                </div>

            ))}

        </div>
    )
}

export default Content
