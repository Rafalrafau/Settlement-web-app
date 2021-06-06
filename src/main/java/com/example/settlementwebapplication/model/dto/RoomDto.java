package com.example.settlementwebapplication.model.dto;

import com.example.settlementwebapplication.model.Room;
import com.example.settlementwebapplication.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RoomDto {

    private long id;
    private String name;
    private List<UserDto> users = new ArrayList<>();

    public static RoomDto from(Room theRoom) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(theRoom.getId());
        roomDto.setName(theRoom.getName());
        roomDto.setUsers(theRoom.getUsers().stream().map(UserDto::from).collect(Collectors.toList()));
        return roomDto;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
