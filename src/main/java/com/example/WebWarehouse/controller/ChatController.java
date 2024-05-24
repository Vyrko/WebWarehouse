package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.ChatMessage;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.model.ChatNotification;
import com.example.WebWarehouse.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    @GetMapping("chat")
    public String openChat(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("nickname",user.getEmail().replace('@', '_'));
        model.addAttribute("fullname",user.getName());
        return "chatIndex";
    }
    @MessageMapping("chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ){
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(),"/queue/messages",
                ChatNotification.builder()
                        .id(savedMsg.getChatId())
                        .sender(savedMsg.getSender())
                        .recipient(savedMsg.getRecipient())
                        .content(savedMsg.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId,recipientId));
    }
}
