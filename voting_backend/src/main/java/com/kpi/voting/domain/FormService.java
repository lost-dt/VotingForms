package com.kpi.voting.domain;


import com.google.common.hash.Hashing;
import com.kpi.voting.dao.FormRepository;
import com.kpi.voting.dao.entity.Form;
import com.kpi.voting.dao.entity.Question;
import com.kpi.voting.dto.RequestFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    FormRepository formRepository;
    @Autowired
    QuestionService questionService;

    public Form getFormById(Long id){
        Optional<Form> form =  formRepository.findById(id);
        return form.orElse(null);
    }

    public Form getFormByHash(String hash){
        Optional<Form> form = formRepository.findByHash(hash);
        return form.orElse(null);
    }

    public List<Form> getAllForms(){
        return formRepository.findAll();
    }

    public boolean deleteFormById(Long id){
        formRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public boolean deleteAllForms(){
        formRepository.deleteAll();
        return Boolean.TRUE;
    }


    public class QuestionElement{
        public Long id;
        public String title;
        public String type;
        public String options;
        public String hash;

        public QuestionElement(Long id, String title, String type, String options, String hash) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.options = options;
            this.hash = hash;
        }

        public QuestionElement() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }

    public class Response{
        public String title;
        public String hash;
        public List<QuestionElement> questions;

        public Response(String title, String hash, List<QuestionElement> questions) {
            this.title = title;
            this.hash = hash;
            this.questions = questions;
        }

        public Response() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public List<QuestionElement> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionElement> questions) {
            this.questions = questions;
        }
    }


    public QuestionElement getQuestionData(Question question){
        return new QuestionElement(question.getId(), question.getTitle(), question.getType(),
                question.getOptions(), question.getForm().getHash());
    }

    public Response getFormData(Form form){
        List<QuestionElement> questionElements = new ArrayList<>();
        List<Question> questions = questionService.getQuestionsByFormId(form.getId());
        for(Question question: questions){
            questionElements.add(getQuestionData(question));
        }
        return new Response(form.getTitle(), form.getHash(), questionElements);
    }

    public List<Response> getAllFormsData(List<Form> forms){
        List <Response> response = new ArrayList<>();
        for (Form form: forms){
            response.add(getFormData(form));
        }
        return response;
    }

    public Response getFormDataByHash(String hash){
        Form form = getFormByHash(hash);
        return getFormData(form);
    }

    private boolean createForm(RequestFormDto form){
        Form newForm = new Form();

        newForm.setTitle(form.getTitle());

        newForm.setHash("");
        newForm = formRepository.save(newForm);
        String sha256hex = Hashing.sha256()
                .hashString(form.getTitle() + newForm.getId(), StandardCharsets.UTF_8)
                .toString();
        newForm.setHash(sha256hex);
        newForm = formRepository.save(newForm);
        formRepository.flush();

        return (newForm.getId() != null);
    }

    public void form(RequestFormDto form) throws Exception{

        boolean isFormCreated = createForm(form);
        if (!isFormCreated) throw new OperationNotSupportedException("Some troubles occurred.");
    }


}
