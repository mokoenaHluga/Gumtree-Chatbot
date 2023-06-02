package com.cos730.chatbot.gumtree.controller;

import com.cos730.chatbot.gumtree.entity.Agent;
import com.cos730.chatbot.gumtree.entity.UserSession;
import com.cos730.chatbot.gumtree.exception.NoAgentFoundException;
import com.cos730.chatbot.gumtree.exception.SessionException;
import com.cos730.chatbot.gumtree.model.RequestDto;
import com.cos730.chatbot.gumtree.service.ChatService;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

class ChatControllerTest {

    @Test
    void testSendReceiveMessage() throws Exception {
        String message = "Hello";
        String id = "123";
        String responseBody = "Response Body";
        String nlpUrl = "http://127.0.0.1:5000/send";

        ChatService chatService = Mockito.mock(ChatService.class);
        HttpClient httpClient = Mockito.mock(HttpClient.class);

        ChatController chatController = new ChatController(chatService);
        ReflectionTestUtils.setField(chatController, "nlpUrl", nlpUrl);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(createHttpResponse(responseBody, HttpStatus.OK));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/{id}", id)
                        .content(message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        HttpRequest expectedHttpRequest = HttpRequest.newBuilder(java.net.URI.create(nlpUrl))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("\"" + message + "\""))
                .build();

    }

    @Test
    void testStartChatSession() throws NoAgentFoundException, SessionException {
        RequestDto requestDto = new RequestDto();
        Agent agent = new Agent();
        UserSession session = UserSession.builder()
                .sessionId("1tve33").agent(agent).timeCreate(LocalDateTime.now()).build();

        ChatService chatService = Mockito.mock(ChatService.class);
        when(chatService.startSession(requestDto)).thenReturn(session);

        ChatController chatController = new ChatController(chatService);

        ResponseEntity<UserSession> expectedResponse = ResponseEntity.ok(session);
        ResponseEntity<UserSession> actualResponse = chatController.startChatSession(requestDto);

        Mockito.verify(chatService).startSession(requestDto);
        org.junit.jupiter.api.Assertions.assertEquals(expectedResponse, actualResponse);
    }

    private HttpResponse<String> createHttpResponse(String responseBody, HttpStatus status) {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return status.value();
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }


            @Override
            public String body() {
                return responseBody;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}
