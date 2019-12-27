package com.kpi.voting.dto;

import javax.validation.constraints.NotNull;

public class RequestFormDto {

    @NotNull
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
