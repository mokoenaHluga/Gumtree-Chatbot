package com.cos730.chatbot.gumtree.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CHAT_SESSION")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SESSION_ID", nullable = false)
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private Agent agent;

    @Column(name = "TIME_CREATE", nullable = false)
    private LocalDateTime timeCreate;

    @Transient
    private Long agentId;

    @PrePersist
    private void updateAgentId() {
        if (agent != null) {
            agentId = agent.getId();
        }
    }

    @PostLoad
    private void restoreAgent() {
        if (agentId != null) {
            agent = new Agent();
            agent.setId(agentId);
        }
    }

}
