package com.pallavi.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private Integer numQuestion;
    private String title;
}
