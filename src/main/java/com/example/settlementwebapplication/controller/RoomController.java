package com.example.settlementwebapplication.controller;

import com.example.settlementwebapplication.model.Room;
import com.example.settlementwebapplication.model.dto.RoomDto;
import com.example.settlementwebapplication.model.dto.UserDto;
import com.example.settlementwebapplication.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> findAllRooms(){
        List<Room> rooms = roomService.getRooms();
        for(Room r : rooms){
            r.setUsers(new ArrayList<>() {
            });
        }
        List<RoomDto> roomsDto = rooms.stream().map(RoomDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(roomsDto, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> findRoom(@PathVariable long roomId){
        Room theRoom = roomService.getRoom(roomId);
        return new ResponseEntity<>(RoomDto.from(theRoom), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RoomDto> addRoom(@RequestBody final RoomDto theRoomDto){
        Room theRoom = roomService.addRoom(Room.from(theRoomDto));
        return new ResponseEntity<>(RoomDto.from(theRoom), HttpStatus.OK);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable long roomId,
                                              @RequestBody final RoomDto theRoomDto){
        Room theRoom = roomService.updateRoom(roomId, Room.from(theRoomDto));
        return new ResponseEntity<>(RoomDto.from(theRoom), HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomDto> deleteRoom(@PathVariable long roomId) {
       Room theRoom = roomService.deleteRoom(roomId);
       return new ResponseEntity<>(RoomDto.from(theRoom), HttpStatus.OK);
    }
}