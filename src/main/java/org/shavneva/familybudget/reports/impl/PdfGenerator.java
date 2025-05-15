package org.shavneva.familybudget.reports.impl;

import com.itextpdf.text.pdf.BaseFont;
import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PdfGenerator implements ReportGenerator {
    private final TransactionService transactionService;
    private final BalanceService balanceService;

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.APPLICATION_PDF;
    }

    @Override
    public String getFileExtension() {
        return "pdf";
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

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        String month = monthFormat.format(transactions.get(0).getDate());

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Загрузка шрифта Roboto
            BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font bodyFont = new Font(baseFont, 12, Font.NORMAL);

            document.add(new Paragraph("Отчет о транзакциях за " + month, titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell(new Phrase("Категория", headerFont));
            table.addCell(new Phrase("Сумма", headerFont));
            table.addCell(new Phrase("Валюта", headerFont));

            for (Transaction t : transactions) {
                table.addCell(new Phrase(t.getCategory().getCategoryname(), bodyFont));
                table.addCell(new Phrase(String.valueOf(t.getAmount()), bodyFont));
                table.addCell(new Phrase(t.getCurrency(), bodyFont));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Конечный баланс:", headerFont));
            for (Map.Entry<String, Double> entry : balances.entrySet()) {
                document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка генерации pdf файла", e);
        }
    }
}