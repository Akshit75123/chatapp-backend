package com.ChatApp.services;

import com.ChatApp.entites.Message;
import com.ChatApp.payload.MessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomServices {
    public ResponseEntity<?> createRoom(String roomId);

    public ResponseEntity<?> getRoom(String roomId);

    public ResponseEntity<List<Message>> getAllMessages(String roomId);

    public ResponseEntity<?> createMessage(String roomId, String message);

    public Message sendMessage(String roomId, MessageRequest request);

    public ResponseEntity<?> clearChat(String roomId);

}
