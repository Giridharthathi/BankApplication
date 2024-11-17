package com.ns.task.account;


import com.ns.task.bean.AccountStatus;
import com.ns.task.bean.Customer;
import com.ns.task.exception.AccountException;
import com.ns.task.exception.WithdrawException;

import java.util.Scanner;

import static com.com.ns.task.GDBankApplication.bankAccountDetails;

public sealed interface IAccount permits CurrentAccount,PremiumAccount, SavingsAccount{
    Scanner scanner = new Scanner(System.in);
    static final double CURRENT_INTEREST_RATE = 0.05;
    static final double PREMIUM_INTEREST_RATE = 0.09;
    static final double SAVINGS_INTEREST_RATE = 0.04;
    static final double CURRENT_MIN_BALANCE = 0;
    static final double SAVINGS_MIN_BALANCE = 5000;
    static final double PREMIUM_MIN_BALANCE = 100000;

    void withDraw(String accountNumber, double amountToWithdraw) throws WithdrawException;
    void deposit(String accountNumber, double amount);

    static void idleTheAccount(String accountNumber) {
        boolean accountFound = false;

        for (Customer customer : bankAccountDetails) {
            if (customer.getAccount().getAccountNumber().equals(accountNumber)) {
                accountFound = true;
                if (customer.getAccount().isStatus() == AccountStatus.IDLE) {
                    System.out.println("Account " + accountNumber + " is already in IDLE state.");
                } else {
                    customer.getAccount().setStatus(AccountStatus.IDLE);
                    System.out.println("Account " + accountNumber + " is now IDLE.");
                }
                break;
            }
        }

        if (!accountFound) {
           throw new AccountException("No account found with account number: " + accountNumber);
        }
    }

    static void activateTheAccount(String accountNumber) {
        boolean accountFound = false;

        for (Customer customer : bankAccountDetails) {
            if (customer.getAccount().getAccountNumber().equals(accountNumber)) {
                accountFound = true;
                if (customer.getAccount().isStatus() == AccountStatus.ACTIVE) {
                    System.out.println("Account " + accountNumber + " is already in ACTIVE state.");
                } else {
                    customer.getAccount().setStatus(AccountStatus.ACTIVE);
                    System.out.println("Account " + accountNumber + " is now ACTIVE.");
                }
                break;
            }
        }

        if (!accountFound) {
           throw new AccountException("No account found with account number: " + accountNumber);
        }
    }

    static double interest(double principalAmount, int time, double rateOfInterest){
        return (principalAmount * time * rateOfInterest)/100;
    }

}
