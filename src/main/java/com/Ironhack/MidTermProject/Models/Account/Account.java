package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "balanceUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "balanceDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "balanceCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "balanceAmount"))
    })
    private Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner_username")

    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_username")
   // @NotEmpty(message = "The secondary owner cannot be empty")
    private AccountHolder secondaryOwner;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "penaltyFeeUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "penaltyFeeDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "penaltyFeeCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penaltyFeeAmount"))
    })
    private Money penaltyFee;

    @OneToMany(mappedBy = "sendAccount")
    @JsonIgnore
    private List<Transfer> sendTransferList;

    @JsonIgnore
    @OneToMany(mappedBy = "receiveAccount")
    private List<Transfer> receiveTransferList;

    @JsonIgnore
    @OneToMany(mappedBy = "accountHolderAccount")
    private List<TransferThirdParty> thirdPartyTransferList;
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = new Money(new BigDecimal(40));
        this.secretKey = secretKey;
        this.status = status;
    }
    public Account(Money balance, AccountHolder primaryOwner, String secretKey, Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.penaltyFee = new Money(new BigDecimal(40));
        this.secretKey = secretKey;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public List<Transfer> getSendTransferList() {
        return sendTransferList;
    }

    public void setSendTransferList(List<Transfer> sendTransferList) {
        this.sendTransferList = sendTransferList;
    }

    public List<Transfer> getReceiveTransferList() {
        return receiveTransferList;
    }

    public void setReceiveTransferList(List<Transfer> receiveTransferList) {
        this.receiveTransferList = receiveTransferList;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TransferThirdParty> getThirdPartyTransferList() {
        return thirdPartyTransferList;
    }

    public void setThirdPartyTransferList(List<TransferThirdParty> thirdPartyTransferList) {
        this.thirdPartyTransferList = thirdPartyTransferList;
    }

}
