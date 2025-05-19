package org.shavneva.familybudget.reports;

import org.shavneva.familybudget.reports.impl.ReportData;
import org.springframework.http.MediaType;

public interface ReportGenerator {
    MediaType getSupportedMediaType();
    String getFileExtension();
}
