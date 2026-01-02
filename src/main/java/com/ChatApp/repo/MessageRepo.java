package com.ChatApp.repo;

import com.ChatApp.entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
