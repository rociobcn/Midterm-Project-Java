package com.Ironhack.MidTermProject.Controllers;

import com.Ironhack.MidTermProject.Controllers.Implements.AccountHolderController;
import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Services.Implements.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@RestController
public class AccountHolderControllerImp implements AccountHolderController {

   @Autowired
   AccountHolderService accountHolderService;


    @GetMapping("/account_holder/get-balance/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Money getBalance(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return accountHolderService.getBalance(id, userDetails);
    }

    @PostMapping("/account_holder/sendTransfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendTransfer(@AuthenticationPrincipal  UserDetails userDetails, @RequestParam long idReceive, @RequestParam long idSend, @RequestParam BigDecimal amount, @RequestParam String key) {
       accountHolderService.sendTransfer(userDetails, idReceive,idSend,amount, key);
    }

    @PostMapping("/account_holder/sendTransferFraud")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendTransferWithFraudDetection(@AuthenticationPrincipal  UserDetails userDetails, @RequestParam long idReceive, @RequestParam long idSend, @RequestParam BigDecimal amount, @RequestParam String key) {
        accountHolderService.sendTransferWithFraudDetection(userDetails, idReceive,idSend,amount, key);
    }

    @PostMapping("/account_holder/send_transfer_third_party")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendTransferToThirdParty(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String userNameThirdParty, @RequestParam long idAccountFromSend, @RequestParam BigDecimal amount, @RequestParam String secretKey) {
        accountHolderService.sendTransferToThirdParty(userDetails,userNameThirdParty, idAccountFromSend, amount,secretKey);
    }

}
