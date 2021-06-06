package com.example.settlementwebapplication.model.dto;

import com.example.settlementwebapplication.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private double money;
    private List<ExpenseDto> sharedExpenses = new ArrayList<>();
    private List<ExpenseDto> paidExpenses = new ArrayList<>();

    public static UserDto from(User theUser ){
        UserDto userDto = new UserDto();
        userDto.setId(theUser.getId());
        userDto.setFirstName(theUser.getFirstName());
        userDto.setLastName(theUser.getLastName());
        userDto.setMoney(theUser.getMoney());
        userDto.setSharedExpenses(theUser.getSharedExpenses().stream().map(ExpenseDto::from).collect(Collectors.toList()));
        userDto.setPaidExpenses(theUser.getPaidExpenses().stream().map(ExpenseDto::from).collect(Collectors.toList()));
        return userDto;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", money=" + money +
                '}';
    }
}
