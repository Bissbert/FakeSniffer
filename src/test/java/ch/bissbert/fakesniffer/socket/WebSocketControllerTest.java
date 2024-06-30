package ch.bissbert.fakesniffer.socket;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import ch.bissbert.fakesniffer.data.Report;
import ch.bissbert.fakesniffer.service.LamService;
import ch.bissbert.fakesniffer.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WebSocketController.class)
public class WebSocketControllerTest {

    @MockBean
    private LamService lamService;

    @MockBean
    private ReportService reportService;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private WebSocketController webSocketController;

    @Captor
    private ArgumentCaptor<Double> scoreCaptor;

    @BeforeEach
    public void setup() {
        // This setup can be used to initialize any necessary data or configurations
    }

    @Test
    public void testHandleVoiceInput() {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setNativeHeader("clientId", "1");
        String voiceData = "test voice data";

        webSocketController.handleVoiceInput(voiceData, headerAccessor);

        // Verify internal state changes or interactions
        verify(headerAccessor).getFirstNativeHeader("clientId");
        // Additional assertions can be added here
    }

    @Test
    public void testEvaluateContent() {
        // Setup mock data
        Report report = new Report();
        report.setContent("Historical report content");
        when(reportService.getReportsForClient(anyLong())).thenReturn(Arrays.asList(report));

        when(lamService.getContentScore(anyString(), anyString())).thenReturn(0.85);

        webSocketController.evaluateContent();

        // Capture and assert the score sent to clients
        verify(template).convertAndSend(matches("/topic/ratings/1"), scoreCaptor.capture());
        assertEquals(0.85, scoreCaptor.getValue());
    }
}
