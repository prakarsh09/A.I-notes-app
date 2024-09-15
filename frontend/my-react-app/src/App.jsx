
import Content from "./Content.jsx";
import Edit from "./Edit.jsx";
import Home from "./Home.jsx";
import Register from "./Register.jsx";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Chat from "./Chat.jsx";
function App() {
   
 return(
  <>
 <BrowserRouter>
        <Home />
        
          <Routes>
          {/* <Route path="/" element={<Home />} /> */}
            <Route path="/addnote" element={<Register />} />
            <Route path="/viewnotes" element={<Content />} /> 
            <Route path="/editnote" element={<Edit />} /> 
          </Routes>
        
      </BrowserRouter>
      <Chat/>
  </>
 );
}

export default App;


