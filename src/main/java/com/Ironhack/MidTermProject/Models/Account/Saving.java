package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Entity
public class Saving extends Account{
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "minimumBalanceCreditCardUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "minimumBalanceCreditCardDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalanceCreditCardCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalanceCreditCardAmount"))
    })
    private Money minimumBalance;
    private BigDecimal interestRate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate interestDate;


    public Saving(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status, Money minimumBalance, BigDecimal interestRate ) {
        super(balance, primaryOwner, secondaryOwner, secretKey, status);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.createDate = LocalDate.now();
        this.interestDate = LocalDate.now();

    }
    public Saving(Money balance, AccountHolder primaryOwner, String secretKey, Status status, Money minimumBalance, BigDecimal interestRate ) {
        super(balance, primaryOwner, secretKey, status);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.createDate = LocalDate.now();
        this.interestDate = LocalDate.now();
    }

    public Saving() {
        this.minimumBalance = new Money(new BigDecimal(1000));
        this.interestRate = new BigDecimal(0.0025);
    }


    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) { this.minimumBalance = minimumBalance;}

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate;}

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

    @Override
    public Money getBalance() {
        penaltyBalance();
        addInterest();
        return super.getBalance();
    }
    @Override
    public void setBalance(Money balance) {
        super.setBalance(balance);
        penaltyBalance();
        addInterest();

    }
    public void penaltyBalance(){
        if(super.getBalance().getAmount().compareTo(getMinimumBalance().getAmount())== -1){
            super.setBalance(new Money(super.getBalance().getAmount().subtract(getPenaltyFee().getAmount())));
        }
    }

    public void addInterest(){

        Long differenceYears = ChronoUnit.YEARS.between(interestDate,LocalDate.now());
        if(differenceYears >=1){
            BigDecimal interest = super.getBalance().getAmount().multiply(getInterestRate()).multiply(BigDecimal.valueOf(differenceYears));
            super.setBalance(new Money(super.getBalance().getAmount().add(interest)));
            this.interestDate = LocalDate.now();
        }

    }


}
