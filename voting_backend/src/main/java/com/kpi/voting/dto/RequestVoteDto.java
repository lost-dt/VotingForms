package com.kpi.voting.dto;

import javax.validation.constraints.NotNull;

public class RequestVoteDto {

    @NotNull
    private String answer;

    @NotNull
    private Long questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
