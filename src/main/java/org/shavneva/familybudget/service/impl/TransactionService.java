package org.shavneva.familybudget.service.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.TransactionRepository;
import org.shavneva.familybudget.repository.UserRepository;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements ICrudService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public Transaction create(Transaction newTransaction) {
        System.out.println(newTransaction);
        return transactionRepository.save(newTransaction);
    }

    @Override
    public List<Transaction> read() {
        return transactionRepository.findAll();
    }


    public Transaction getById(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id:" + id));
    }

    @Override
    public Transaction update(Transaction updatedTransaction) {
        Transaction transactionExisting = getById(updatedTransaction.getIdtransaction());
        if(updatedTransaction.getUser() != null){
            transactionExisting.setUser(updatedTransaction.getUser());
        }
        if(updatedTransaction.getCategory() != null){
            transactionExisting.setCategory(updatedTransaction.getCategory());
        }
        if(updatedTransaction.getCurrency() != null){
            transactionExisting.setCurrency(updatedTransaction.getCurrency());
        }
        if(updatedTransaction.getAmount() != null){
            transactionExisting.setAmount(updatedTransaction.getAmount());
        }
        if(updatedTransaction.getDate() != null){
            transactionExisting.setDate(updatedTransaction.getDate());
        }

        return  transactionRepository.save(updatedTransaction);
    }

    @Override
    public void delete(int id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByUser(String nickname, String date) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String yearMonth = date.substring(0, 7);
        return transactionRepository.findByUserAndMonth(user.getIduser(), yearMonth);
    }
}
