package com.example.settlementwebapplication.model;

import com.example.settlementwebapplication.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="money")
    private double money;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private List<Expense> paidExpenses = new ArrayList<>();

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_expense",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="expense_id"))
    private List<Expense> sharedExpenses;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(){
    }

    public void addSharedExpense(Expense theExpense){
       int splitFor = theExpense.getUsers().size();
       double ownedMoney = theExpense.getPrice()/splitFor;
       this.setMoney(this.getMoney() - ownedMoney);
        sharedExpenses.add(theExpense);
    }
    public void removeSharedExpense(Expense theExpense){
        int splitFor = theExpense.getUsers().size();
        double ownedMoney = theExpense.getPrice()/splitFor;
        this.setMoney(this.getMoney() + ownedMoney);
        sharedExpenses.remove(theExpense);
    }

    public void addPaidExpense(Expense theExpense){
        double paidMoney = theExpense.getPrice();
        this.setMoney(this.getMoney() + paidMoney);
        sharedExpenses.add(theExpense);
    }

    public void removePaidExpense(Expense theExpense){
        double paidMoney = theExpense.getPrice();
        this.setMoney(this.getMoney() - paidMoney);
        sharedExpenses.remove(theExpense);
    }

    public static User from(UserDto userDto){
        User theUser = new User(userDto.getFirstName(),userDto.getLastName());
        theUser.setId(userDto.getId());
        theUser.setMoney(userDto.getMoney());
        theUser.setSharedExpenses(userDto.getSharedExpenses().stream().map(Expense::from).collect(Collectors.toList()));
        theUser.setPaidExpenses(userDto.getPaidExpenses().stream().map(Expense::from).collect(Collectors.toList()));
        return theUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
