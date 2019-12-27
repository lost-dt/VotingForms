package com.kpi.voting.domain;

import com.kpi.voting.dao.VoteRepository;
import com.kpi.voting.dao.entity.Form;
import com.kpi.voting.dao.entity.Question;
import com.kpi.voting.dto.RequestVoteDto;
import com.kpi.voting.dao.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import java.util.*;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private QuestionService questionService;

    public void vote(RequestVoteDto vote) throws Exception {
        Question question = questionService.getQuestion(vote.getQuestionId());
        if (Objects.isNull(question)) throw new OperationNotSupportedException("Question not found.");

        boolean isVoteCreated = createVote(vote, question);
        if (!isVoteCreated) throw new OperationNotSupportedException("Some troubles occurred.");
    }

    private boolean createVote(RequestVoteDto vote, Question question) {
        Vote newVote = new Vote();

        newVote.setQuestion(question);
        newVote.setAnswer(vote.getAnswer());

        newVote = voteRepository.save(newVote);
        voteRepository.flush();

        return (newVote.getId() != null);
    }


    public Collection<String> getAnswer(Long id){
        return voteRepository.getAnswer(id);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public String getOptions(Long id){
        Question question = questionService.getQuestion(id);
        return question.getOptions();
    }

    //Class for stats response
    public class StatsInfo{
        private String title;
        private String type;
        private Long id;
        private Map<String, Integer> stats;

        public StatsInfo(String title, String type, Map<String, Integer> stats) {
            this.title = title;
            this.type = type;
            this.stats = stats;
        }

        public StatsInfo() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Integer> getStats() {
            return stats;
        }

        public void setStats(Map<String, Integer> stats) {
            this.stats = stats;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public class FormResponse{
        private String name;
        private List<StatsInfo> stats;

        public FormResponse(String name, List<StatsInfo> stats) {
            this.name = name;
            this.stats = stats;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<StatsInfo> getStats() {
            return stats;
        }

        public void setStats(List<StatsInfo> stats) {
            this.stats = stats;
        }
    }


    public StatsInfo getStats(Long id){
        StatsInfo statsInfo = new StatsInfo();
        HashMap<String, Integer> stats = new HashMap<String, Integer>();
        Question question = questionService.getQuestion(id);
        Collection<String> answers = getAnswer(id);
        String options = getOptions(id);
        String[] optionsList = options.split(", ");
        for (int i = 0; i < optionsList.length; i++) {
            int counter = 0;
            for (String answer: answers){
                if(Arrays.asList(answer.split(", ")).contains(optionsList[i])){
                    counter++;
                }
            }
            stats.put(optionsList[i], counter);
        }
        statsInfo.setStats(stats);
        statsInfo.setTitle(question.getTitle());
        statsInfo.setType(question.getType());
        statsInfo.setId(id);
        return statsInfo;
    }

    public List<StatsInfo> getAllStats(){
        List<StatsInfo> allStats = new ArrayList<>();
        List<Question> questions = questionService.getAllQuestions();
        for (Question question: questions){
            if(!question.getType().equals("text")) {
                allStats.add(getStats(question.getId()));
            }
        }
        return allStats;
    }

    public List<StatsInfo> getStatsForForm(Form form){
        List<StatsInfo> allStats = new ArrayList<>();
        List<Question> questions = questionService.getQuestionsByFormId(form.getId());
        for (Question question: questions){
            if(!question.getType().equals("text")) {
                allStats.add(getStats(question.getId()));
            }
        }
        return allStats;
    }

    public FormResponse getFormStats(Form form){
        return new FormResponse(form.getTitle(), getStatsForForm(form));
    }
}
