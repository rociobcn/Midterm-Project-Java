package com.Ironhack.MidTermProject.Services.Implements;

import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;

public interface AdminService {

    AccountHolder addAccountHolder(AccountHolder accountHolder);
    ThirdParty addThirdParty(ThirdParty thirdParty);
}
