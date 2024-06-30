package ch.bissbert.fakesniffer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void restTemplateBeanExists() {
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        assertNotNull(restTemplate, "RestTemplate bean should be available");
    }
}
