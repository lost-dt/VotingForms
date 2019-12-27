package com.kpi.voting.domain;


import com.kpi.voting.dao.AdminUserRepository;
import com.kpi.voting.dao.entity.AdminUser;
import com.kpi.voting.dto.RequestAdminUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public void adminUser(RequestAdminUserDto adminUser) throws Exception {
        boolean UsernameExists = isUsernameExists(adminUser.getUsername());
        if (UsernameExists) throw new OperationNotSupportedException("This username is already used by someone.");

        boolean isAdminUserCreated = createAdminUser(adminUser);
        if (!isAdminUserCreated) throw new OperationNotSupportedException("Some troubles occurred.");
    }

    private boolean createAdminUser(RequestAdminUserDto adminUser) {
        AdminUser newAdminUser = new AdminUser();

        newAdminUser.setUsername(adminUser.getUsername());
        newAdminUser.setPassword(adminUser.getPassword());

        newAdminUser = adminUserRepository.save(newAdminUser);
        adminUserRepository.flush();

        return (newAdminUser.getId() != null);
    }

    private boolean isUsernameExists(String username) {
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        return adminUser.isPresent();
    }

    public List<AdminUser> getAllAdminUsers(){
        return adminUserRepository.findAll();
    }

    public AdminUser getAdminUserById(Long id){
        Optional<AdminUser> adminUser =  adminUserRepository.findById(id);
        return adminUser.orElse(null);
    }

    public AdminUser getAdminUserByUsername(String username){
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        return adminUser.orElse(null);
    }

    public Boolean deleteAllAdminUsers(){
        adminUserRepository.deleteAll();
        return Boolean.TRUE;
    }

    public Boolean deleteAdminUserById(Long id){
        adminUserRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public Boolean deleteAdminUserByUsername(String username){
        return adminUserRepository.deleteAdminUserByUsername(username);
    }

}
