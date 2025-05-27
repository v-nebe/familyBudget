package org.shavneva.familybudget.controller.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.controller.IReportsController;
import org.shavneva.familybudget.reports.ReportWrapper;
import org.shavneva.familybudget.service.impl.ReportsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportsController implements IReportsController {
    private final ReportsService reportsService;

    @Override
    public ResponseEntity<byte[]> downloadWordFile(String nickname,
                                                   String date,
                                                   String currency,
                                                   String acceptHeader){

       MediaType mediaType = MediaType.parseMediaType(acceptHeader);
       ReportWrapper report = reportsService.generateReport(nickname, date, currency, mediaType);
       String filename = "report." + report.fileExtension;

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(mediaType)
                .body(report.data);

    }
}
