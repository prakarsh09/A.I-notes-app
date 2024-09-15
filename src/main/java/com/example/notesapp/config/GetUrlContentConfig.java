package com.example.notesapp.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

import com.example.notesapp.controller.GetUrlContent;

@Configuration
public class GetUrlContentConfig {
     @Bean
	@Description("This function retrieves text content of a webpage . It takes a single string argument which is url of webpage. The function returns string Htmlcontent which contains text content of webpage ")
	public Function<com.example.notesapp.controller.GetUrlContent.UrlRequest,com.example.notesapp.controller.GetUrlContent.UrltextResponse > GetUrlContent() {
		return new GetUrlContent();
	}

     @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
