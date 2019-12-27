package com.kpi.voting.dao;

import com.kpi.voting.dao.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    Optional<Form> findByHash(String hash);
}
