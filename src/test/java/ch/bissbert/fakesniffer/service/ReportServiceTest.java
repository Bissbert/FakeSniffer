package ch.bissbert.fakesniffer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ch.bissbert.fakesniffer.data.Report;
import ch.bissbert.fakesniffer.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    public void testGetReportsForClient() {
        // Arrange
        Long clientId = 1L;
        Report report1 = new Report();
        report1.setContent("Report1");
        Report report2 = new Report();
        report2.setContent("Report2");
        when(reportRepository.findByClientId(clientId)).thenReturn(Arrays.asList(report1, report2));

        // Act
        List<Report> reports = reportService.getReportsForClient(clientId);

        // Assert
        assertEquals(2, reports.size());
        verify(reportRepository).findByClientId(clientId);
    }

    @Test
    public void testGetReportsContentForPeriod() {
        // Arrange
        Long clientId = 1L;
        LocalDate now = LocalDate.now();
        Date startDate = Date.valueOf(now.minusMonths(8));
        Date endDate = Date.valueOf(now.minusMonths(4));
        Report report1 = new Report();
        report1.setContent("Report1");
        Report report2 = new Report();
        report2.setContent("Report2");
        when(reportRepository.findByClientIdAndDateCreatedBetween(eq(clientId), eq(startDate), eq(endDate)))
                .thenReturn(Arrays.asList(report1, report2));

        // Act
        String content = reportService.getReportsContentForPeriod(clientId, 4, 8);

        // Assert
        assertTrue(content.contains("Report1 Report2"));
        verify(reportRepository).findByClientIdAndDateCreatedBetween(eq(clientId), eq(startDate), eq(endDate));
    }
}
