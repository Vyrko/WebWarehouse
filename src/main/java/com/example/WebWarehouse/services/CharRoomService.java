package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.ChatRoom;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getCharRoomId(
            String sender,
            String recipient,
            boolean createNewRoomIfNoyExist
    ){
        return chatRoomRepository
                .findBySenderAndRecipient(sender, recipient)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if(createNewRoomIfNoyExist){
                        var chatId = createChat(sender,recipient);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });

    }
    public String createChat(String sender,String recipient){
        var chatId = String.format(
                "%s_%s",
                sender.replaceAll("\\s+","-"),
                recipient.replaceAll("\\s+","-")
        );

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .sender(sender)
                .recipient(recipient)
                .build();
        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .sender(recipient)
                .recipient(sender)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }
}
