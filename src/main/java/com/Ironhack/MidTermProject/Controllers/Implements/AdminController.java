package com.Ironhack.MidTermProject.Controllers.Implements;

import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;

public interface AdminController {
    AccountHolder addAccountHolder(AccountHolder accountHolder);
    ThirdParty addThirdParty(ThirdParty thirdParty);
}
