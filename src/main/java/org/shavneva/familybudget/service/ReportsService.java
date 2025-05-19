package org.shavneva.familybudget.service;

import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.reports.ReportWrapper;
import org.shavneva.familybudget.reports.impl.AbstractReportGenerator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    private final Map<MediaType, AbstractReportGenerator> generatorMap;

    public ReportsService(List<AbstractReportGenerator> generators) {
        this.generatorMap = generators.stream()
                .collect(Collectors.toMap(ReportGenerator::getSupportedMediaType, Function.identity()));
    }

    public ReportWrapper generateReport(String username, String date, String currency, MediaType mediaType) {
        AbstractReportGenerator generator = generatorMap.get(mediaType);
        byte[] fileBytes = generator.prepareReportData(username, date, currency);
        return new ReportWrapper(fileBytes, generator.getFileExtension(), mediaType);
    }

}
