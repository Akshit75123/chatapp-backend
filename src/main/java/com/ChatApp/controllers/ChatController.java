package com.ChatApp.controllers;

import com.ChatApp.entites.Message;
import com.ChatApp.payload.MessageRequest;
import com.ChatApp.services.RoomServices;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@CrossOrigin(
        origins = "https://chat-frontend-xi-jade.vercel.app",
        allowedHeaders = "*"
)
@Controller
public class ChatController {

    private final RoomServices roomServices;

    public ChatController(RoomServices roomServices) {
        this.roomServices = roomServices;
    }
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @Payload MessageRequest request) {

        return roomServices.sendMessage(roomId, request);
    }

    // REST API (this part was already fine)
    @DeleteMapping("/clear/{roomId}")
    public ResponseEntity<?> clearChat(@PathVariable String roomId) {
        return roomServices.clearChat(roomId);
    }
}
