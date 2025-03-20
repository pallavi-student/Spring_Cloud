package com.pallavi.quizapp.controller;

import com.pallavi.quizapp.model.Question;
import com.pallavi.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class  QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    // if category is cat in mapping then we need to mention it with pathvariable{cat}
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>>getQuestionByCategory(@PathVariable String category){

        return questionService.getQuestionByCategory(category);
    }
    @PostMapping("add")
    public ResponseEntity<String> AddQuestion(@RequestBody Question question){

        return questionService.AddQuestion(question);
    }
    @DeleteMapping("delete")
    public ResponseEntity<String> DeleteQuestion(@RequestBody Question question){

        return questionService.DeleteQuestion(question);
    }
    @PostMapping("update/{id}")
    public ResponseEntity<String> UpdateQuestion(@PathVariable Integer id,@RequestBody Question question){

        return questionService.UpdateQuestion(id, question);
    }

}
