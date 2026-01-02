package com.ChatApp.controllers;

import com.ChatApp.config.AppConstants;
import com.ChatApp.entites.Message;
import com.ChatApp.payload.MessageRequest;
import com.ChatApp.repo.RoomRepository;
import com.ChatApp.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
 @CrossOrigin("https://chat-frontend-xi-jade.vercel.app/")
//@CrossOrigin(AppConstants.frontend_base_url)
public class ChatController {
    private RoomServices roomServices;

    public ChatController(RoomServices roomServices) {
        this.roomServices = roomServices;
    }

    @Autowired
    public RoomRepository roomRepository;

    // for sending and receiving messages
    @MessageMapping("sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")

    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request) {
        return roomServices.sendMessage(roomId, request);
    }

    @DeleteMapping("/clear/{roomId}")
    public ResponseEntity<?> clearChat(@PathVariable String roomId) {
        return roomServices.clearChat(roomId);
    }

}
