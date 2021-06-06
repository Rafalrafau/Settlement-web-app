package com.example.settlementwebapplication.model;

import com.example.settlementwebapplication.model.dto.ExpenseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @ManyToMany(mappedBy = "sharedExpenses")
    @JsonIgnoreProperties("sharedExpenses")
    private List<User> users;

    public Expense(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Expense(){
    }

    public static Expense from(ExpenseDto expenseDto){
        Expense expense = new Expense(expenseDto.getName(), expenseDto.getPrice());
        expense.setId(expenseDto.getId());
        expense.setUsers(expenseDto.getUsers().stream().map(User::from).collect(Collectors.toList()));
        return expense;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
