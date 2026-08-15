package com.example.splitwire.service;

import com.example.splitwire.entity.Friendship;
import com.example.splitwire.entity.User;
import com.example.splitwire.enums.FriendRequestStatus;
import com.example.splitwire.repo.FriendshipRepository;
import com.example.splitwire.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public String sendFriendRequest(Integer senderId, Integer receiverId) {

        if (senderId == null || receiverId == null) {
            return "Sender and receiver IDs must be provided.";
        }

        if (Objects.equals(senderId, receiverId)) {
            return "Sender and receiver cannot be the same user.";
        }

        Optional<User> sender = userRepository.findById(senderId);

        if (sender.isEmpty()) return "Sender doesn't exists";

        Optional<User> receiver = userRepository.findById(receiverId);

        if (receiver.isEmpty()) return "Receiver doesn't exists";

        Optional<Friendship> existingFriendships = friendshipRepository.findBySenderIdAndReceiverId(sender.get(), receiver.get());

        if (existingFriendships.isPresent()) {
            if (existingFriendships.get().getStatus() == FriendRequestStatus.PENDING) {
                return "Request Already Exists in Pending State";
            }

            if (existingFriendships.get().getStatus() == FriendRequestStatus.REJECTED) {
                return "Request Already Rejected by Receiver";
            }
        }

        Friendship friendship = new Friendship(sender.get(), receiver.get(), FriendRequestStatus.PENDING);

        friendshipRepository.save(friendship);

        log.info("Friend request created: senderId={}, receiverId={}", senderId, receiverId);

        return "User request Sent";
    }
}
