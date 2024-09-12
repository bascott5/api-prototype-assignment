package com.csc340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class Controller {
    @GetMapping("/")
    public Object randomCardAPI() {
        try {
            String url = "https://api.scryfall.com/cards/random?q=set:blb";
            RestTemplate template = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String response = template.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);

            Card randomCard = new Card(root.get("name").asText(), root.get("id").asText(), root.get("image_uris").get("normal").asText());

            return randomCard;
        } catch (JsonProcessingException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
            return "<h1>error in /card</h1>";
        }
    }

    @GetMapping("/html")
    public Object randomCard() {
        try {
            String url = "https://api.scryfall.com/cards/random?q=set:blb";
            RestTemplate template = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String response = template.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);

            Card randomCard = new Card(root.get("name").asText(), root.get("id").asText(), root.get("image_uris").get("normal").asText());

            return String.format("""
                    <h1>%s</h1>
                    <p>%s</p>
                    <img src="%s" alt="Image of %s">
                    <p>Refresh the page to GET another random card!</p>
                    """, randomCard.getName(), randomCard.getID(), randomCard.getImageURL(), randomCard.getName());
        } catch (JsonProcessingException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
            return "<h1>error in /card</h1>";
        }
    }
}
