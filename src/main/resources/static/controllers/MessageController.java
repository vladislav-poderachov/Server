package controllers;

import dto.MessageRequest;
import dto.MessageResponse;
import services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send/{senderId}")
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable Long senderId, @RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(messageService.sendMessage(senderId, messageRequest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponse>> getUserMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getUserMessages(userId));
    }
} 