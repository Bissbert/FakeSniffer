package ch.bissbert.fakesniffer.repository;

import static org.mockito.Mockito.*;

import ch.bissbert.fakesniffer.data.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void testFindById() {
        // Arrange
        Long id = 1L;
        Client mockClient = new Client();
        mockClient.setClientId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(mockClient));

        // Act
        Optional<Client> returnedClient = clientRepository.findById(id);

        // Assert
        verify(clientRepository).findById(id);
        assert returnedClient.isPresent();
        assert returnedClient.get().equals(mockClient);
    }
}
