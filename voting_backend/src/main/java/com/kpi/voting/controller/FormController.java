package com.kpi.voting.controller;

import com.kpi.voting.dao.entity.Form;
import com.kpi.voting.domain.FormService;
import com.kpi.voting.dto.RequestFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("form")
public class FormController {

    @Autowired
    FormService formService;

    @GetMapping(value = "/id_{id}")
    public @ResponseBody
    Form getFormById(@PathVariable("id") Long id){
        return formService.getFormById(id);
    }

    @GetMapping(value = "all")
    public @ResponseBody
    List<Form> getAllForms(){
        return formService.getAllForms();
    }

    @GetMapping(value = "/hash_{hash}")
    public @ResponseBody
    Form getFormByHash(@PathVariable("hash") String hash){
        return formService.getFormByHash(hash);
    }

    @GetMapping(value = "/data_all")
    public @ResponseBody
    List<FormService.Response> getAllFormsData(){
        List<Form> forms = formService.getAllForms();
        return formService.getAllFormsData(forms);
    }

    @GetMapping(value = "/data_{hash}")
    public @ResponseBody
    FormService.Response getFormDataByHash(@PathVariable("hash") String hash){
        return formService.getFormDataByHash(hash);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> form(@Valid @RequestBody RequestFormDto form) {
        try {
            formService.form(form);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "all")
    public @ResponseBody
    Boolean deleteAllForms(){
        return formService.deleteAllForms();
    }

    @DeleteMapping(value = "/id_{id}")
    public @ResponseBody
    Boolean deleteFormById(@PathVariable("id") Long id){
        return formService.deleteFormById(id);
    }

    @DeleteMapping(value = "/hash_{hash}")
    public @ResponseBody
    Boolean deleteFormByHash(@PathVariable("hash") String hash){
        Form form = formService.getFormByHash(hash);
        return formService.deleteFormById(form.getId());
    }
}
