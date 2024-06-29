package ch.bissbert.fakesniffer.service;

import ch.bissbert.fakesniffer.data.Report;
import ch.bissbert.fakesniffer.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getReportsForClient(Long clientId) {
        return reportRepository.findByClientId(clientId);
    }

    public String getReportsContentForPeriod(Long clientId, int startMonth, int endMonth) {
        LocalDate now = LocalDate.now();
        Date startDate = java.sql.Date.valueOf(now.minusMonths(endMonth));
        Date endDate = java.sql.Date.valueOf(now.minusMonths(startMonth));

        List<Report> reports = reportRepository.findByClientIdAndDateCreatedBetween(clientId, startDate, endDate);
        return reports.stream()
                .map(Report::getContent)
                .collect(Collectors.joining(" "));
    }
}
