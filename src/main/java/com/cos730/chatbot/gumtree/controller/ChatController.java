package com.cos730.chatbot.gumtree.controller;

import com.cos730.chatbot.gumtree.entity.Agent;
import com.cos730.chatbot.gumtree.model.RequestDto;
import com.cos730.chatbot.gumtree.service.ChatService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Value("${nlp-url}")
    private String nlpUrl;

    @PutMapping("/{id}")
    public String sendReceiveMessage(@RequestBody String message, @PathVariable String id) throws IOException, InterruptedException {
        // send message to answer generator service
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(nlpUrl))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("\"" + message + "\""))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        // return answer to frontend
        return JSONObject.quote(response.body());
    }

    @PostMapping("/start-session")
    public ResponseEntity<Agent> startChatSession(@RequestBody RequestDto requestDto) {
        // start s chat session with agent
        Agent agent = chatService.startSession(requestDto);
        return ResponseEntity.ok(agent);
    }
}
