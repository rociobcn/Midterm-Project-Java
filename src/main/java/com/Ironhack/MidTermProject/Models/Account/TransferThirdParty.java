package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.StatusTransferThirdParty;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransferThirdParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @AttributeOverrides({
           @AttributeOverride(name = "USD", column = @Column(name = "amountUSD")),
           @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "amountDefaultRounding")),
           @AttributeOverride(name = "currency", column = @Column(name = "amountCurrency")),
           @AttributeOverride(name = "amount", column = @Column(name = "amountAmount"))
    })
    private Money amount;

    @ManyToOne
    @JoinColumn(name = "account_holder_account_id")
    private Account accountHolderAccount;

    @ManyToOne
    @JoinColumn(name = "third_party_username")
    private ThirdParty thirdParty;

    private LocalDateTime dateTransfer;

    private StatusTransferThirdParty statusTransferThirdParty;

    public TransferThirdParty(){}

    public TransferThirdParty(Money amount, Account accountHolderAccount, ThirdParty thirdParty, StatusTransferThirdParty statusTransferThirdParty) {
        this.amount = amount;
        this.accountHolderAccount = accountHolderAccount;
        this.thirdParty = thirdParty;
        this.dateTransfer = LocalDateTime.now();
        this.statusTransferThirdParty = statusTransferThirdParty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Account getAccountHolderAccount() {
        return accountHolderAccount;
    }

    public void setAccountHolderAccount(Account accountHolderAccount) {
        this.accountHolderAccount = accountHolderAccount;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public LocalDateTime getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(LocalDateTime dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

    public StatusTransferThirdParty getStatusTransferThirdParty() {
        return statusTransferThirdParty;
    }

    public void setStatusTransferThirdParty(StatusTransferThirdParty statusTransferThirdParty) {
        this.statusTransferThirdParty = statusTransferThirdParty;
    }
}
