package org.shavneva.familybudget.reports.impl.docsGenerator;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.reports.TypeReportGenerator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class WordGenerator implements TypeReportGenerator {
    @Override
    public byte[] generateReport(List<Transaction> transactions, Map<String, Double> balances) {
        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        // Получаем месяц из первой транзакции
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        String month = monthFormat.format(transactions.get(0).getDate());

        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Заголовок отчета
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Отчет о транзакциях за " + month);
            titleRun.setFontFamily("Times New Roman");
            titleRun.setBold(true);
            titleRun.setFontSize(20);

            // Создание объединенной таблицы
            XWPFTable table = document.createTable();
            table.setWidth("100%");
            CTTblPr tblPr = table.getCTTbl().getTblPr();
            if (tblPr == null) tblPr = table.getCTTbl().addNewTblPr();
            CTJcTable jc = tblPr.addNewJc();
            jc.setVal(STJcTable.CENTER);

            // Заголовок таблицы
            XWPFTableRow headerRow = table.getRow(0);
            setCellText(headerRow.getCell(0), "Категория", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Сумма", 14, "Times New Roman");
            setCellText(headerRow.addNewTableCell(), "Валюта", 14, "Times New Roman");

            // Заполняем таблицу транзакциями
            for (Transaction transaction : transactions) {
                XWPFTableRow row = table.createRow();
                setCellText(row.getCell(0), transaction.getCategory().getCategoryname(), 13, "Times New Roman");
                setCellText(row.getCell(1), String.valueOf(transaction.getAmount()), 13, "Times New Roman");
                setCellText(row.getCell(2), transaction.getCurrency(), 13, "Times New Roman");
            }


            XWPFTableRow separatorRow = table.createRow();
            XWPFTableCell separatorCell = separatorRow.getCell(0);

            CTTcPr tcPr = separatorCell.getCTTc().addNewTcPr();
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(separatorRow.getTableCells().size()));
            tcPr.addNewHMerge().setVal(STMerge.RESTART);

            setCellText(separatorCell, "", 13, "Times New Roman");

            for (int i = 1; i < separatorRow.getTableCells().size(); i++) {
                XWPFTableCell cell = separatorRow.getCell(i);
                if (cell != null) {
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                }
            }

            for (Map.Entry<String, Double> entry : balances.entrySet()) {
                XWPFTableRow balanceRow = table.createRow();
                setCellText(balanceRow.getCell(0), "Конечный баланс", 13, "Times New Roman");
                setCellText(balanceRow.getCell(1), String.valueOf(entry.getValue()), 13, "Times New Roman"); // Сумма
                setCellText(balanceRow.getCell(2), entry.getKey(), 13, "Times New Roman"); // Валюта
            }

            document.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellText(XWPFTableCell cell, String text, int fontSize, String fontFamily) {
        XWPFRun run = cell.getParagraphs().get(0).createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        run.setFontFamily(fontFamily);
    }
}