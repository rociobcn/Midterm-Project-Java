package com.Ironhack.MidTermProject.Controllers.Implements;

import com.Ironhack.MidTermProject.DTO.CheckingDTO;
import com.Ironhack.MidTermProject.DTO.CreditCardDTO;
import com.Ironhack.MidTermProject.DTO.SavingDTO;
import com.Ironhack.MidTermProject.Models.Account.Account;
import com.Ironhack.MidTermProject.Models.Account.Checking;
import com.Ironhack.MidTermProject.Models.Account.CreditCard;
import com.Ironhack.MidTermProject.Models.Account.Saving;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;

import java.math.BigDecimal;

public interface AdminController {
    AccountHolder addAccountHolder(AccountHolder accountHolder);
    ThirdParty addThirdParty(ThirdParty thirdParty);
    Account createCheckingAccount(CheckingDTO checking);
    Saving createSaving(SavingDTO saving);
    CreditCard createCreditCard(CreditCardDTO creditCard);
    Account modifyBalance(long idAccount, BigDecimal balance);
    void deleteProduct(long id);
}
