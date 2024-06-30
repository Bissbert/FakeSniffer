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
public class LttmServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LttmService lttmService;

    @Test
    public void testGetTranscription() {
        // Arrange
        String voiceData = "Sample voice data";
        String responseBody = "{\"predictions\": [\"This is a transcription\"]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(eq("http://localhost:8501/v1/models/lttm:predict"), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // Act
        String result = lttmService.getTranscription(voiceData);

        // Assert
        assertNotNull(result);
        assertEquals("This is a transcription", result);
        verify(restTemplate).postForEntity(eq("http://localhost:8501/v1/models/lttm:predict"), any(HttpEntity.class), eq(String.class));
    }
}
