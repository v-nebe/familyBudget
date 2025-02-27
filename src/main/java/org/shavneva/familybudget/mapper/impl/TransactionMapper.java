package org.shavneva.familybudget.mapper.impl;

import org.shavneva.familybudget.dto.CategoryDTO;
import org.shavneva.familybudget.dto.TransactionDTO;
import org.shavneva.familybudget.dto.UserDTO;
import org.shavneva.familybudget.entity.Category;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper implements IMapper<Transaction, TransactionDTO> {

    private final IMapper<User, UserDTO> userMapper;
    private final IMapper<Category, CategoryDTO> categoryMapper;

    @Autowired
    public TransactionMapper(IMapper<User, UserDTO> userMapper, IMapper<Category, CategoryDTO> categoryMapper) {
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public TransactionDTO mapToDTO(Transaction entity) {
        if(entity == null){
            return null;
        }
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setIdtransaction(entity.getIdtransaction());
        transactionDTO.setCurrency(entity.getCurrency());
        transactionDTO.setAmount(entity.getAmount());
        transactionDTO.setDate(entity.getDate());

        transactionDTO.setUser(userMapper.mapToDTO(entity.getUser()));
        transactionDTO.setCategory(categoryMapper.mapToDTO(entity.getCategory()));

        return transactionDTO;
    }

    @Override
    public Transaction mapToEntity(TransactionDTO transactionDTO) {
        if(transactionDTO == null){
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setIdtransaction(transactionDTO.getIdtransaction());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate());

        transaction.setUser(userMapper.mapToEntity(transactionDTO.getUser()));
        transaction.setCategory(categoryMapper.mapToEntity(transactionDTO.getCategory()));

        return transaction;
    }
}
