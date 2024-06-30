package ch.bissbert.fakesniffer.repository;

import static org.mockito.Mockito.*;

import ch.bissbert.fakesniffer.data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        // Arrange
        Long id = 1L;
        User mockUser = new User();
        mockUser.setUserId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> returnedUser = userRepository.findById(id);

        // Assert
        verify(userRepository).findById(id);
        assert returnedUser.isPresent();
        assert returnedUser.get().equals(mockUser);
    }
}
