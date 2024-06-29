package ch.bissbert.fakesniffer.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class for the users.
 * It represents the users in the database.
 * @author Bissbert
 */
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String role;

    // Constructors, getters, and setters
    public User() {}

}
