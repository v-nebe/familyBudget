package org.shavneva.familybudget.reports.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExcelGenerator implements ReportGenerator {

    private final TransactionService transactionService;
    private final BalanceService balanceService;

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public Object[] prepareReportData(String username, String date, String currency) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        Map<String, Double> balances = balanceService.calculateBalances(transactions, currency);
        String month = new SimpleDateFormat("yyyy-MM").format(transactions.get(0).getDate());

        return new Object[] { transactions, balances, month };
    }

    @Override
    public byte[] generateReport(String username, String date, String currency) {

        Object[] reportData = prepareReportData(username, date, currency);
        @SuppressWarnings("unchecked")
        List<Transaction> transactions = (List<Transaction>) reportData[0];
        @SuppressWarnings("unchecked")
        Map<String, Double> balances = (Map<String, Double>) reportData[1];
        String month = (String) reportData[2];

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Отчет о транзакциях за " + month);

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Категория");
            header.createCell(1).setCellValue("Сумма");
            header.createCell(2).setCellValue("Валюта");

            // Data
            int rowIdx = 1;
            for (Transaction t : transactions) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(t.getCategory().getCategoryname());
                row.createCell(1).setCellValue(t.getAmount());
                row.createCell(2).setCellValue(t.getCurrency());
            }

            // Balances
            Sheet balanceSheet = workbook.createSheet("Конечный баланс");
            int i = 0;
            for (Map.Entry<String, Double> entry : balances.entrySet()) {
                Row row = balanceSheet.createRow(i++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания excel файла", e);
        }
    }
}
