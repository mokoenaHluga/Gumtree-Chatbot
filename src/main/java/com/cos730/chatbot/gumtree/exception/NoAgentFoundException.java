package com.cos730.chatbot.gumtree.exception;

public class NoAgentFoundException extends Exception{
    public NoAgentFoundException(String message) {
        super(message);
    }

    public NoAgentFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
