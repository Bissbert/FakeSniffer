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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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

    @MockBean
    private ConcurrentHashMap<Long, StringBuilder> activeConversations;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        // Access the private field
        Field field = WebSocketController.class.getDeclaredField("activeConversations");
        field.setAccessible(true);
        activeConversations = (ConcurrentHashMap<Long, StringBuilder>) field.get(webSocketController);
        activeConversations.put(1L, new StringBuilder("Sample voice data"));
    }

    @Test
    public void testHandleVoiceInput() {
        // Create a mock for SimpMessageHeaderAccessor to allow verification
        SimpMessageHeaderAccessor headerAccessor = mock(SimpMessageHeaderAccessor.class);
        when(headerAccessor.getFirstNativeHeader("clientId")).thenReturn("1");
        String voiceData = "test voice data";

        // Call the method under test
        webSocketController.handleVoiceInput(voiceData, headerAccessor);

        // Verify that getFirstNativeHeader was called correctly
        verify(headerAccessor).getFirstNativeHeader("clientId");
    }

    @Test
    public void testEvaluateContent() {
        // Setup mock data
        Report report = new Report();
        report.setContent("Historical report content");
        when(reportService.getReportsForClient(1L)).thenReturn(Arrays.asList(report));

        when(lamService.getContentScore(anyString(), anyString())).thenReturn(0.85);

        webSocketController.evaluateContent();

        // Capture and assert the score sent to clients
        verify(template).convertAndSend(matches("/topic/ratings/1"), scoreCaptor.capture());
        assertEquals(0.85, scoreCaptor.getValue(), "Score should match mocked response.");
    }
}
