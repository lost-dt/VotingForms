package com.kpi.voting.controller;


import com.kpi.voting.dao.entity.AdminUser;
import com.kpi.voting.domain.AdminUserService;
import com.kpi.voting.dto.RequestAdminUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin_user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping(value = "all")
    public @ResponseBody
    List<AdminUser> getAllAdminusers(){
        return adminUserService.getAllAdminUsers();
    }

    @GetMapping(value = "/id_{id}")
    public @ResponseBody
    AdminUser getAdminUserById(@PathVariable("id") Long id) {
        return adminUserService.getAdminUserById(id);
    }

    @GetMapping(value = "/username_{username}")
    public @ResponseBody
    AdminUser getAdminUserByUsername(@PathVariable("username") String username){
        return adminUserService.getAdminUserByUsername(username);
    }

    @DeleteMapping(value = "all")
    public @ResponseBody
    Boolean deleteAllAdminUsers(){
        return adminUserService.deleteAllAdminUsers();
    }


    @DeleteMapping(value = "/id_{id}")
    public @ResponseBody
    Boolean deleteAdminUserById(@PathVariable("id") Long id){
        return adminUserService.deleteAdminUserById(id);
    }

    @DeleteMapping(value = "/username_{username}")
    public @ResponseBody
    Boolean deleteAdminUserByUsername(@PathVariable("username") String username){
        AdminUser adminUser = adminUserService.getAdminUserByUsername(username);
        return adminUserService.deleteAdminUserById(adminUser.getId());
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> adminUser(@Valid @RequestBody RequestAdminUserDto adminUser){
        try {
            adminUserService.adminUser(adminUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
