package com.Ironhack.MidTermProject.Services;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Enums.StatusTransferThirdParty;
import com.Ironhack.MidTermProject.Models.Account.Account;
import com.Ironhack.MidTermProject.Models.Account.Transfer;
import com.Ironhack.MidTermProject.Models.Account.TransferThirdParty;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;
import com.Ironhack.MidTermProject.Repositories.*;
import com.Ironhack.MidTermProject.Services.Implements.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHolderServiceImp implements AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    TransferThirdPartyRepository transferThirdPartyRepository;

    public Money getBalance(Long id, UserDetails userDetails) {
        AccountHolder verifyAccountHolder = new AccountHolder(userDetails.getUsername(), userDetails.getPassword());
        Money balance = null;
        List <Account> allAccountsList = accountRepository.findAll();
        for (Account x : allAccountsList){
            if (x.getId() == id){
                if (x.getPrimaryOwner().getUsername().equals(verifyAccountHolder.getUsername()) || (x.getSecondaryOwner()!= null && x.getSecondaryOwner().getUsername().equals(verifyAccountHolder.getUsername()))){
                    balance = x.getBalance();
                    break;
                } else{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your Account not exists");
                }
            }

        }
        return balance;
    }

    public void sendTransfer(UserDetails userDetails, long idReceive, long idSend, BigDecimal amount, String key) {

        if(accountHolderRepository.findByUsername(userDetails.getUsername()).isPresent()){
            AccountHolder verifyAccountHolder = accountHolderRepository.findByUsername(userDetails.getUsername()).get();
            if(accountRepository.findById(idReceive).isPresent()){
                Account receive = accountRepository.findById(idReceive).get();
                List<Account> allAccounts = new ArrayList<>(verifyAccountHolder.getAccountListPrimaryOwner());
                allAccounts.addAll(verifyAccountHolder.getAccountListSecondaryOwner());
                for(Account z : allAccounts){
                    if(z.getId() == idSend && z.getSecretKey().equals(key) && z.getStatus().equals(Status.ACTIVE)){
                        if(amount == null ){
                            amount = new BigDecimal(0);
                        }
                        if(z.getBalance().getAmount().compareTo(amount) >= 0){
                            Transfer transfer1 = new Transfer(new Money(amount), receive, z);
                            transferRepository.save(transfer1);
                            z.setBalance(new Money(z.getBalance().getAmount().subtract(amount)));
                            receive.setBalance(new Money(receive.getBalance().getAmount().add(amount)));
                            accountRepository.save(z);
                            accountRepository.save(receive);
                            break;
                        } else{
                            throw new ResponseStatusException(HttpStatus.CONFLICT, "You don't have enough money");
                        }

                    }else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your Account doesn't exist or your secret key is not valid or status is frozen. ");
                    }
                }
        } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account doesn't exists");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account Holder doesn't exists");
        }
    }

    public void sendTransferToThirdParty(UserDetails userDetails, String userNameThirdParty, long idAccountFromSend, BigDecimal amount, String secretKey){
        AccountHolder verifyAccountHolder;
        if(accountHolderRepository.findByUsername(userDetails.getUsername()).isPresent()) {
            verifyAccountHolder = accountHolderRepository.findByUsername(userDetails.getUsername()).get();
            if(thirdPartyRepository.findByUsername(userNameThirdParty).isPresent()){
                ThirdParty thirdParty = thirdPartyRepository.findByUsername(userNameThirdParty).get();
                List<Account> allAccounts = new ArrayList<>(verifyAccountHolder.getAccountListPrimaryOwner());
                allAccounts.addAll(verifyAccountHolder.getAccountListSecondaryOwner());
                for(Account i : allAccounts){
                    if(i.getId() == idAccountFromSend && i.getSecretKey().equals(secretKey) && i.getStatus().equals(Status.ACTIVE)){
                        if(amount == null ){
                            amount = new BigDecimal(0);
                        }
                        if(i.getBalance().getAmount().compareTo(amount) >= 0){
                            TransferThirdParty transferThirdParty1 = new TransferThirdParty(new Money(amount), i, thirdParty, StatusTransferThirdParty.SENDFROMACCOUNTHOLDERTOTHIRDPARTY);
                            transferThirdPartyRepository.save(transferThirdParty1);
                            i.setBalance(new Money(i.getBalance().getAmount().subtract(amount)));
                            accountRepository.save(i);
                        } else {
                            throw new ResponseStatusException(HttpStatus.CONFLICT, "Your not have a money");
                        }
                    }else{
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Not is valid");
                    }
                }
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Third Party not exists");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account Holder not exists");
        }
    }

    public void sendTransferWithFraudDetection(UserDetails userDetails, long idReceive, long idSend, BigDecimal amount, String key) {

        if(accountHolderRepository.findByUsername(userDetails.getUsername()).isPresent()){
            AccountHolder verifyAccountHolder = accountHolderRepository.findByUsername(userDetails.getUsername()).get();
            if(accountRepository.findById(idReceive).isPresent()){
                Account receive = accountRepository.findById(idReceive).get();
                List<Account> allAccounts = new ArrayList<>(verifyAccountHolder.getAccountListPrimaryOwner());
                allAccounts.addAll(verifyAccountHolder.getAccountListSecondaryOwner());
                for(Account z : allAccounts){
                    if(z.getId() == idSend && z.getSecretKey().equals(key)){
                        //bonus1
                        List<Transfer> transferfordate = z.getSendTransferList();
                        //bonus 2
                        Transfer ultimaTransfer = z.getSendTransferList().get(z.getSendTransferList().size() -1);
                        LocalDateTime dateUltimaTransfer = ultimaTransfer.getDateTransfer();
                        LocalDateTime now = LocalDateTime.now();
                        long difference = ChronoUnit.SECONDS.between(dateUltimaTransfer, now);
                        //truncatedTo(ChronoUnit.SECONDS)
                        if (difference < 100){
                            z.setStatus(Status.FROZEN);
                            break;
                        } else {
                            if(amount == null ){ amount = new BigDecimal(0);}
                            if(z.getBalance().getAmount().compareTo(amount) >= 0 && z.getStatus().equals(Status.ACTIVE) ){
                                Transfer transfer1 = new Transfer(new Money(amount), receive, z);
                                transferRepository.save(transfer1);
                                z.setBalance(new Money(z.getBalance().getAmount().subtract(amount)));
                                receive.setBalance(new Money(receive.getBalance().getAmount().add(amount)));
                                accountRepository.save(z);
                                accountRepository.save(receive);
                                break;
                            } else{
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "Your not have a money");
                            }
                        }
                    }else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your Account not exists");
                    }
                }
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account Holder not exists");
            }
        }
    }

}
