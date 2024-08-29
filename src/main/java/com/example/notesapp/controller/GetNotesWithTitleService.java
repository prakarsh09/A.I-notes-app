package com.example.notesapp.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import com.example.notesapp.entity.JwtRequest;
import com.example.notesapp.entity.JwtResponse;
import com.example.notesapp.entity.Notes;
import com.example.notesapp.repo.NotesRepository;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;


@Service
public class GetNotesWithTitleService implements Function<com.example.notesapp.controller.GetNotesWithTitleService.Request,com.example.notesapp.controller.GetNotesWithTitleService.Response>  {
    @Autowired
       NotesRepository notesRepository;
       
       @JsonClassDescription("Get notes with title api request") // (2) function description
        public record Request(@JsonProperty(required = true,value = "title")
                            @JsonPropertyDescription("the title of note the user wants to retrieve") String title) {}
        public record Response(String title,String content) {}
        @Override
        public Response apply(Request t) {
            Authentication newauth=SecurityContextHolder.getContext().getAuthentication();
        DefaultOAuth2User principal=(DefaultOAuth2User)newauth.getPrincipal();
          Map<String , Object> attributes=principal.getAttributes();
          String email= attributes.getOrDefault("email", "").toString();
            List<Notes> n =notesRepository.findNotesByUserandtitle(email, t.title);
            Notes note=n.get(0);
             Response res=new Response(note.getTitle(),note.getContent());
            return res;
            
        }
    
       


       
    }
       
      
    

