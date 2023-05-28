package com.cos730.chatbot.gumtree.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestDto {
    private String sessionId;
    private Long agentId;
}
