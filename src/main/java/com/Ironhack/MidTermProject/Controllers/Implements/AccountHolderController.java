package com.Ironhack.MidTermProject.Controllers.Implements;

import com.Ironhack.MidTermProject.Embeddables.Money;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

public interface AccountHolderController {
    Money getBalance(Long id, UserDetails userDetails);
    void sendTransfer(UserDetails userDetails, long idReceive, long idSend, BigDecimal amount, String key);

    void sendTransferWithFraudDetection(UserDetails userDetails, long idReceive, long idSend, BigDecimal amount, String key);
    void sendTransferToThirdParty(UserDetails userDetails, String userNameThirdParty, long idAccountFromSend, BigDecimal amount, String secretKey);
}
