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

@Service
public class LttmService {
    private final RestTemplate restTemplate;
    private static final String LTTM_URL = "http://localhost:8501/v1/models/lttm:predict";

    @Autowired
    public LttmService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTranscription(String voiceData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();
        requestBody.put("signature_name", "serving_default");
        requestBody.put("instances", Collections.singletonList(Collections.singletonMap("voice_data", voiceData)));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(LTTM_URL, request, String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return jsonResponse.getJSONArray("predictions").getString(0);
    }
}

