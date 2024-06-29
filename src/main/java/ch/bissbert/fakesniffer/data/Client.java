package ch.bissbert.fakesniffer.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String name;
    private String email;
    private String phoneNumber;

    // Constructors, getters, and setters
    public Client() {}

}

