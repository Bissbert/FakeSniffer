package ch.bissbert.fakesniffer.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WebSocketConfigTest {

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    @Mock
    private MessageBrokerRegistry messageBrokerRegistry;

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Test
    public void testMessageBrokerConfig() {
        webSocketConfig.configureMessageBroker(messageBrokerRegistry);
        verify(messageBrokerRegistry).enableSimpleBroker("/topic", "/queue");
        verify(messageBrokerRegistry).setUserDestinationPrefix("/user");
        verify(messageBrokerRegistry).setApplicationDestinationPrefixes("/app");
    }

    @Test
    public void testRegisterStompEndpoints() {
        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);
        verify(stompEndpointRegistry).addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
}
