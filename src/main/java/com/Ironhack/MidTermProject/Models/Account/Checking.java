package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @NotNull(message = "The secret key cannot be empty")
    private String secretKey;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "The date cannot be empty")

    private Date createDate;
    @NotNull(message = "The status cannot be empty")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Checking(String secretKey, Date createDate, Status status) {
        this.minimumBalance = new Money(new BigDecimal(250));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
        this.secretKey = secretKey;
        this.createDate = createDate;
        this.status = status;
    }

    public Checking(long id, Money balance, AccountHolder primaryOwner, AccountHolder secundaryOwner, String secretKey, Date createDate, Status status) {
        super(id, balance, primaryOwner, secundaryOwner);
        this.minimumBalance = new Money(new BigDecimal(250));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
        this.secretKey = secretKey;
        this.createDate = createDate;
        this.status = status;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void penaltyBalance(){
        if(getBalance().getAmount().compareTo(getMinimumBalance().getAmount())== -1){
            setBalance(new Money(getBalance().getAmount().subtract(getPenaltyFee().getAmount())));
        }
    }

    @Override
    public Money getBalance() {
        penaltyBalance();
        return super.getBalance();
    }

    @Override
    public void setBalance(Money balance) {
        penaltyBalance();
        super.setBalance(balance);
    }
}
