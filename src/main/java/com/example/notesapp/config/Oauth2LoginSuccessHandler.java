package com.example.notesapp.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.notesapp.entity.Employee;
import com.example.notesapp.repo.EmpRepo;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

@Autowired    
private EmpRepo empRepo;


    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException{
        OAuth2AuthenticationToken oauthtoken=(OAuth2AuthenticationToken) authentication;
      
            DefaultOAuth2User principal=(DefaultOAuth2User)authentication.getPrincipal();
            Map<String , Object> attributes=principal.getAttributes();
            String email= attributes.getOrDefault("email", "").toString();
            String name= attributes.getOrDefault("name", "").toString();
            Employee emp= empRepo.findByEmail(email);
            if(emp==null){
                Employee user= new Employee();
                user.setEmail(email);
                user.setName(name);
                empRepo.save(user);
                DefaultOAuth2User newUser=new DefaultOAuth2User(null, attributes, "name");
                Authentication securityAuth= new OAuth2AuthenticationToken(newUser, null,oauthtoken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            }else{
                DefaultOAuth2User newUser=new DefaultOAuth2User(null, attributes, "name");
                Authentication securityAuth= new OAuth2AuthenticationToken(newUser, null,oauthtoken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            }
            this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:8080/viewnotes");
        super.onAuthenticationSuccess(request, response, authentication);
      
    }
}
