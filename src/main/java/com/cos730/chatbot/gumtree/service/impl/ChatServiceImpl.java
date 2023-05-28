package com.cos730.chatbot.gumtree.service.impl;

import com.cos730.chatbot.gumtree.entity.Agent;
import com.cos730.chatbot.gumtree.entity.UserSession;
import com.cos730.chatbot.gumtree.entity.repository.AgentRepository;
import com.cos730.chatbot.gumtree.entity.repository.UserSessionRepository;
import com.cos730.chatbot.gumtree.model.RequestDto;
import com.cos730.chatbot.gumtree.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    private final UserSessionRepository userSessionRepository;
    private final AgentRepository userRepository;

    @Autowired
    public ChatServiceImpl(UserSessionRepository userSessionRepository, AgentRepository userRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void startSession(RequestDto requestDto) {
        Optional<Agent> user = userRepository.findById(requestDto.getAgentId());

        if (user.isPresent()) {
            UserSession session = UserSession.builder()
                    .sessionId(requestDto.getSessionId())
                    .timeCreate(LocalDate.now().atStartOfDay())
                    .agent(user.get())
                    .build();

            this.userSessionRepository.save(session);
        }
    }
}
