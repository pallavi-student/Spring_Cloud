package com.pallavi.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    private String category;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @Column(name = "difficultylevel")
    private String difficultyLevel;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "right_answer")
    private String rightAnswer;

}
