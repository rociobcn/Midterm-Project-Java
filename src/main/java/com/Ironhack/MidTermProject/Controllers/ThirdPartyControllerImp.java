package com.Ironhack.MidTermProject.Controllers;

import com.Ironhack.MidTermProject.Controllers.Implements.ThirdPartyController;
import com.Ironhack.MidTermProject.Services.Implements.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ThirdPartyControllerImp implements ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/third_party/sendTransfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendTransferForAccountHolder(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String hashedKey, @RequestParam long idAccountReceive, @RequestParam BigDecimal amount, @RequestParam String secretKey) {
        thirdPartyService.sendTransferForAccountHolder(userDetails,hashedKey,idAccountReceive, amount, secretKey);
    }
}
