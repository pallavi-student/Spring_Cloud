package com.pallavi.question_service.dao;


import com.pallavi.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
     List<Question> findByCategory(String category);
//NativeQuery that is JPQL Query
    @Query(value = "Select q.id  from question q where q.category=:category Order By Rand() Limit :numQues",nativeQuery=true)

    List<Integer> findRandomQuestionCategory(String category, int numQues);
}
