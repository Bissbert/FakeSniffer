package ch.bissbert.fakesniffer.repository;

import ch.bissbert.fakesniffer.data.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the clients.
 * It provides methods to interact with the clients in the database.
 * @author Bissbert
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
