package com.pallavi.quiz_service.controller;

import com.pallavi.quiz_service.model.QuestionWrapper;
import com.pallavi.quiz_service.model.QuizDto;
import com.pallavi.quiz_service.model.Response;
import com.pallavi.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        if (quizDto.getNumQuestion() <= 0) {
            return ResponseEntity.badRequest().body("Number of questions must be greater than 0");
        }
        if (quizDto.getCategory() == null || quizDto.getCategory().isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be empty");
        }
        if (quizDto.getTitle() == null || quizDto.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Quiz title cannot be empty");
        }

        return quizService.createQuiz(quizDto.getCategory(),quizDto.getNumQuestion(),quizDto.getTitle());
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
      return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer>calculateResult(@PathVariable Integer id,@RequestBody List<Response>responses){
        return quizService.calculateResult(id,responses);
    }

}
