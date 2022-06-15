package com.Ironhack.MidTermProject.Models.Account;

import com.Ironhack.MidTermProject.Embeddables.Money;
import com.Ironhack.MidTermProject.Enums.Status;
import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class StudentChecking extends Account{
    @NotNull(message = "The secret key cannot be empty")
    private String secretKey;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "The date cannot be empty")
    private Date createDate;
    @NotNull(message = "The status cannot be empty")
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking(String secretKey, Date createDate, Status status) {
        this.secretKey = secretKey;
        this.createDate = createDate;
        this.status = status;
    }

    public StudentChecking(long id, Money balance, AccountHolder primaryOwner, AccountHolder secundaryOwner, String secretKey, Date createDate, Status status) {
        super(id, balance, primaryOwner, secundaryOwner);
        this.secretKey = secretKey;
        this.createDate = createDate;
        this.status = status;
    }

    public StudentChecking() {
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
}
