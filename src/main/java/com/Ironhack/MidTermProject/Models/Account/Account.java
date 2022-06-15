package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @NotNull(message = "The balance cannot be empty")
    private Money balance;
    @ManyToOne
    @JoinColumn(name = "primary_owner_username")
    @NotNull(message = "The primary owner cannot be empty")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secundary_owner_username")
    private AccountHolder secundaryOwner;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "penaltyFeeUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "penaltyFeeDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "penaltyFeeCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penaltyFeeAmount"))
    })
    private Money penaltyFee;

    public Account() {
    }

    public Account(long id, Money balance, AccountHolder primaryOwner, AccountHolder secundaryOwner) {
        this.id = id;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secundaryOwner = secundaryOwner;
        this.penaltyFee = new Money(new BigDecimal(40));
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

    public AccountHolder getSecundaryOwner() {
        return secundaryOwner;
    }

    public void setSecundaryOwner(AccountHolder secundaryOwner) {
        this.secundaryOwner = secundaryOwner;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }
}
