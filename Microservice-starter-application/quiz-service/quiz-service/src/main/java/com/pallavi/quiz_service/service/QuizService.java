package com.pallavi.quiz_service.service;


import com.pallavi.quiz_service.dao.QuizDao;
import com.pallavi.quiz_service.feign.QuizInterface;
import com.pallavi.quiz_service.model.QuestionWrapper;
import com.pallavi.quiz_service.model.Quiz;
import com.pallavi.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
  @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String>createQuiz(String category,int numQuestion,String title) {
        if (numQuestion <= 0) {
            return new ResponseEntity<>("Number of questions must be greater than 0", HttpStatus.BAD_REQUEST);
        }
       List<Integer>questions=quizInterface.getQuestionForQuiz(category,numQuestion).getBody();
        if (questions == null || questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for the given category", HttpStatus.BAD_REQUEST);
        }
       Quiz quiz=new Quiz();
       quiz.setTitle(title);
       quiz.setQuestionIds(questions);
       quizDao.save(quiz);

        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>>getQuizQuestions(Integer id){
Quiz quiz=quizDao.findById(id).get();
List<Integer>questionIds=quiz.getQuestionIds();

ResponseEntity<List<QuestionWrapper>> questions=
        quizInterface.getQuestionsFromId((questionIds));

    return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        ResponseEntity<Integer>score=quizInterface.getScore(responses);
        return score;
    }
}

