package com.example.splitwire.controller;

import com.example.splitwire.entity.Friendship;
import com.example.splitwire.entity.User;
import com.example.splitwire.enums.FriendRequestStatus;
import com.example.splitwire.repo.FriendshipRepository;
import com.example.splitwire.repo.UserRepository;
import com.example.splitwire.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @PostMapping("request/{senderId}")
    public String sendFriendRequest(@PathVariable Integer senderId, @RequestParam("receiverId") Integer receiverId) {
        return friendshipService.sendFriendRequest(senderId, receiverId);
    }

    @GetMapping("request/all")
    public List<Friendship> getAllFriendships() {
        return friendshipRepository.findAll();
    }

    @GetMapping("request/all/{userId}")
    public List<Friendship> showAllRequestsByUserId(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository.findAllBySenderId(user);
    }

    @GetMapping("friends/{userId}")
    public List<Friendship> myFriends(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Friendship> sender = friendshipRepository.findAllBySenderIdAndStatus(user, FriendRequestStatus.ACCEPTED);
        List<Friendship> receiver = friendshipRepository.findAllByReceiverIdAndStatus(user, FriendRequestStatus.ACCEPTED);

        Set<Friendship> friendSet = new HashSet<>(sender);
        friendSet.addAll(receiver);

        return new ArrayList<>(friendSet);
    }

    @GetMapping("friends/pending/{userId}")
    public List<Friendship> getPendingFriendsByUserId(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository.findAllBySenderIdAndStatus(user, FriendRequestStatus.PENDING);
    }

    @GetMapping("friendrequest/{userId}")
    public List<Friendship> getFriendRequestsByUserId(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        return friendshipRepository.findAllByReceiverIdAndStatus(user, FriendRequestStatus.PENDING);
    }

    @PostMapping("request/acceptAll/{userId}")
    public List<Friendship> acceptAllPendingRequestByUser(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Friendship> pendingRequests = friendshipRepository.findAllByReceiverIdAndStatus(user, FriendRequestStatus.PENDING);
        for (Friendship request : pendingRequests) {
            request.setStatus(FriendRequestStatus.ACCEPTED);
        }

        return friendshipRepository.saveAll(pendingRequests);
    }

}
