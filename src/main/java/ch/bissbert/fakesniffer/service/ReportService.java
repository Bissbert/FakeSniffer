package ch.bissbert.fakesniffer.service;

import ch.bissbert.fakesniffer.data.Report;
import ch.bissbert.fakesniffer.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for the reports.
 * It provides methods to interact with the reports.
 * @author Bissbert
 */
@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getReportsForClient(Long clientId) {
        return reportRepository.findByClientId(clientId);
    }

    /**
     * Get the content of the reports for a client for a specific period.
     * The period is defined by the start month and the end month.
     * @param clientId The id of the client.
     * @param startMonth The start month of the period.
     * @param endMonth The end month of the period.
     * @return The content of the reports for the client for the period.
     */
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
