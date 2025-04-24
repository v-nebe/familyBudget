package org.shavneva.familybudget.service.impl;

import com.itextpdf.text.pdf.BaseFont;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.ReportGenerator;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class PdfGenerator implements ReportGenerator {
    @Override
    public byte[] generateReport(List<Transaction> transactionList, Map<String, Double> balances) {
        if (transactionList.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        String month = monthFormat.format(transactionList.get(0).getDate());

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

            for (Transaction t : transactionList) {
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