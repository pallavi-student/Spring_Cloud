package com.pallavi.question_service.service;


import com.pallavi.question_service.dao.QuestionDao;
import com.pallavi.question_service.model.Question;
import com.pallavi.question_service.model.QuestionWrapper;
import com.pallavi.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
@Autowired
QuestionDao questiondao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
        return new ResponseEntity<>(questiondao.findByCategory(category),HttpStatus.OK);}
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questiondao.findByCategory(category),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String>AddQuestion(Question question) {
         questiondao.save(question);
         return new ResponseEntity<>("success",HttpStatus.CREATED);
    }
    public ResponseEntity<String> DeleteQuestion(Question question) {
        questiondao.delete(question);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
    public  ResponseEntity<String> UpdateQuestion(Integer id,Question updatedQuestion) {
        Question existingQuestion = questiondao.findById(id).orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));

        existingQuestion.setCategory(updatedQuestion.getCategory());
        existingQuestion.setOption1(updatedQuestion.getOption1());
        existingQuestion.setOption2(updatedQuestion.getOption2());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption4(updatedQuestion.getOption4());
        existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
        existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
        existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
        questiondao.save(existingQuestion);
        return new ResponseEntity<>("Question updated successfully",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestion) {
        List<Integer> question=questiondao.findRandomQuestionCategory(categoryName,numQuestion);
        return new ResponseEntity<>(question,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(List<Integer> questionIds) {
        List<Question>questions=new ArrayList<>();
        for(Integer id:questionIds){
            questions.add(questiondao.findById(id).get());
        }List<QuestionWrapper>wrapper=new ArrayList<>();
        for(Question question:questions){
            QuestionWrapper qw=new QuestionWrapper(question.getId(),question.getQuestionTitle(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4());
            wrapper.add(qw);
        }
        return new ResponseEntity<>(wrapper,HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score=0;
        for(Response response:responses) {
            String ans = questiondao.findById(response.getId()).get().getRightAnswer();
            if (response.getResponse().equalsIgnoreCase(ans)) {
                score++;
            }
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
