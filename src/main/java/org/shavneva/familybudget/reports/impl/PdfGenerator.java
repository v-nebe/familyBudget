package org.shavneva.familybudget.reports.impl;

import com.itextpdf.text.pdf.BaseFont;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PdfGenerator extends AbstractReportGenerator {


    protected PdfGenerator(TransactionService transactionService, BalanceService balanceService) {
        super(transactionService, balanceService);
    }

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.APPLICATION_PDF;
    }

    @Override
    public String getFileExtension() {
        return "pdf";
    }

    @Override
    public byte[] generateReport(ReportData data) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Загрузка шрифта Roboto
            BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font bodyFont = new Font(baseFont, 12, Font.NORMAL);

            document.add(new Paragraph("Отчет о транзакциях за " + data.month(), titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell(new Phrase("Категория", headerFont));
            table.addCell(new Phrase("Сумма", headerFont));
            table.addCell(new Phrase("Валюта", headerFont));

            for (Transaction t : data.transactions()) {
                table.addCell(new Phrase(t.getCategory().getCategoryname(), bodyFont));
                table.addCell(new Phrase(String.valueOf(t.getAmount()), bodyFont));
                table.addCell(new Phrase(t.getCurrency(), bodyFont));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Конечный баланс:", headerFont));
            for (Map.Entry<String, Double> entry : data.balances().entrySet()) {
                document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка генерации pdf файла", e);
        }
    }
}