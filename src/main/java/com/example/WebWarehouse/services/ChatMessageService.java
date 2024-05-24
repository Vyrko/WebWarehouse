package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.ChatMessage;
import com.example.WebWarehouse.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final CharRoomService charRoomService;
    private final ChatMessageRepository repository;

    public ChatMessage save(ChatMessage chatMessage){
        var chatId = charRoomService
                .getCharRoomId(chatMessage.getSender(), chatMessage.getRecipient(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }
    public List<ChatMessage> findChatMessage(
            String senderId, String recipient
    ){
        var chatId = charRoomService.getCharRoomId(
                senderId,
                recipient,
                false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
