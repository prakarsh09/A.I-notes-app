package com.example.notesapp.controller;



import java.util.List;
import java.util.Map;



import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.notesapp.entity.Employee;

import com.example.notesapp.entity.Notes;
import com.example.notesapp.entity.delid;
import com.example.notesapp.repo.EmpRepo;
import com.example.notesapp.repo.NotesRepository;

import org.springframework.web.bind.annotation.RequestParam;





@RestController

public class HomeController {
@Autowired    
private EmpRepo empRepo;
@Autowired
NotesRepository notesRepository;



 public ChatClient chatClient;
public HomeController(ChatClient.Builder builder){
this.chatClient=builder.build();
}



    

    @GetMapping("/viewNotes")
   
    public List<Notes> viewnotes(){
        Authentication newauth=SecurityContextHolder.getContext().getAuthentication();
        DefaultOAuth2User principal=(DefaultOAuth2User)newauth.getPrincipal();
          Map<String , Object> attributes=principal.getAttributes();
          String email= attributes.getOrDefault("email", "").toString();
          Employee user=empRepo.findByEmail(email);
        List<Notes> notes=notesRepository.findNotesByUser(user.getId());
      
      
        return notes;
    }

    @PostMapping("/saveNotes")
    @CachePut(value="note",key = "#notes.id")
    public String saveNotes(@RequestBody Notes notes){
        Authentication newauth=SecurityContextHolder.getContext().getAuthentication();
          DefaultOAuth2User principal=(DefaultOAuth2User)newauth.getPrincipal();
            Map<String , Object> attributes=principal.getAttributes();
            String email= attributes.getOrDefault("email", "").toString();
        Employee e=empRepo.findByEmail(email);
       notes.setEmpl(e);
       if(e!=null){
       notesRepository.save(notes);
       return"success";
       }
       return"not success";

}
@PostMapping("/delete")
@CacheEvict(value="note",key = "#id")
public String getMethodName(@RequestBody delid id) {
  
   notesRepository.deleteById(id.getId());
    return "success";
}

@GetMapping("/ask")
public String askGpt(@RequestParam("prompt") String prompt){
    SystemMessage systemMessage= new SystemMessage("You are a helpful assistant answering questions on notes made by user");
    UserMessage userMessage=new UserMessage(prompt);
    return this.chatClient.prompt()
    .system(systemMessage.getContent())
    .user(userMessage.getContent())
    .functions("GetNotesWithTitle")
    .call()
    .content();
}





 
 
    
    
}
