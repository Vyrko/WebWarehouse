package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findBySenderAndRecipient(String senderId,String recipientId);
}
