package ch.bissbert.fakesniffer.repository;

import ch.bissbert.fakesniffer.data.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository for the reports.
 * It provides methods to interact with the reports in the database.
 * @author Bissbert
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    /**
     * Find all reports by the client id.
     * @param clientId The id of the client.
     * @return A list of reports.
     */
    List<Report> findByClientId(Long clientId);

    /**
     * Find all reports by the client id and the date created between the start date and the end date.
     * @param clientId The id of the client.
     * @param startDate The start date.
     * @param endDate The end date.
     * @return A list of reports.
     */
    List<Report> findByClientIdAndDateCreatedBetween(Long clientId, Date startDate, Date endDate);
}
