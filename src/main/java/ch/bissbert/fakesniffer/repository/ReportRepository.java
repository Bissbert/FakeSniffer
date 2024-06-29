package ch.bissbert.fakesniffer.repository;

import ch.bissbert.fakesniffer.data.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByClientId(Long clientId);
    List<Report> findByClientIdAndDateCreatedBetween(Long clientId, Date startDate, Date endDate);
}
