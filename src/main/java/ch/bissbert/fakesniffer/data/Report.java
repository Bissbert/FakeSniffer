package ch.bissbert.fakesniffer.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class for the reports.
 * It represents the reports in the database.
 * @author Bissbert
 */
@Setter
@Getter
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Date dateCreated;
    private String content;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors, getters, and setters
    public Report() {}

}

