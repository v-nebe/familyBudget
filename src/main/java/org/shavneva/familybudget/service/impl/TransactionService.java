package org.shavneva.familybudget.service.impl;

import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.TransactionRepository;
import org.shavneva.familybudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService extends BaseService<Transaction, Integer> {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        super(transactionRepository, "Transaction");
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction create(Transaction newTransaction) {
        return super.create(newTransaction);
    }

    @Override
    public List<Transaction> read() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction update(Transaction updatedTransaction) {
        Transaction transactionExisting = transactionRepository.findById(updatedTransaction.getIdtransaction())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + updatedTransaction.getIdtransaction()));

        if (updatedTransaction.getUser() != null &&
                updatedTransaction.getUser().getIduser() != transactionExisting.getUser().getIduser()) {
            throw new AccessDeniedException("Вы не можете изменить владельца транзакции.");
        }

        if (updatedTransaction.getCategory() != null) {
            transactionExisting.setCategory(updatedTransaction.getCategory());
        }
        if (updatedTransaction.getCurrency() != null) {
            transactionExisting.setCurrency(updatedTransaction.getCurrency());
        }
        if (updatedTransaction.getAmount() != null) {
            transactionExisting.setAmount(updatedTransaction.getAmount());
        }
        if (updatedTransaction.getDate() != null) {
            transactionExisting.setDate(updatedTransaction.getDate());
        }

        return transactionRepository.save(transactionExisting);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }


    public List<Transaction> getTransactionsByUser(String nickname, String date) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String yearMonth = date.substring(0, 7);
        return transactionRepository.findByUserAndMonth(user.getIduser(), yearMonth);
    }
}
