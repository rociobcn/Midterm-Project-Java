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

public class Saving extends Account{
    @Embedded
    @DecimalMin(value="100")
    @AttributeOverrides({
            @AttributeOverride(name = "USD", column = @Column(name = "minimumBalanceCreditCardUSD")),
            @AttributeOverride(name = "DEFAULT_ROUNDING", column = @Column(name = "minimumBalanceCreditCardDefaultRounding")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimumBalanceCreditCardCurrency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimumBalanceCreditCardAmount"))
    })
    private Money minimumBalance;
    @DecimalMax(value="0.5")
    private BigDecimal interestRate;
    @NotNull(message = "The secret key cannot be empty")
    private String secretKey;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "The date cannot be empty")
    private Date createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "The date cannot be empty")
    private Date interestDate;
    @NotNull(message = "The status cannot be empty")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Saving(BigDecimal interestRate, String secretKey, Status status) {
        this.minimumBalance = new Money(new BigDecimal(1000));
        this.interestRate = new BigDecimal(0.0025);
        this.secretKey = secretKey;
        this.createDate = Date.from(Instant.now());
        this.status = status;
        this.interestDate =Date.from(Instant.now());
    }

    public Saving(long id, Money balance, AccountHolder primaryOwner, AccountHolder secundaryOwner, Money minimumBalance, BigDecimal interestRate, String secretKey, Date createDate, Status status) {
        super(id, balance, primaryOwner, secundaryOwner);
        this.minimumBalance = new Money(new BigDecimal(1000));
        this.interestRate = new BigDecimal(0.0025);
        this.secretKey = secretKey;
        this.createDate = Date.from(Instant.now());
        this.interestDate =Date.from(Instant.now());
        this.status = status;
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

    public Date getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(Date interestDate) {
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
        penaltyBalance();
        addInterest();
        super.setBalance(balance);
    }
    public void penaltyBalance(){
        if(getBalance().getAmount().compareTo(getMinimumBalance().getAmount())== -1){
            setBalance(new Money(getBalance().getAmount().subtract(getPenaltyFee().getAmount())));
        }
    }

    public void addInterest(){
        LocalDate interestDateParse = LocalDate.of(getInterestDate().getYear(), getInterestDate().getMonth(), getInterestDate().getDay());
        Long differenceYears = ChronoUnit.YEARS.between(interestDateParse, LocalDate.now());
        if(differenceYears >=1){
            BigDecimal interest = getBalance().getAmount().multiply(getInterestRate()).multiply(BigDecimal.valueOf(differenceYears));
            setBalance(new Money(getBalance().getAmount().add(interest)));
            this.interestDate = Date.from(Instant.now());
        }

    }


}
