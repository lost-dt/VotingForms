package com.kpi.voting.controller;

import com.kpi.voting.dao.entity.Form;
import com.kpi.voting.dao.entity.Vote;
import com.kpi.voting.domain.FormService;
import com.kpi.voting.domain.VoteService;
import com.kpi.voting.dto.RequestVoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private FormService formService;

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Collection<String> getAnswer(@PathVariable("id") Long id){
        return voteService.getAnswer(id);
    }

    @GetMapping(value = "all")
    public @ResponseBody
    List<Vote> getAllVotes(){
        return voteService.getAllVotes();
    }

/*    @GetMapping(value = "stats_{id}")
    public @ResponseBody
    VoteService.StatsInfo getStats(@PathVariable("id") Long id){
        return voteService.getStats(id);
    }*/

    @GetMapping(value = "stats_{hash}")
    public @ResponseBody
    VoteService.FormResponse getFormStats(@PathVariable("hash") String hash) {
        Form form = formService.getFormByHash(hash);
        return voteService.getFormStats(form);
    }

    @GetMapping(value = "stats_all")
    public @ResponseBody
    List<VoteService.StatsInfo> getAllStats(){
        return voteService.getAllStats();
    }


    @PostMapping(produces = "application/json")
    public ResponseEntity<?> answer(@Valid @RequestBody RequestVoteDto vote) {
        try {
            voteService.vote(vote);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
