package com.Ironhack.MidTermProject.Controllers;

import com.Ironhack.MidTermProject.Controllers.Implements.AdminController;
import com.Ironhack.MidTermProject.DTO.CheckingDTO;
import com.Ironhack.MidTermProject.DTO.CreditCardDTO;
import com.Ironhack.MidTermProject.DTO.SavingDTO;
import com.Ironhack.MidTermProject.Models.Account.Account;
import com.Ironhack.MidTermProject.Models.Account.Checking;
import com.Ironhack.MidTermProject.Models.Account.CreditCard;
import com.Ironhack.MidTermProject.Models.Account.Saving;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;
import com.Ironhack.MidTermProject.Services.Implements.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class AdminControllerImpl implements AdminController {

    @Autowired
    AdminService adminService;


    @PostMapping("/admin/add_account_holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
        return adminService.addAccountHolder(accountHolder);
    }
    @PostMapping("/admin/add_third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody @Valid ThirdParty thirdParty) {
        return adminService.addThirdParty(thirdParty);
    }

    @PostMapping("/admin/add_checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody @Valid CheckingDTO checking) {
        return adminService.createCheckingAccount(checking);
    }

    @PostMapping("/admin/add_saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Saving createSaving(@RequestBody @Valid SavingDTO saving) {
        return adminService.createSaving(saving);
    }

    @PostMapping("/admin/add_credit_card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCard(@RequestBody @Valid CreditCardDTO creditCard) {
        return adminService.createCreditCard(creditCard);
    }

    @PatchMapping("/admin/modify_balance/{idAccount}")
    @ResponseStatus(HttpStatus.OK)
    public Account modifyBalance(@PathVariable long idAccount, @RequestParam BigDecimal balance) {
        return adminService.modifyBalance(idAccount,balance);
    }

    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct( @PathVariable long id) {
        adminService.deleteProduct(id);

    }


}
