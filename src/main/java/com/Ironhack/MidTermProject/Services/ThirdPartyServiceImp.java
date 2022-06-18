package com.Ironhack.MidTermProject.Services;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Enums.StatusTransferThirdParty;
import com.Ironhack.MidTermProject.Models.Account.Account;
import com.Ironhack.MidTermProject.Models.Account.Transfer;
import com.Ironhack.MidTermProject.Models.Account.TransferThirdParty;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;
import com.Ironhack.MidTermProject.Repositories.AccountHolderRepository;
import com.Ironhack.MidTermProject.Repositories.AccountRepository;
import com.Ironhack.MidTermProject.Repositories.ThirdPartyRepository;
import com.Ironhack.MidTermProject.Repositories.TransferThirdPartyRepository;
import com.Ironhack.MidTermProject.Services.Implements.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyServiceImp implements ThirdPartyService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    TransferThirdPartyRepository transferThirdPartyRepository;

    public void sendTransferForAccountHolder(UserDetails userDetails, String hashedKey, long idAccountReceive, BigDecimal amount, String secretKey) {
        ThirdParty verifyThirdParty;
        if (thirdPartyRepository.findByUsername(userDetails.getUsername()).isPresent()) {
            verifyThirdParty = thirdPartyRepository.findByUsername(userDetails.getUsername()).get();
            if (verifyThirdParty.getHashedKey().equals(hashedKey)) {
                Account accountOfReceive;
                if (accountRepository.findById(idAccountReceive).isPresent()){
                    accountOfReceive = accountRepository.findById(idAccountReceive).get();
                    TransferThirdParty transferThirdParty1 = new TransferThirdParty(new Money(amount), accountOfReceive, verifyThirdParty, StatusTransferThirdParty.SENDFROMTOTHIRDPARTYTOACCOUNTHOLDER);
                    transferThirdPartyRepository.save(transferThirdParty1);
                    accountOfReceive.setBalance(new Money(accountOfReceive.getBalance().getAmount().add(amount)));
                    accountRepository.save(accountOfReceive);
                } else{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account not exists");
                }
            } else{
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Your Hashed Key isn't correct");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Third Party not exists");
        }
    }

}
