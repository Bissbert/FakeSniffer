package ch.bissbert.fakesniffer.socket;

import ch.bissbert.fakesniffer.data.Report;
import ch.bissbert.fakesniffer.service.LamService;
import ch.bissbert.fakesniffer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Controller for the WebSocket.
 * It provides methods to handle voice input from the clients and evaluate the content of the conversations.
 * The content is evaluated using the LAM model.
 * The results are sent back to the clients using WebSocket.
 * @author Bissbert
 */
@Controller
public class WebSocketController {

    @Autowired
    private LamService lamService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SimpMessagingTemplate template;

    private final ConcurrentHashMap<Long, StringBuilder> activeConversations = new ConcurrentHashMap<>();

    /**
     * Handles the voice input from the clients.
     * The voice input is stored in the active conversations map.
     * @param voiceData The voice data.
     * @param headerAccessor The header accessor.
     */
    @MessageMapping("/sendVoice")
    public void handleVoiceInput(@Payload String voiceData, SimpMessageHeaderAccessor headerAccessor) {
        // Extract client ID from headers
        String clientIdStr = headerAccessor.getFirstNativeHeader("clientId");
        if (clientIdStr == null) {
            throw new IllegalStateException("Client ID must be provided");
        }
        Long clientId = Long.parseLong(clientIdStr);
        activeConversations.computeIfAbsent(clientId, k -> new StringBuilder()).append(voiceData);
    }

    /**
     * Evaluates the content of the active conversations.
     * The content is evaluated using the LAM model.
     * The results are sent back to the clients using WebSocket.
     * The evaluation is done periodically every second.
     */
    @Scheduled(fixedRate = 1000)
    public void evaluateContent() {
        activeConversations.forEach((clientId, conversation) -> {
            List<Report> historicalReports = reportService.getReportsForClient(clientId);
            String existingData = historicalReports.stream()
                    .map(Report::getContent)
                    .collect(Collectors.joining(" "));
            Double score = lamService.getContentScore(existingData, conversation.toString());
            // Using convertAndSendToUser requires user destinations to be configured
            // Assuming each client is subscribed to a unique destination like "/user/{clientId}/topic/ratings"
            template.convertAndSend("/topic/ratings/" + clientId, score);
        });
    }
}
