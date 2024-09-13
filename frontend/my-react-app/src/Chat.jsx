import React, { useState } from 'react'
import axios from "axios"
import '@chatscope/chat-ui-kit-styles/dist/default/styles.min.css';
import {MainContainer,ChatContainer,MessageList,Message,MessageInput,TypingIndicator} from "@chatscope/chat-ui-kit-react"
const Chat = () => {

    const[typing,settyping]=useState(false)

const[messages,setmessages]=useState([
    {
        message:"Hello i am chatgpt",
        sender:"chatGPT",
        direction:"incoming"
       
    }
])

const handleSend= async(message)=>{
    const newMessage={
       message:message ,
       sender:"user",
       direction:"outgoing"
    }
    const newMessages=[...messages,newMessage];

    setmessages(newMessages);

    settyping(true);  
   
    await requestchatGPT(newMessage,newMessages); 
        
    
}

async function requestchatGPT(newMessage,chatMessages){

    
    const response = await axios.get(`http://localhost:7070/ask?prompt=${newMessage.message}`,
        {
            withCredentials: true
        })
    console.log(response.data)
    setmessages([
       ...chatMessages,{
            message:response.data,
            sender:"chatGPT",
            direction:"incoming"
        }
    ])
    settyping(false);
}


  return (
    <div className='chat'style={{display:"grid",alignItems:"center", justifyContent:"center"}}>
      <div style={{position:"relative", height:"800px", width:"700px",paddingTop:"100px"}}>
        <MainContainer style={{background:"black"}}>
            <ChatContainer style={{backgroundColor:"black"}}>
                <MessageList
                typingIndicator={typing?<TypingIndicator content="ChatGPT is typing"></TypingIndicator>:null}
                >
                {messages.map((message,i)=>{
                   return <Message key={i} model={message}></Message>
                })}
                </MessageList>
                <MessageInput placeholder='Type here' onSend={handleSend}>
                </MessageInput>
            </ChatContainer>
        </MainContainer>
        </div>  
      
    </div>
  )
}

export default Chat
