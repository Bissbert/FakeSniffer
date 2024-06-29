package ch.bissbert.fakesniffer.repository;

import ch.bissbert.fakesniffer.data.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
