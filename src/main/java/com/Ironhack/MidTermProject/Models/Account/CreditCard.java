package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Entity
public class CreditCard extends Account{

    private BigDecimal interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "creditLimitUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "creditLimitDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "creditLimitCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "creditLimitAmount"))
    })
    private Money creditLimit;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate interestDate;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status, BigDecimal interestRate, Money creditLimit) {
        super(balance, primaryOwner, secondaryOwner, secretKey, status);
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
        this.createDate = LocalDate.now();
        this.interestDate = LocalDate.now();
    }
    public CreditCard(Money balance, AccountHolder primaryOwner, String secretKey, Status status, BigDecimal interestRate, Money creditLimit) {
        super(balance, primaryOwner,secretKey, status);
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
        this.createDate = LocalDate.now();
        this.interestDate = LocalDate.now();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {this.interestRate = interestRate;}

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(LocalDate interestDate) {
        this.interestDate = interestDate;
    }

    public void addInterest(){

        Long differenceMonths = ChronoUnit.MONTHS.between(interestDate, LocalDate.now());
        if(differenceMonths >=1){
            BigDecimal interest = super.getBalance().getAmount().multiply(getInterestRate()).multiply(BigDecimal.valueOf(differenceMonths));
            super.setBalance(new Money(super.getBalance().getAmount().add(interest)));
            this.interestDate = LocalDate.now();
        }
    }

    @Override
    public Money getBalance() {
        addInterest();
        return super.getBalance();
    }

    @Override
    public void setBalance(Money balance) {
        super.setBalance(balance);
        addInterest();

    }
}
