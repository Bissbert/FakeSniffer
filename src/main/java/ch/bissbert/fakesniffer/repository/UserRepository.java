package ch.bissbert.fakesniffer.repository;

import ch.bissbert.fakesniffer.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the users.
 * It provides methods to interact with the users in the database.
 * @author Bissbert
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
