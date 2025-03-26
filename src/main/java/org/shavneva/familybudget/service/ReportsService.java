package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJcTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJcTable;
import org.shavneva.familybudget.entity.Transaction;

import org.shavneva.familybudget.service.impl.TransactionService;
import org.shavneva.familybudget.service.impl.WordGenerator;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {

    private final TransactionService transactionService;
    private final WordGenerator wordGenerator;

    public byte[] generateWordReportForUser(String username, String date) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);
        return wordGenerator.generateReport(transactions);
    }

}
