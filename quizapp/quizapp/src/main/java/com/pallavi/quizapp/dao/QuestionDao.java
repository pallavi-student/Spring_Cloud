package com.pallavi.quizapp.dao;

import com.pallavi.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
     List<Question> findByCategory(String category);
//NativeQuery that is JPQL Query
    @Query(value = "Select *from question q where q.category=:category Order By Rand() Limit :numQues",nativeQuery=true)

    List<Question> findRandomQuestionCategory(String category, int numQues);
}
