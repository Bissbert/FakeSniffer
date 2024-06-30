package ch.bissbert.fakesniffer.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void restTemplateBeanCreated() {
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        assertThat(restTemplate).isNotNull();
    }
}
