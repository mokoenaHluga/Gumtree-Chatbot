package com.cos730.chatbot.gumtree.entity.repository;

import com.cos730.chatbot.gumtree.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
}
