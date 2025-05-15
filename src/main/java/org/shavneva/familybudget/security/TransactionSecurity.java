package org.shavneva.familybudget.security;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.repository.TransactionRepository;
import org.shavneva.familybudget.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("transactionSecurity")
@AllArgsConstructor
public class TransactionSecurity {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    public boolean isOwner(int transactionId, Authentication authentication){
        String username = authentication.getName();
        return transactionRepository.findById(transactionId)
                .map(t -> t.getUser().getNickname().equals(username))
                .orElse(false);

    }

    public boolean isUserSelf(Transaction transaction, Authentication authentication){
        String username = authentication.getName();
        return  userRepository.findById(transaction.getUser().getIduser())
                .map(user -> user.getNickname().equals(username))
                .orElse(false);
    }
}
