package com.cos730.chatbot.gumtree.service;

import com.cos730.chatbot.gumtree.model.RequestDto;
import org.springframework.stereotype.Service;


public interface ChatService {
    boolean startSession(RequestDto requestDto);
}
