package com.Ironhack.MidTermProject.Models.User;

import com.Ironhack.MidTermProject.Models.Account.TransferThirdParty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ThirdParty extends User{

    private String hashedKey;

    @OneToMany(mappedBy = "thirdParty")
    private List<TransferThirdParty> transferThirdPartyList;

    public ThirdParty(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public ThirdParty(String username, String password, String fullName, String hashedKey) {
        super(username, password, fullName);
        this.hashedKey = hashedKey;
    }

    public ThirdParty() {
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public List<TransferThirdParty> getTransferThirdPartyList() {
        return transferThirdPartyList;
    }

    public void setTransferThirdPartyList(List<TransferThirdParty> transferThirdPartyList) {
        this.transferThirdPartyList = transferThirdPartyList;
    }
}
