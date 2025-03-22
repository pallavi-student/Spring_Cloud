package com.pallavi.quiz_service.feign;

import com.pallavi.quiz_service.model.QuestionWrapper;
import com.pallavi.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("/question/generate")
    @CircuitBreaker(name = "quizServiceBreaker", fallbackMethod = "questionFallback")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestion);

    @PostMapping("/question/getquestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

    // Fallback method for Feign
    default ResponseEntity<List<Integer>> questionFallback(String categoryName, Integer numQuestion, Exception e) {
        System.out.println("Fallback for quiz Feign: " + e.getMessage());
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}