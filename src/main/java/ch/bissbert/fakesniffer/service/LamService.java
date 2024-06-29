package ch.bissbert.fakesniffer.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Service for the LAM model.
 * It provides methods to interact with the LAM model.
 * @author Bissbert
 */
@Service
public class LamService {
    private final RestTemplate restTemplate;
    private static final String LAM_URL = "http://localhost:8501/v1/models/lam:predict";

    @Autowired
    public LamService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gets the content score from the LAM model for the given existing data and active conversation.
     * @param existingData The existing data.
     * @param activeConversation The active conversation.
     * @return The content score.
     */
    public Double getContentScore(String existingData, String activeConversation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();
        requestBody.put("signature_name", "serving_default");
        requestBody.put("instances", Collections.singletonList(
                Map.of("existing_data", existingData, "active_conversation", activeConversation)
        ));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(LAM_URL, request, String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return jsonResponse.getJSONArray("predictions").getDouble(0);
    }
}

