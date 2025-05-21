package org.shavneva.familybudget.reports;

import org.springframework.http.MediaType;

public interface ReportGenerator {
    MediaType getSupportedMediaType();
    String getFileExtension();
}
