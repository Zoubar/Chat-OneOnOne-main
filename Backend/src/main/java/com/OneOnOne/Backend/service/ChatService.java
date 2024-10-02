package com.OneOnOne.Backend.service;

import com.OneOnOne.Backend.entity.Chat;
import com.OneOnOne.Backend.entity.Message;
import com.OneOnOne.Backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface ChatService {

     Chat createChat(User user1, User user2);
     List<Chat> getAllChats(User user);
     Message sendMessage(Chat chat, User sender, String content);
     List<Message> getMessages(Chat chat);
     Chat getChatById(Long id);
     Optional<Chat> getChatBetweenUsers(User user1, User user2);
     Chat createChatWithMessage(User user1, Long user2Id, String messageContent);




}
