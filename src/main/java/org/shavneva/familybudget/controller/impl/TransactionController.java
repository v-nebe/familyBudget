package org.shavneva.familybudget.controller.impl;

import lombok.AllArgsConstructor;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController extends BaseController<Transaction, TransactionDTO> {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        super(transactionService, transactionMapper);
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }


    @Override
    @PreAuthorize("isAuthenticated() and @transactionSecurity.isUserSelf(#transactionDTO, authentication)")
    public TransactionDTO create(TransactionDTO transactionDTO){
        return super.create(transactionDTO);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<TransactionDTO> read(){
        return super.read();
    }

    @Override
    @PreAuthorize("isAuthenticated() and @transactionSecurity.isOwner(#transactionDTO.idtransaction, authentication)")
    public TransactionDTO update(TransactionDTO transactionDTO){
        return super.update(transactionDTO);
    }

    @Override
    @PreAuthorize("isAuthenticated() and @transactionSecurity.isOwner(#id, authentication)")
    public void delete(int id){
        super.delete(id);
    }
}
