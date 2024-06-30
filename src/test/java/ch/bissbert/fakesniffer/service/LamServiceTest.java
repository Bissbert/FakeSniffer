package ch.bissbert.fakesniffer.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class LamServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LamService lamService;

    @Test
    public void testGetContentScore() {
        // Arrange
        String existingData = "Existing Data";
        String activeConversation = "Active Conversation";
        String responseBody = "{\"predictions\": [0.85]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(eq("http://localhost:8501/v1/models/lam:predict"), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // Act
        Double result = lamService.getContentScore(existingData, activeConversation);

        // Assert
        assertNotNull(result);
        assertEquals(0.85, result, 0.001);
        verify(restTemplate).postForEntity(eq("http://localhost:8501/v1/models/lam:predict"), any(HttpEntity.class), eq(String.class));
    }
}
