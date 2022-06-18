package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Checking extends Account{
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "minimumBalanceCheckingUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "minimumBalanceCheckingDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalanceCheckingCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalanceCheckingAmount"))
    })

    private Money minimumBalance;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "monthlyMaintenanceFeeUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "monthlyMaintenanceFeeDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthlyMaintenanceFeeCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthlyMaintenanceFeeAmount"))
    })

    private Money monthlyMaintenanceFee;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createDate;
    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, secretKey, status);
        this.minimumBalance = new Money(new BigDecimal(250));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
        this.createDate = LocalDate.now();

    }
    public Checking(Money balance, AccountHolder primaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner, secretKey, status);
        this.minimumBalance = new Money(new BigDecimal(250));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
        this.createDate = LocalDate.now();
    }

    public Checking() {
        this.minimumBalance = new Money(new BigDecimal(250));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }


    public void penaltyBalance(){
        if(super.getBalance().getAmount().compareTo(getMinimumBalance().getAmount())== -1){
            super.setBalance(new Money(super.getBalance().getAmount().subtract(getPenaltyFee().getAmount())));
        }
    }

    @Override
    public Money getBalance() {
        penaltyBalance();
        return super.getBalance();
    }

    @Override
    public void setBalance(Money balance) {

        super.setBalance(balance);
        penaltyBalance();
    }
}
