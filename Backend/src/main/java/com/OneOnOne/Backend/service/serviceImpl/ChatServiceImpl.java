package com.OneOnOne.Backend.service.serviceImpl;


import com.OneOnOne.Backend.entity.Chat;
import com.OneOnOne.Backend.entity.Message;
import com.OneOnOne.Backend.entity.User;
import com.OneOnOne.Backend.repository.ChatRepository;
import com.OneOnOne.Backend.repository.MessageRepository;
import com.OneOnOne.Backend.repository.UserRepository;
import com.OneOnOne.Backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private static final Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());


    @Override
    public Chat createChat(User user1, User user2) {
        // Ensure both users are fetched from the database
        user1 = userRepository.findById(user1.getId()).orElseThrow(() -> new IllegalStateException("User not found"));
        user2 = userRepository.findById(user2.getId()).orElseThrow(() -> new IllegalStateException("User not found"));

        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Chat createChatWithMessage(User user1, Long user2Id, String messageContent) {
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new IllegalStateException("User not found"));
        Chat chat = createChat(user1, user2);
        sendMessage(chat, user1, messageContent);
        return chat;
    }



    @Override
    public List<Chat> getAllChats(User user) {
        return chatRepository.findByUser(user);
    }



    @Override
    public Message sendMessage(Chat chat, User sender, String content) {
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Chat chat) {
        return messageRepository.findByChat(chat);
    }

    @Override
    public Chat getChatById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        return chat.orElseThrow(() -> new IllegalStateException("Chat not found"));
    }

    @Override
    public Optional<Chat> getChatBetweenUsers(User user1, User user2) {
        return chatRepository.findChatBetweenUsers(user1, user2);
    }




}
