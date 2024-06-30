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

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class QuestionGenerationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private QuestionGenerationService questionGenerationService;

    @Test
    public void testGenerateQuestions() {
        // Arrange
        String reportContent = "Example report content";
        String responseBody = "{\"predictions\": [\"What is your name?\", \"Where do you live?\"]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(eq("http://localhost:8501/v1/models/question_model:predict"), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // Act
        List<String> questions = questionGenerationService.generateQuestions(reportContent);

        // Assert
        assertNotNull(questions);
        assertEquals(2, questions.size());
        assertTrue(questions.contains("What is your name?"));
        assertTrue(questions.contains("Where do you live?"));
        verify(restTemplate).postForEntity(eq("http://localhost:8501/v1/models/question_model:predict"), any(HttpEntity.class), eq(String.class));
    }
}
