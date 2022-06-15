package com.Ironhack.MidTermProject.Controllers;

import com.Ironhack.MidTermProject.Controllers.Implements.AdminController;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Services.Implements.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminControllerImpl implements AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/add_account_holder/")
    public String securityWorks(){
        return "Yes";
    }


    @PostMapping("/admin/add_account_holder/other")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
        return adminService.addAccountHolder(accountHolder);
    }



}
