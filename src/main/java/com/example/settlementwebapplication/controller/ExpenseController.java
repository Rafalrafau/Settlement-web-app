package com.example.settlementwebapplication.controller;

import com.example.settlementwebapplication.model.Expense;
import com.example.settlementwebapplication.model.dto.ExpenseDto;
import com.example.settlementwebapplication.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/{roomId}/users/{userId}/expenses")
    public ResponseEntity<ExpenseDto> addExpense(@PathVariable long roomId,
                                                 @PathVariable long userId,
                                                 @RequestBody ExpenseDto theExpenseDto){
       Expense theExpense = expenseService.addExpense(roomId, userId,Expense.from(theExpenseDto));
       return new ResponseEntity<>(ExpenseDto.from(theExpense), HttpStatus.OK);
    }

    @GetMapping("/{roomId}/users/{userId}/expenses/{expenseId}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable long roomId,
                                                 @PathVariable long userId,
                                                 @PathVariable long expenseId){
       Expense theExpense = expenseService.getExpense(roomId, userId, expenseId);
       return new ResponseEntity<>(ExpenseDto.from(theExpense), HttpStatus.OK);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@PathVariable long roomId){
        List<Expense> expenses = expenseService.getAllExpenses(roomId);
        List<ExpenseDto> expenseDto = expenses.stream().map(ExpenseDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }

    @PutMapping("/{roomId}/users/{userId}/expenses")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable long roomId,
                                                    @PathVariable long userId,
                                                    @RequestBody ExpenseDto theExpenseDto){
        Expense theExpense = expenseService.updateExpense(roomId, userId, Expense.from(theExpenseDto));
        return new ResponseEntity<>(ExpenseDto.from(theExpense), HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}/users/{userId}/expenses/{expenseId}")
    public ResponseEntity<ExpenseDto> deleteExpense(@PathVariable long roomId,
                                 @PathVariable long userId,
                                 @PathVariable long expenseId){
       Expense theExpense = expenseService.deleteExpense(roomId, userId, expenseId);
       return new ResponseEntity<>(ExpenseDto.from(theExpense), HttpStatus.OK);
    }
}
