package com.Ironhack.MidTermProject.Controllers.Implements;

import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

public interface ThirdPartyController {
    void sendTransferForAccountHolder(UserDetails userDetails, String hashedKey, long idAccountReceive, BigDecimal amount, String secretKey);
}
