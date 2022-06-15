package com.Ironhack.MidTermProject.Services;

import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Repositories.AccountHolderRepository;
import com.Ironhack.MidTermProject.Repositories.AdminRepository;
import com.Ironhack.MidTermProject.Repositories.ThirdPartyRepository;
import com.Ironhack.MidTermProject.Services.Implements.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AdminRepository adminRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountHolder addAccountHolder(AccountHolder accountHolder) {
        if (!accountHolderRepository.findByUsername(accountHolder.getUsername()).isPresent()) {
            AccountHolder accountHolder1 = new AccountHolder(accountHolder.getUsername(), passwordEncoder.encode((accountHolder.getPassword())),
                    accountHolder.getFullName(), accountHolder.getBirth(),accountHolder.getPrimaryAddress(),
                    accountHolder.getMailingAddress());
            return accountHolderRepository.save(accountHolder1);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The Account Holder already exists");
        }
    }
}
