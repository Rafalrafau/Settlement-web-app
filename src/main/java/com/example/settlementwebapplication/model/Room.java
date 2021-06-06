package com.example.settlementwebapplication.model;

import com.example.settlementwebapplication.model.dto.RoomDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="room")
public class Room extends Object{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="room_id")
    private List<User> users = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public Room(){
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public static Room from(RoomDto roomDto){
        Room theRoom = new Room(roomDto.getName());
        theRoom.setId(roomDto.getId());
        theRoom.setUsers(roomDto.getUsers().stream().map(User::from).collect(Collectors.toList()));
        return theRoom;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
