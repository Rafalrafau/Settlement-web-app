package com.example.settlementwebapplication.model.dto;

import com.example.settlementwebapplication.model.Expense;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExpenseDto {

    private long id;
    private String name;
    private double price;
    private List<UserDto> users = new ArrayList<>();

    public static ExpenseDto from(Expense theExpense){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(theExpense.getId());
        expenseDto.setName(theExpense.getName());
        expenseDto.setPrice(theExpense.getPrice());
        expenseDto.setUsers(theExpense.getUsers().stream().map(UserDto::from).collect(Collectors.toList()));
        return expenseDto;
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
