package com.example.notesapp.controller;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
       
      
        return getCachedNotes(user);
    }
    @Cacheable(key = "#user.id", value = "notes")
public List<Notes> getCachedNotes(Employee user) {
    // Fetch and sort the user's notes
    List<Notes> notes = notesRepository.findNotesByUser(user.getId());
    notes.sort((note1, note2) -> Integer.compare(note2.getId(), note1.getId()));
    
    return notes;  // This will be cached with the user's ID as the key
}

    @PostMapping("/saveNotes")
   
    public String saveNotes(@RequestBody Notes notes){
        Authentication newauth=SecurityContextHolder.getContext().getAuthentication();
          DefaultOAuth2User principal=(DefaultOAuth2User)newauth.getPrincipal();
            Map<String , Object> attributes=principal.getAttributes();
            String email= attributes.getOrDefault("email", "").toString();
        Employee user=empRepo.findByEmail(email);
      return save(notes, user);

}
@CacheEvict(key = "#user.id", value = "notes")
public String save(Notes note,Employee user){
  note.setEmpl(user);
  if(user!=null){
  notesRepository.save(note);
  return"success";
  }
  return"not success";
}
@PostMapping("/delete")
public String deletenotebynoteid(@RequestBody delid id){
  Authentication newauth=SecurityContextHolder.getContext().getAuthentication();
  DefaultOAuth2User principal=(DefaultOAuth2User)newauth.getPrincipal();
    Map<String , Object> attributes=principal.getAttributes();
    String email= attributes.getOrDefault("email", "").toString();
    Employee user=empRepo.findByEmail(email);
    return delete(id, user);
}
@CacheEvict(key ="#user.id",value = "notes")
public String delete(delid id, Employee user) {
  
   notesRepository.deleteById(id.getId());
    return "success";
}

@GetMapping("/ask")
public String askGpt(@RequestParam("prompt") String prompt){
    SystemMessage systemMessage= new SystemMessage("You are a helpful assistant with name Friday answering questions on notes made by user");
    UserMessage userMessage=new UserMessage(prompt);
    try{
    return this.chatClient.prompt()
    .system(systemMessage.getContent())
    .user(userMessage.getContent())
    .functions("GetNotesWithTitle","GetUrlContent")
    .call()
    .content();}
    catch(Exception e){
      return "Unable to understand";
    }
}





 
    
    
}
