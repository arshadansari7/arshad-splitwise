package com.example.splitwire.repo;

import com.example.splitwire.entity.Friendship;
import com.example.splitwire.entity.User;
import com.example.splitwire.enums.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    Optional<Friendship> findBySenderIdAndReceiverId(User sender, User receiver);

    List<Friendship> findAllBySenderId(User senderId);

    List<Friendship> findAllByReceiverId(User receiverId);

    List<Friendship> findAllBySenderIdAndStatus(User senderId, FriendRequestStatus status);

    List<Friendship> findAllByReceiverIdAndStatus(User receiverId, FriendRequestStatus status);

    List<Friendship> findAllBySenderIdOrReceiverId(User senderId, User receiverId);


}
