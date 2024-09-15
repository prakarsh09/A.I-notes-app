package com.example.notesapp.controller;

import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@Service
public class GetUrlContent implements Function<com.example.notesapp.controller.GetUrlContent.UrlRequest,com.example.notesapp.controller.GetUrlContent.UrltextResponse> {
    
    @Autowired
    private RestTemplate restTemplate;


    public record UrlRequest(@JsonProperty(required = true,value = "Url")
                            @JsonPropertyDescription("the url of webpage provided by user") String Url) {}
        public record UrltextResponse(String Htmlcontent) {}

    @Override
    public UrltextResponse apply(UrlRequest U) {
        
      
            // Fetch the webpage as a string using RestTemplate
            String htmlContent = restTemplate.getForObject(U.Url, String.class);
    
            // Parse the HTML using Jsoup
            Document doc = Jsoup.parse(htmlContent);
    
            // Extract the text content from the webpage, e.g., from paragraphs
            Elements paragraphs = doc.select("p");  // Extract all <p> tags
    
            // Concatenate all the text from the paragraphs
            StringBuilder textContent = new StringBuilder();
            for (org.jsoup.nodes.Element paragraph : paragraphs) {
                textContent.append(paragraph.text()).append("\n");
            }
            UrltextResponse res= new UrltextResponse(textContent.toString());
            return res;  // Return the extracted text
        }
    }

