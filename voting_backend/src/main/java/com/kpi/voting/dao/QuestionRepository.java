package com.kpi.voting.dao;

import com.kpi.voting.dao.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll();

    Optional<Question> findTopByOrderByIdDesc();

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    Optional<Question> findById(Long id);

    @Query("SELECT question FROM Question question where question.form.id = ?1")
    List<Question> getQuestionsByFormId(Long id);

}
