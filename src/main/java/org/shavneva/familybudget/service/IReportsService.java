package org.shavneva.familybudget.service;

import org.shavneva.familybudget.reports.ReportWrapper;
import org.springframework.http.MediaType;

public interface IReportsService {
    ReportWrapper generateReport(String username, String date, String currency, MediaType mediaType);
}
