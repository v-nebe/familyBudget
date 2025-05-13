package org.shavneva.familybudget.service.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.TransactionRepository;
import org.shavneva.familybudget.repository.UserRepository;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements ICrudService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public Transaction create(Transaction newTransaction) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        System.out.println(newTransaction.getUser());

        if (newTransaction.getUser().getIduser() != currentUser.getIduser()) {
            throw new AccessDeniedException("Вы не можете создавать транзакцию для другого пользователя.");
        }
        else {
            newTransaction.setUser(currentUser);
        }

        return transactionRepository.save(newTransaction);
    }

    @Override
    public List<Transaction> read() {
        return transactionRepository.findAll();
    }


    public Transaction getById(int id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        if (transaction.getUser().getIduser() != currentUser.getIduser()) {
            throw new AccessDeniedException("Вы не можете просматривать чужую транзакцию.");
        }

        return transaction;
    }

    @Override
    public Transaction update(Transaction updatedTransaction) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Transaction transactionExisting = getById(updatedTransaction.getIdtransaction());

        if (updatedTransaction.getUser().getIduser() != currentUser.getIduser()) {
            throw new AccessDeniedException("Вы не можете переназначить свою транзакцию на другого пользователя.");
        }

        transactionExisting.setUser(updatedTransaction.getUser());
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Транзакция была не найден с id " + id));

        if (transaction.getUser().getIduser() != currentUser.getIduser()) {
            throw new AccessDeniedException("Вы не можете удалить чужую транзакцию.");
        }

        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByUser(String nickname, String date) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String yearMonth = date.substring(0, 7);
        return transactionRepository.findByUserAndMonth(user.getIduser(), yearMonth);
    }
}
