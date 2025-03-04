package org.shavneva.familybudget.controller.impl;

import org.shavneva.familybudget.dto.CategoryDTO;
import org.shavneva.familybudget.dto.TransactionDTO;
import org.shavneva.familybudget.dto.UserDTO;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.mapper.IMapper;
import org.shavneva.familybudget.mapper.impl.TransactionMapper;
import org.shavneva.familybudget.service.ICrudService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.shavneva.familybudget.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController extends BaseController<Transaction, TransactionDTO> {

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper){
        super(transactionService, transactionMapper);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    public TransactionDTO create(TransactionDTO transactionDTO){
        return super.create(transactionDTO);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    public List<TransactionDTO> read(){
        return super.read();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    public TransactionDTO update(TransactionDTO transactionDTO){
        return super.update(transactionDTO);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    public void delete(int id){
        super.delete(id);
    }
}
