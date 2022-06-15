package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CreditCard extends Account{
    @DecimalMin(value="0.1")
    private BigDecimal interestRate;
    @DecimalMax(value="100000")
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
    @NotNull(message = "The date cannot be empty")
    private Date createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "The date cannot be empty")
    private Date interestDate;

    public CreditCard() {
        this.interestRate = new BigDecimal(0.0025);
        this.creditLimit = new Money(new BigDecimal(100));
        this.createDate = Date.from(Instant.now());
        this.interestDate =Date.from(Instant.now());
    }

    public CreditCard(long id, Money balance, AccountHolder primaryOwner, AccountHolder secundaryOwner) {
        super(id, balance, primaryOwner, secundaryOwner);
        this.interestRate = new BigDecimal(0.2);
        this.creditLimit = new Money(new BigDecimal(100));
        this.createDate = Date.from(Instant.now());
        this.interestDate =Date.from(Instant.now());
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(Date interestDate) {
        this.interestDate = interestDate;
    }

    public void addInterest(){
        LocalDate interestDateParse = LocalDate.of(getInterestDate().getYear(), getInterestDate().getMonth(), getInterestDate().getDay());
        Long differenceMonths = ChronoUnit.MONTHS.between(interestDateParse, LocalDate.now());
        if(differenceMonths >=1){
            BigDecimal interest = getBalance().getAmount().multiply(getInterestRate()).multiply(BigDecimal.valueOf(differenceMonths));
            setBalance(new Money(getBalance().getAmount().add(interest)));
            this.interestDate = Date.from(Instant.now());
        }
    }

    @Override
    public Money getBalance() {
        addInterest();
        return super.getBalance();
    }

    @Override
    public void setBalance(Money balance) {
        addInterest();
        super.setBalance(balance);
    }
}
