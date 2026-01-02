package com.ChatApp.services;

import com.ChatApp.entites.Message;
import com.ChatApp.entites.Room;
import com.ChatApp.payload.MessageRequest;
import com.ChatApp.repo.MessageRepo;
import com.ChatApp.repo.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomServicesImpl implements RoomServices{
    private final RoomRepository roomRepository;
    @Autowired
    private MessageRepo messageRepo;

    public RoomServicesImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    @Override
    public ResponseEntity<?> createRoom(String roomId) {
        if (roomRepository.findByRoomId(roomId)==null)
        {
            Room room = new Room();
            room.setRoomId(roomId);
            roomRepository.save(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(room);

        }
        return ResponseEntity.badRequest().body("Room already exists");

    }

    @Override
    public ResponseEntity<?> getRoom(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room==null)
        {
            return ResponseEntity.badRequest().body("Room does not exist");
        }
        return ResponseEntity.ok(room);
    }

    @Override
    public ResponseEntity<List<Message>> getAllMessages(String roomId) {
        if (roomRepository.findByRoomId(roomId)==null)
        {
            return ResponseEntity.badRequest().build();
        }
        //get Messages
        Room room = roomRepository.findByRoomId(roomId);
        List<Message> messages = room.getMessages();
        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<?> createMessage(String roomId, String message) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room==null)
        {
            return ResponseEntity.badRequest().body("Room does not exist");
        }
        Message newMessage = new Message();
        newMessage.setContent(message);
        newMessage.setRoom(room);
        room.getMessages().add(newMessage);
        roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
    }

    @Override
    @Transactional
    public Message sendMessage(String roomId, MessageRequest request) {
        Room room = roomRepository.findByRoomId(request.getRoomId());
        if (room==null)
        {
            throw new RuntimeException("Room does not exist");
        }
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());

        message.setTimeStamp(LocalDateTime.now());
        message = messageRepo.save(message);
        message.setRoom(room);
        room.getMessages().add(message);
        roomRepository.save(room);
        return message;
    }

    @Override
    @Transactional
    public ResponseEntity<?> clearChat(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room does not exist");
        }

        // Clear all messages from the room
        room.getMessages().clear();
        roomRepository.save(room);

        return ResponseEntity.ok("Chat cleared successfully");
    }



}
