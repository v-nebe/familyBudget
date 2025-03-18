package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJcTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJcTable;
import org.shavneva.familybudget.entity.Transaction;

import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class TableService {

    private final TransactionService transactionService;

    public byte[] generateWordFileForCurrentUser(String username, String date) {
        List<Transaction> userTransactions = transactionService.getTransactionsByUser(username, date);
        return generateWordFile(userTransactions);
    }

    public byte[] generateWordFile(List<Transaction> transactions){
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Отчет о транзакциях за месяц");
            titleRun.setFontFamily("Times New Roman");
            titleRun.setBold(true);
            titleRun.setFontSize(20);

            XWPFTable table = document.createTable();
            table.setWidth("100%");
            CTTblPr tblPr = table.getCTTbl().getTblPr();
            if (tblPr == null) tblPr = table.getCTTbl().addNewTblPr();
            CTJcTable jc = tblPr.addNewJc();
            jc.setVal(STJcTable.CENTER);

            XWPFTableRow headerRow = table.getRow(0);
            setCellText(headerRow.getCell(0), "Пользователь", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Категория", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Сумма", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Валюта", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Дата", 14, "Times New Roman");



            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for(Transaction transaction: transactions){
                XWPFTableRow row = table.createRow();
                setCellText(row.getCell(0), transaction.getUser().getNickname(), 13, "Times New Roman");
                setCellText(row.getCell(1), transaction.getCategory().getCategoryname(), 13, "Times New Roman");
                setCellText(row.getCell(2), String.valueOf(transaction.getAmount()), 13, "Times New Roman");
                setCellText(row.getCell(3), transaction.getCurrency(), 13, "Times New Roman");
                setCellText(row.getCell(4), dateFormat.format(transaction.getDate()), 13, "Times New Roman");
            }

            document.write(out);
            return  out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellText(XWPFTableCell cell, String text, int fontSize, String fontFamily) {
        XWPFRun run = cell.getParagraphs().get(0).createRun();
        run.setText(text);
        run.setFontSize(fontSize); // Устанавливаем размер шрифта
        run.setFontFamily(fontFamily);
    }
}
