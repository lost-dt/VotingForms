package com.kpi.voting.controller;

import com.kpi.voting.dao.entity.Question;
import com.kpi.voting.domain.QuestionService;
import com.kpi.voting.dto.RequestQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "last")
    public @ResponseBody
    Question getLastQuestion() {
        return questionService.getLastQuestion();
    }

    @GetMapping(value = "all")
    public @ResponseBody
    List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Question getOneQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestion(id);
    }

    @GetMapping(value = "/form_{id}")
    public @ResponseBody
    List<Question> getQuestionsByFormId(@PathVariable("id") Long id){
        return questionService.getQuestionsByFormId(id);
    }


    @DeleteMapping(value = "all")
    public @ResponseBody
    Boolean deleteAllQuestions(){
        return questionService.deleteAll();
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    Boolean deleteOneQuestion(@PathVariable("id") Long id){
        return questionService.deleteById(id);
    }


    @PostMapping(produces = "application/json")
    public ResponseEntity<?> question(@Valid @RequestBody RequestQuestionDto question) {
        try {
            questionService.question(question);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
