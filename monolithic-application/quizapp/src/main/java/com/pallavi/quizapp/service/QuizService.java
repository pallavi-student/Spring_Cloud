package com.pallavi.quizapp.service;

import com.pallavi.quizapp.dao.QuestionDao;
import com.pallavi.quizapp.dao.QuizDao;
import com.pallavi.quizapp.model.Question;
import com.pallavi.quizapp.model.QuestionWrapper;
import com.pallavi.quizapp.model.Quiz;
import com.pallavi.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQues, String title) {
        List<Question> questions = questionDao.findRandomQuestionCategory(category, numQues);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
   questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz=quizDao.findById(id).get();
        List<Question>questions=quiz.getQuestions();
        int score=0;
        int itr=0;
        for(Response response:responses){
            for (Question question : questions) {
                if (question.getId().equals(response.getId())) {
                    if (question.getRightAnswer().equalsIgnoreCase(response.getResponse())) {
                        score++;
                    }
                    break; // No need to check remaining questions
                }
            }
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}

