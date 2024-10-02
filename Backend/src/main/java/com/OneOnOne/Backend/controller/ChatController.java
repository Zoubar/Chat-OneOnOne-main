package com.OneOnOne.Backend.controller;

import com.OneOnOne.Backend.entity.Chat;
import com.OneOnOne.Backend.entity.Message;
import com.OneOnOne.Backend.entity.User;
import com.OneOnOne.Backend.repository.UserRepository;
import com.OneOnOne.Backend.service.ChatService;
import com.OneOnOne.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(ChatController.class.getName());



    @PostMapping("/api/chat-with-message")
    public Chat createChatWithMessage(@RequestBody Map<String, Object> payload, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user1 = userRepository.findByEmail(oAuth2User.getAttribute("email"));
                //.orElseThrow(() -> new IllegalStateException("User not found"));

        Integer user2IdInt = (Integer) payload.get("user2Id");
        Long user2Id = user2IdInt.longValue(); // Convert Integer to Long
        String messageContent = (String) payload.get("messageContent");

        return chatService.createChatWithMessage(user1, user2Id, messageContent);
    }


    @PostMapping("/api/chat")
    public Chat createChat(@RequestBody User user2, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user1 = new User();
        user1.setEmail(oAuth2User.getAttribute("email"));
        user1.setUsername(oAuth2User.getAttribute("name"));
        return chatService.createChat(user1, user2);
    }

    @GetMapping("/api/chats")
    public List<Chat> getAllChats(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Fetch the user from the database
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        return chatService.getAllChats(user);
    }

    @GetMapping("/api/chats/{chatId}/messages")
    public List<Message> getMessages(@PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        List<Message> messages = chatService.getMessages(chat);
        logger.info("Messages retrieved for chat ID " + chatId + ": " + messages);
        return messages;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(Message message) {
        Chat chat = chatService.getChatById(message.getChat().getId());
        User sender = userService.findUserByEmail(message.getSender().getEmail());
        return chatService.sendMessage(chat, sender, message.getContent());
    }

    @GetMapping("/api/chats/between/{userId1}/{userId2}")
    public Chat getChatBetweenUsers(@PathVariable Long userId1, @PathVariable Long userId2) {
        User user1 = new User();
        user1.setId(userId1);
        User user2 = new User();
        user2.setId(userId2);
        Optional<Chat> chat = chatService.getChatBetweenUsers(user1, user2);
        return chat.orElseThrow(() -> new IllegalStateException("Chat not found"));
    }



    @GetMapping("/api/chats/{id}")
    public Chat getChatById(@PathVariable Long id) {
        return chatService.getChatById(id);
    }








}
