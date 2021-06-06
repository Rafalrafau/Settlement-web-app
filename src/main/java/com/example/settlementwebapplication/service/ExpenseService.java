package com.example.settlementwebapplication.service;

import com.example.settlementwebapplication.model.Expense;
import com.example.settlementwebapplication.model.Room;
import com.example.settlementwebapplication.model.User;
import com.example.settlementwebapplication.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Transactional
    public Expense addExpense(long roomId,long userId, Expense theExpense){
        theExpense.setId(0);
        User paidByUser = userService.getUser(roomId, userId);
        paidByUser.addPaidExpense(theExpense);
        for(User tempUser: theExpense.getUsers()){
            tempUser.addSharedExpense(theExpense);
        }
        expenseRepository.save(theExpense);
        return theExpense;
    }

    public Expense getExpense(long roomId, long userId, long expenseId){
        User theUser = userService.getUser(roomId, userId);
        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense theExpense = null;
        if(result.isPresent()){
            theExpense = result.get();
            if(theUser.getPaidExpenses().contains(theExpense)){
                return theExpense;
            }else{
                throw new RuntimeException("There is no expense with id " + expenseId +
                        " paid by " + theUser.getFirstName());
            }
        }else{
            throw new RuntimeException("There is no expense with id " + expenseId);
        }
    }

    @Transactional
    public List<Expense> getAllExpenses(long roomId){
        Room theRoom = roomService.getRoom(roomId);
        List<Expense> expensesInGroup = new ArrayList<>();
        for(User theUser: theRoom.getUsers()){
            for(Expense theExpense: theUser.getPaidExpenses()){
                expensesInGroup.add(theExpense);
            }
        }
        return expensesInGroup;
    }

    @Transactional
    public Expense deleteExpense(long roomId,long userId, long expenseId){
        User paidByUser = userService.getUser(roomId, userId);
        Expense theExpense = getExpense(roomId,userId,expenseId);
        paidByUser.removePaidExpense(theExpense);
        for(User tempUser: theExpense.getUsers()){
            tempUser.removeSharedExpense(theExpense);
        }
        expenseRepository.delete(theExpense);
        return theExpense;
    }

    @Transactional
    public Expense updateExpense(long roomId, long userId, Expense theExpense){
       long theId = theExpense.getId();
       Expense dlExpense = getExpense(roomId,userId,theId);
       expenseRepository.delete(dlExpense);
       expenseRepository.save(theExpense);
       return theExpense;
    }

}
