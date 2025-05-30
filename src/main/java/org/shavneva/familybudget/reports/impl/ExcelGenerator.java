package org.shavneva.familybudget.reports.impl;

import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.impl.BalanceService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class ExcelGenerator extends AbstractReportGenerator {

    protected ExcelGenerator(TransactionService transactionService, BalanceService balanceService) {
        super(transactionService, balanceService);
    }

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public byte[] generateReport(ReportData data) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Отчет о транзакциях за " + data.month());

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Категория");
            header.createCell(1).setCellValue("Сумма");
            header.createCell(2).setCellValue("Валюта");

            // Data
            int rowIdx = 1;
            for (Transaction t : data.transactions()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(t.getCategory().getCategoryname());
                row.createCell(1).setCellValue(t.getAmount());
                row.createCell(2).setCellValue(t.getCurrency());
            }

            // Balances
            Sheet balanceSheet = workbook.createSheet("Конечный баланс");
            int i = 0;
            for (Map.Entry<String, Double> entry : data.balances().entrySet()) {
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
