package ch.bissbert.fakesniffer.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import ch.bissbert.fakesniffer.service.QuestionGenerationService;
import ch.bissbert.fakesniffer.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @Mock
    private QuestionGenerationService questionGenerationService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    public void testGenerateQuestionsForClient() throws Exception {
        Long clientId = 1L;
        String reportsContent = "Some report content";
        List<String> mockQuestions = Arrays.asList("What is your name?", "Where do you live?");
        when(reportService.getReportsContentForPeriod(clientId, 4, 8)).thenReturn(reportsContent);
        when(questionGenerationService.generateQuestions(reportsContent)).thenReturn(mockQuestions);

        mockMvc.perform(get("/api/questions/generate/{clientId}", clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("What is your name?"))
                .andExpect(jsonPath("$[1]").value("Where do you live?"));

        verify(reportService).getReportsContentForPeriod(clientId, 4, 8);
        verify(questionGenerationService).generateQuestions(reportsContent);
    }
}
