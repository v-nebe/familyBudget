package org.shavneva.familybudget.service.impl;

import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.ReportGenerator;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class ExcelGenerator implements ReportGenerator {
    @Override
    public byte[] generateReport(List<Transaction> transactionList, Map<String, Double> balances) {
        if (transactionList.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        String month = monthFormat.format(transactionList.get(0).getDate());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Отчет о транзакциях за " + month);

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Категория");
            header.createCell(1).setCellValue("Сумма");
            header.createCell(2).setCellValue("Валюта");

            // Data
            int rowIdx = 1;
            for (Transaction t : transactionList) {
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
