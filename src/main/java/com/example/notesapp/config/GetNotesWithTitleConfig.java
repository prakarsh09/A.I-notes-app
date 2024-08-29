package com.example.notesapp.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;


import com.example.notesapp.controller.GetNotesWithTitleService;
import com.fasterxml.jackson.annotation.JsonClassDescription;

@Configuration
public class GetNotesWithTitleConfig {
	
	

    @Bean
	@Description("This function retrieves a note based on title. It takes a single string argument which is title of note. The function returns two strings title which is same as input title and content which is string content of note")
	public Function<GetNotesWithTitleService.Request,GetNotesWithTitleService.Response > GetNotesWithTitle() {
		return new GetNotesWithTitleService();
	}

}

