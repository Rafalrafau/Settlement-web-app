package com.example.settlementwebapplication.service;

import com.example.settlementwebapplication.model.Room;
import com.example.settlementwebapplication.model.User;
import com.example.settlementwebapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;


    public User addUser(Long roomId, User theUser){
        Room currentRoom = roomService.getRoom(roomId);
        theUser.setId(0);
        currentRoom.addUser(theUser);
        return userRepository.save(theUser);
    }

    public User getUser(long roomId, long userId){
        Room currentRoom = roomService.getRoom(roomId);
        Optional<User> result = userRepository.findById(userId);
        User theUser = null;
        if(result.isPresent()){
            theUser = result.get();
            if(currentRoom.getUsers().contains(theUser)){
                return theUser;
            }else{
                throw new RuntimeException("Did not find user with id- " + userId +
                        "in room: " + currentRoom.getName());
            }
        }else{
            throw new RuntimeException("Did not find user with id- " + userId);
        }
    }

    @Transactional
    public List<User> getAllUsers(long roomId){
        Room currentRoom = roomService.getRoom(roomId);
        return currentRoom.getUsers();
    }

    public User deleteUser(long roomId, long userId){
        User userToDelete = getUser(roomId, userId);
        Room theRoom = roomService.getRoom(roomId);
        theRoom.removeUser(userToDelete);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    public User updateUser(long roomId, long userId, User theUser){
        User userToUpdate = getUser(roomId, userId);
        theUser.setId(userToUpdate.getId());
        return userRepository.save(theUser);
    }
}
