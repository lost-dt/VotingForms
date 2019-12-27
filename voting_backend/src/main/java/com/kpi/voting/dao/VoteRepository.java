package com.kpi.voting.dao;

import com.kpi.voting.dao.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAll();

    @Query("SELECT answer FROM Vote vote where vote.question.id = ?1")
    Collection<String> getAnswer(Long id);
}
