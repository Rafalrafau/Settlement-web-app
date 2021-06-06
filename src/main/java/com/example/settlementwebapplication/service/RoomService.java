package com.example.settlementwebapplication.service;

import com.example.settlementwebapplication.model.Room;
import com.example.settlementwebapplication.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room addRoom(Room theRoom){
        roomRepository.save(theRoom);
        return theRoom;
    }

    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    public Room getRoom(long roomId){
       Optional<Room> roomResult = roomRepository.findById(roomId);
       Room theRoom = null;
       if(roomResult.isPresent()){
           theRoom = roomResult.get();
           return theRoom;
       }else{
           throw new RuntimeException("Did not find room id- " + roomId);
       }
    }

    public Room updateRoom(long roomId, Room theRoom){
       Room roomToUpdate = getRoom(roomId);
       roomToUpdate.setName(theRoom.getName());
        return roomToUpdate;
    }

    public Room deleteRoom(Long roomId){
        Room roomToDelete = getRoom(roomId);
        roomRepository.delete(roomToDelete);
        return roomToDelete;
    }
}
