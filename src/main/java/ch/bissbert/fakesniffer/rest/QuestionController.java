package ch.bissbert.fakesniffer.rest;

import ch.bissbert.fakesniffer.service.QuestionGenerationService;
import ch.bissbert.fakesniffer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private QuestionGenerationService questionGenerationService;

    @GetMapping("/generate/{clientId}")
    public ResponseEntity<List<String>> generateQuestionsForClient(@PathVariable Long clientId) {
        try {
            String reportsContent = reportService.getReportsContentForPeriod(clientId, 4, 8);
            List<String> questions = questionGenerationService.generateQuestions(reportsContent);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

