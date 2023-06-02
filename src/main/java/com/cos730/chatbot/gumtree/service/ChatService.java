package com.cos730.chatbot.gumtree.service;

import com.cos730.chatbot.gumtree.entity.UserSession;
import com.cos730.chatbot.gumtree.exception.SessionException;
import com.cos730.chatbot.gumtree.model.RequestDto;


public interface ChatService {
    UserSession startSession(RequestDto requestDto) throws SessionException;
}
