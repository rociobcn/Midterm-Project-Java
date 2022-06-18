package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transfer {
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
    @JoinColumn(name = "receive_id")
    private Account receiveAccount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sendAccount;

    private LocalDateTime dateTransfer;

    public Transfer(){}

    public Transfer(Money amount, Account receiveAccount, Account sendAccount) {
        this.amount = amount;
        this.receiveAccount = receiveAccount;
        this.sendAccount = sendAccount;
        this.dateTransfer = LocalDateTime.now();
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

    public Account getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(Account receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public Account getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(Account sendAccount) {
        this.sendAccount = sendAccount;
    }

    public LocalDateTime getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(LocalDateTime dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

}
