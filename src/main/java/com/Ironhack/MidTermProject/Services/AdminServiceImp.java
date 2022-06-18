package com.Ironhack.MidTermProject.Services;

import com.Ironhack.MidTermProject.DTO.CheckingDTO;
import com.Ironhack.MidTermProject.DTO.CreditCardDTO;
import com.Ironhack.MidTermProject.DTO.SavingDTO;
import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Models.Account.*;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;
import com.Ironhack.MidTermProject.Repositories.*;
import com.Ironhack.MidTermProject.Services.Implements.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentCheckingRepository studentCheckingRepository;
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    SavingRepository savingRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountHolder addAccountHolder(AccountHolder accountHolder) {
        if (!accountHolderRepository.findByUsername(accountHolder.getUsername()).isPresent()) {
            AccountHolder accountHolder1 = new AccountHolder(accountHolder.getUsername(), passwordEncoder.encode((accountHolder.getPassword())),
                    accountHolder.getFullName(), accountHolder.getBirth(),accountHolder.getPrimaryAddress(),
                    accountHolder.getMailingAddress());
            accountHolder1.setRole("ACCOUNTHOLDER");
            return accountHolderRepository.save(accountHolder1);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The username already exists");
        }
    }
    public ThirdParty addThirdParty(ThirdParty thirdParty) {
        if (!thirdPartyRepository.findByUsername(thirdParty.getUsername()).isPresent()) {
            ThirdParty thirdParty1 = new ThirdParty(thirdParty.getUsername(), passwordEncoder.encode((thirdParty.getPassword())),
                    thirdParty.getFullName(), thirdParty.getHashedKey());
            thirdParty1.setRole("THIRDPARTY");
            return thirdPartyRepository.save(thirdParty1);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The username already exists");
        }
    }

    public Account createCheckingAccount(CheckingDTO checking) {
        Account result;
        if (accountHolderRepository.findByUsername(checking.getPrimaryOwner()).isPresent()) {
            AccountHolder accountHolder1 = accountHolderRepository.findByUsername(checking.getPrimaryOwner()).get();
            long year = ChronoUnit.YEARS.between(accountHolder1.getBirth(), LocalDate.now());

            if (year < 24) {
                if(accountHolderRepository.findByUsername(checking.getSecondaryOwner()).isPresent()){
                    AccountHolder accountHolder2 = accountHolderRepository.findByUsername(checking.getSecondaryOwner()).get();
                    StudentChecking studentChecking1 = new StudentChecking(new Money(checking.getBalance()), accountHolder1,
                            accountHolder2, checking.getSecretKey(), checking.getStatus());
                    result = studentCheckingRepository.save(studentChecking1);
                } else{
                    StudentChecking studentChecking2 = new StudentChecking(new Money(checking.getBalance()), accountHolder1,
                            checking.getSecretKey(), checking.getStatus());
                    result = studentCheckingRepository.save(studentChecking2);
                }

            } else {
                if(accountHolderRepository.findByUsername(checking.getSecondaryOwner()).isPresent()){
                    AccountHolder accountHolder2 = accountHolderRepository.findByUsername(checking.getSecondaryOwner()).get();
                    Checking checking1 = new Checking(new Money(checking.getBalance()), accountHolder1,
                            accountHolder2, checking.getSecretKey(), checking.getStatus());
                    result = checkingRepository.save(checking1);
                } else{
                    Checking checking2 = new Checking(new Money(checking.getBalance()), accountHolder1,
                             checking.getSecretKey(), checking.getStatus()) ;
                    result = checkingRepository.save(checking2);
                }

            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exists");
        }

        return result;
    }

    public Saving createSaving(SavingDTO saving){
        System.out.println(saving.getBalance());

        if (accountHolderRepository.findByUsername(saving.getPrimaryOwner()).isPresent()) {
            AccountHolder accountHolder1 = accountHolderRepository.findByUsername(saving.getPrimaryOwner()).get();
            Money minimumBalance;
                if (saving.getMinimumBalance() == null) {
                    minimumBalance = new Money(new BigDecimal("1000"));
                } else {
                    minimumBalance = new Money(saving.getMinimumBalance());
                }
                if(minimumBalance.getAmount().compareTo(new BigDecimal("100")) < 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Minimum Balance is not correct");
                }
                if(minimumBalance.getAmount().compareTo(new BigDecimal("1000")) > 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Minimum Balance is not correct");
                }
            BigDecimal interestRate;
                if(saving.getInterestRate() == null){
                    interestRate = new BigDecimal("0.0025");
                } else{
                    interestRate = new BigDecimal(String.valueOf(saving.getInterestRate()));
                }
                if(interestRate.compareTo(new BigDecimal("0.5")) > 0){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Interest Rate is not correct");
                }
                if(interestRate.compareTo(new BigDecimal("0.0025")) < 0){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Interest Rate is not correct");
                }
            if (accountHolderRepository.findByUsername(saving.getSecondaryOwner()).isPresent()){
                AccountHolder accountHolder2 = accountHolderRepository.findByUsername(saving.getSecondaryOwner()).get();

                Saving saving1 = new Saving(new Money(saving.getBalance()), accountHolder1, accountHolder2, saving.getSecretKey(),saving.getStatus(),
                        minimumBalance, interestRate);

                return savingRepository.save(saving1);
            } else{
                Saving saving2 = new Saving(new Money(saving.getBalance()), accountHolder1,saving.getSecretKey(),saving.getStatus(),
                        minimumBalance, interestRate);

                saving2.addInterest();

                return savingRepository.save(saving2);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Username doesn't exists");
        }
    }
    public CreditCard createCreditCard(CreditCardDTO creditCard){

        if (accountHolderRepository.findByUsername(creditCard.getPrimaryOwner()).isPresent()) {
            AccountHolder accountHolder1 = accountHolderRepository.findByUsername(creditCard.getPrimaryOwner()).get();
            Money creditLimit;
                if (creditCard.getCreditLimit() == null) {
                    creditLimit = new Money(new BigDecimal("100"));
                } else {
                    creditLimit = new Money(creditCard.getCreditLimit());
                }
                if(creditLimit.getAmount().compareTo(new BigDecimal("100000")) > 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Credit Limit is not correct");
                }
                if(creditLimit.getAmount().compareTo(new BigDecimal("100")) < 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Credit Limit is not correct");
                }
            BigDecimal interestRate;
                if(creditCard.getInterestRate() == null){
                    interestRate = new BigDecimal("0.2");
                } else{
                    interestRate = new BigDecimal(String.valueOf(creditCard.getInterestRate()));
                }
                if(interestRate.compareTo(new BigDecimal("0.1")) < 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Interest Rate is not correct");
                }
                if(interestRate.compareTo(new BigDecimal("0.2")) > 0) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Interest Rate is not correct");
                }

            if (accountHolderRepository.findByUsername(creditCard.getSecondaryOwner()).isPresent()){
                AccountHolder accountHolder2 = accountHolderRepository.findByUsername(creditCard.getSecondaryOwner()).get();
                CreditCard creditCard1 = new CreditCard(new Money(creditCard.getBalance()), accountHolder1, accountHolder2, creditCard.getSecretKey(), creditCard.getStatus(), interestRate, creditLimit);
                return creditCardRepository.save(creditCard1);
            } else{
                CreditCard creditCard2 = new CreditCard(new Money(creditCard.getBalance()), accountHolder1, creditCard.getSecretKey(),creditCard.getStatus(), interestRate, creditLimit);
                return creditCardRepository.save(creditCard2);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exists");
        }
    }

    public Account modifyBalance(long idAccount, BigDecimal balance){
        Account account1;
        if (accountRepository.findById(idAccount).isPresent()) {
            account1 = accountRepository.findById(idAccount).get();
            account1.setBalance(new Money(balance));
            accountRepository.save(account1);
            return account1;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");
        }
    }
    public void deleteProduct(long id) {
        if (!accountRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account doesn't exist");
        }
      accountRepository.deleteById(id);
    }

}
