package ch.bissbert.fakesniffer.repository;

import static org.mockito.Mockito.*;

import ch.bissbert.fakesniffer.data.Client;
import ch.bissbert.fakesniffer.data.Report;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReportRepositoryTest {

    @Mock
    private ReportRepository reportRepository;

    @Test
    public void testFindByClientId() {
        // Arrange
        Long clientId = 1L;
        Client client = new Client();
        client.setClientId(clientId);

        Report report1 = new Report();
        report1.setClient(client);
        Report report2 = new Report();
        report2.setClient(client);
        List<Report> reports = Arrays.asList(report1, report2);
        when(reportRepository.findByClientId(clientId)).thenReturn(reports);

        // Act
        List<Report> returnedReports = reportRepository.findByClientId(clientId);

        // Assert
        verify(reportRepository).findByClientId(clientId);
        assert returnedReports.size() == 2;
        assert returnedReports.equals(reports);
    }

    @Test
    public void testFindByClientIdAndDateCreatedBetween() {
        // Arrange
        Long clientId = 1L;
        Client client = new Client();
        client.setClientId(clientId);

        Date startDate = new Date(2021, 01, 01);
        Date endDate = new Date(2021, 12, 31);
        Report report1 = new Report();
        report1.setClient(client);
        Report report2 = new Report();
        report2.setClient(client);
        List<Report> reports = Arrays.asList(report1, report2);
        when(reportRepository.findByClientIdAndDateCreatedBetween(clientId, startDate, endDate)).thenReturn(reports);

        // Act
        List<Report> returnedReports = reportRepository.findByClientIdAndDateCreatedBetween(clientId, startDate, endDate);

        // Assert
        verify(reportRepository).findByClientIdAndDateCreatedBetween(clientId, startDate, endDate);
        assert returnedReports.size() == 2;
        assert returnedReports.equals(reports);
    }
}
