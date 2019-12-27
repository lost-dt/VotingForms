package com.kpi.voting.dao;


import com.kpi.voting.dao.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    List<AdminUser> findAll();

    Optional<AdminUser> findByUsername(String username);

    Optional<AdminUser> findById(Long id);

    Boolean deleteAdminUserByUsername(String username);

}
