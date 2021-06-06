package com.example.settlementwebapplication.controller;

import com.example.settlementwebapplication.model.User;
import com.example.settlementwebapplication.model.dto.UserDto;
import com.example.settlementwebapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms/{roomId}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@PathVariable long roomId,
                                           @RequestBody final UserDto theUserDto){
        User theUser = userService.addUser(roomId, User.from(theUserDto));
        return new ResponseEntity<>(UserDto.from(theUser), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long roomId,
                                           @PathVariable long userId){
        User theUser = userService.getUser(roomId,userId);
        return new ResponseEntity<>(UserDto.from(theUser), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@PathVariable long roomId){
        List<User> users = userService.getAllUsers(roomId);
        List<UserDto> usersDto = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable long roomId,
                                              @PathVariable long userId){
        User theUser = userService.deleteUser(roomId, userId);
        return new ResponseEntity<>(UserDto.from(theUser), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long roomId,
                                              @PathVariable long userId,
                                              @RequestBody final UserDto theUserDto){
        User updateUser = userService.updateUser(roomId,userId, User.from(theUserDto));
        return new ResponseEntity<>(UserDto.from(updateUser), HttpStatus.OK);
    }
}