import { useState } from "react";
import axios from "axios";

function Register(){
   
    const[title,settitle]=useState("");
    const[content,setcontent]=useState("");
   

async function save(event){
    
    event.preventDefault();
    try{
        
        await axios.post("http://localhost:7070/saveNotes",  {
            title:title,
            content:content,
          
        },{
             withCredentials:true
        } );
        alert("success");
    }
    catch(err){
        alert(err);
    }
}



return(
    <div className="">
        
    <div>
<div className=""style={{marginTop:100}} >
  
    
        <form className="col-sm-10" >
          
            <div >
               <textarea 
                 id="title" placeholder="Enter Title" style={{height: "10vh", width: "50vw",marginLeft:25,borderRadius:5,}}
                value={title}
                onChange={(event)=>{
                    settitle(event.target.value)
                }}/>
           </div>
           <div class="Content">
            <textarea 
                 id="content" placeholder="Enter Content" required style={{height: "50vh", width: "50vw"}}
                value={content}
                onChange={(event)=>{
                    setcontent(event.target.value)
                }}/>
                 
            <div class="btt">
                <button class="signupbutt" type="submit" onClick={save} >Save</button>
            </div>
                 
           </div>
        
          

        </form>
    </div>
</div>

    </div>

);



}
export default Register