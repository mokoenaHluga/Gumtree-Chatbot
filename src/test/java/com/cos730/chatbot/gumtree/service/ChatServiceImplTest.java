package com.cos730.chatbot.gumtree.service;

import com.cos730.chatbot.gumtree.entity.Agent;
import com.cos730.chatbot.gumtree.entity.UserSession;
import com.cos730.chatbot.gumtree.entity.repository.AgentRepository;
import com.cos730.chatbot.gumtree.entity.repository.UserSessionRepository;
import com.cos730.chatbot.gumtree.exception.NoAgentFoundException;
import com.cos730.chatbot.gumtree.exception.SessionException;
import com.cos730.chatbot.gumtree.model.RequestDto;
import com.cos730.chatbot.gumtree.service.impl.ChatServiceImpl;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.Optional;

class ChatServiceImplTest {

    @Mock
    private UserSessionRepository userSessionRepository;

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartSession_WithValidAgentId_ShouldSaveUserSessionAndReturnAgent() throws NoAgentFoundException, SessionException {
        // Arrange
        long agentId = 1L;
        String sessionId = "ABC123";
        Agent agent = Agent.builder().id(1L).name("Stacey").surname("COS730").build();
        UserSession session = UserSession.builder()
                .sessionId("ghvcc5")
                .timeCreate(LocalDateTime.now())
                .agent(agent)
                .build();


        RequestDto requestDto = new RequestDto(sessionId, agentId);

        when(agentRepository.findById(anyLong())).thenReturn(Optional.of(agent));
        when(userSessionRepository.save(any(UserSession.class))).thenReturn(session);

        UserSession result = chatService.startSession(requestDto);

        verify(userSessionRepository).save(any(UserSession.class));
        assertNotNull(result);
        assertNotNull(result.getAgent());
    }

    @Test
    void testStartSessionUnsuccessfulNoAgentFound() {
        // Arrange
        long agentId = 1L;
        String sessionId = "ABC123";
        RequestDto requestDto = RequestDto.builder()
                .agentId(agentId)
                .sessionId(sessionId)
                .build();

        // Act
        SessionException exception = assertThrows(SessionException.class, () ->
                chatService.startSession(requestDto));

        String expectedMessage = "Could not start a session";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));    }

//    @Test
//    void testStartSessionUnsuccessful() {
//        // Arrange
//        long agentId = 1L;
//        String sessionId = "ABC123";
//        RequestDto requestDto = new RequestDto(sessionId, agentId);
//
//        // Act
//        SessionException exception = assertThrows(SessionException.class, () ->
//                chatService.startSession(requestDto));
//
//        String expectedMessage = "Could not start a session";
//        String actualMessage = exception.getMessage();
//
//        // Assert
//        assertTrue(actualMessage.contains(expectedMessage));    }
}
