package com.pallavi.quizapp.service;

import com.pallavi.quizapp.dao.QuestionDao;
import com.pallavi.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
