package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;

import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.reports.ReportWrapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    private final Map<MediaType, ReportGenerator> generatorMap;

    public ReportsService(List<ReportGenerator> generators) {
        this.generatorMap = generators.stream()
                .collect(Collectors.toMap(ReportGenerator::getSupportedMediaType, Function.identity()));
    }

    public ReportWrapper generateReport(String username, String date, String currency, MediaType mediaType) {
        ReportGenerator reportGenerator = generatorMap.get(mediaType);
        byte[] data = reportGenerator.generateReport(username, date, currency);
        return  new ReportWrapper(data, reportGenerator.getFileExtension(), mediaType);
    }

}
