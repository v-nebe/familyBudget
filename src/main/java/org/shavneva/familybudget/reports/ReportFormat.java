package org.shavneva.familybudget.reports;

import org.springframework.http.MediaType;

public interface ReportFormat {
    MediaType getSupportedMediaType();
    String getFileExtension();
}
