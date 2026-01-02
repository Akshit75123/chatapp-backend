package com.ChatApp.controllers;

import com.ChatApp.config.AppConstants;
import com.ChatApp.entites.Message;
import com.ChatApp.services.RoomServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
//@CrossOrigin(AppConstants.frontend_base_url)
 @CrossOrigin("https://chat-without-limits.vercel.app")
public class RoomController {

    private final RoomServices roomServices;

    public RoomController(RoomServices roomServices) {
        this.roomServices = roomServices;
    };

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {
        System.out.println(roomId);
        return roomServices.createRoom(roomId);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        return roomServices.getRoom(roomId);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId) {
        return roomServices.getAllMessages(roomId);

    }

    @PostMapping("/{roomId}/create-message")
    public ResponseEntity<?> createMessage(@PathVariable String roomId, @RequestBody String message) {
        return roomServices.createMessage(roomId, message);
    }

}
