package com.OneOnOne.Backend.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();
    private LocalDateTime createdAt;

}
