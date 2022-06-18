package com.Ironhack.MidTermProject.Services.Implements;

import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

public interface ThirdPartyService {
    void sendTransferForAccountHolder(UserDetails userDetails, String hashedKey, long idAccountReceive, BigDecimal amount, String secretKey);
}
