package com.ns.task.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BankAccount implements Serializable {
    private String accountNumber;
    private BigDecimal amount;
    private AccountStatus status;
    private BigDecimal interestAmount;
    private LocalDate openedDate;
    private AccountType accountType;
    private int loyaltyPoints;
    private BigDecimal openingBalance;
    private  LocalDate lastUsed;

    private int withdrawAttempts;


    public BankAccount() {

    }

    public BankAccount(String accountNumber, BigDecimal amount, AccountStatus status, BigDecimal interestAmount, LocalDate openedDate, AccountType accountType, int loyaltyPoints, BigDecimal openingBalance, int withdrawAttempts) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.interestAmount = interestAmount;
        this.openedDate = openedDate;
        this.accountType = accountType;
        this.loyaltyPoints = loyaltyPoints;
        this.openingBalance = openingBalance;
        this.withdrawAttempts = withdrawAttempts;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }



    public AccountStatus isStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }


    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDate openedDate) {
        this.openedDate = openedDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }


    public int getWithdrawAttempts() {
        return withdrawAttempts;
    }

    public void setWithdrawAttempts(int withdrawAttempts) {
        this.withdrawAttempts = withdrawAttempts;
    }

    public LocalDate getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDate lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", interestAmount=" + interestAmount +
                ", openedDate=" + openedDate +
                ", accountType=" + accountType +
                ", loyaltyPoints=" + loyaltyPoints +
                ", openingBalance=" + openingBalance +
                ", withdrawAttempts=" + withdrawAttempts +
                '}';
    }
}
