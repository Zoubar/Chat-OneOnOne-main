package com.OneOnOne.Backend.repository;

import com.OneOnOne.Backend.entity.Chat;
import com.OneOnOne.Backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChat(Chat chat);

}
