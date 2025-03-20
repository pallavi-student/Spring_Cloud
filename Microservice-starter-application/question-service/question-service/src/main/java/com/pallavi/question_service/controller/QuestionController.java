package com.pallavi.question_service.controller;


import com.pallavi.question_service.model.Question;
import com.pallavi.question_service.model.QuestionWrapper;
import com.pallavi.question_service.model.Response;
import com.pallavi.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class  QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    Environment environment;
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
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz
            (@RequestParam String categoryName, @RequestParam Integer numQuestion){
        return questionService.getQuestionForQuiz(categoryName,numQuestion);
    }
    @PostMapping("getquestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionFromIds(questionIds);
    }
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
       return questionService.getScore(responses);
        }


}
