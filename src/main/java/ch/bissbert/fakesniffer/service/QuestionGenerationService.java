package ch.bissbert.fakesniffer.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuestionGenerationService {

    private final RestTemplate restTemplate;
    private static final String QUESTION_MODEL_URL = "http://localhost:8501/v1/models/question_model:predict";

    @Autowired
    public QuestionGenerationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> generateQuestions(String reportContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();
        requestBody.put("signature_name", "serving_default");
        requestBody.put("instances", Collections.singletonList(Collections.singletonMap("report_content", reportContent)));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(QUESTION_MODEL_URL, request, String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray questions = jsonResponse.getJSONArray("predictions");

        return IntStream.range(0, questions.length())
                .mapToObj(questions::getString)
                .collect(Collectors.toList());
    }
}

