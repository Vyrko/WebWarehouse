package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findByChatId(String s);
}
