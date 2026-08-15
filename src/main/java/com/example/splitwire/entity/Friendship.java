package com.example.splitwire.entity;

import com.example.splitwire.enums.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverId;

    @Enumerated(EnumType.ORDINAL)
    private FriendRequestStatus status;


    public Friendship(User sender, User receiver, FriendRequestStatus pending) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.status = pending;
    }
}
