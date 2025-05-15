package org.shavneva.familybudget.reports;

import org.springframework.http.MediaType;

public interface ReportGenerator {
    MediaType getSupportedMediaType();
    String getFileExtension();
    Object[] prepareReportData(String username, String date, String currency);
    byte[] generateReport(String username, String date, String currency);

}
