package com.kpi.voting.domain;

import com.kpi.voting.dao.QuestionRepository;
import com.kpi.voting.dao.entity.Form;
import com.kpi.voting.dao.entity.Question;
import com.kpi.voting.dto.RequestQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.OperationNotSupportedException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private FormService formService;


    public Question getLastQuestion() {
        Optional<Question> question = questionRepository.findTopByOrderByIdDesc();
        return question.orElse(null);
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByFormId(Long id){
        return questionRepository.getQuestionsByFormId(id);
    }

    @Transactional
    public Question getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }


    public Boolean deleteAll(){
        questionRepository.deleteAll();
        return Boolean.TRUE;
    }

    public Boolean deleteById(Long id){
        questionRepository.deleteById(id);
        return Boolean.TRUE;
    }


    private boolean createQuestion(RequestQuestionDto question, Form form) {
        Question newQuestion = new Question();

        newQuestion.setOptions(question.getOptions());
        newQuestion.setTitle(question.getTitle());
        newQuestion.setType(question.getType());
        newQuestion.setForm(form);

        newQuestion = questionRepository.save(newQuestion);
        questionRepository.flush();

        return (newQuestion.getId() != null);
    }

    public void question(RequestQuestionDto question) throws Exception {

        Form form = formService.getFormByHash(question.getFormHash());
        if (Objects.isNull(form)) throw new OperationNotSupportedException("Form not found");

        boolean isQuestionCreated = createQuestion(question, form);
        if (!isQuestionCreated) throw new OperationNotSupportedException("Some troubles occurred.");
    }
}
