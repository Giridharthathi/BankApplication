package com.ns.task.service;

import com.com.ns.task.GDBankApplication;
import com.ns.task.account.CurrentAccount;
import com.ns.task.account.IAccount;
import com.ns.task.account.PremiumAccount;
import com.ns.task.account.SavingsAccount;
import com.ns.task.bean.AccountType;
import com.ns.task.bean.Customer;
import com.ns.task.exception.AccountException;
import com.ns.task.exception.WithdrawException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.com.ns.task.GDBankApplication.bankAccountDetails;
import static com.ns.task.account.IAccount.*;

public class BankOperation {
    static Scanner scanner = new Scanner(System.in);
    private IAccount account;

    public void withDraw() throws WithdrawException {
        System.out.print("Enter your account Number:");
        String accountNumber = scanner.next();
        AccountType accountFoundType = findAccountTypeByNumber(accountNumber);

        if (accountFoundType != null) {
            double amountToWithdraw;
            System.out.print("Enter amount to withdraw:");
            String amountInput = scanner.next();
            amountToWithdraw = Double.parseDouble(amountInput);
            if (amountToWithdraw < 0) {
                System.out.println("Please enter amount greater than 0");
                return;
            }

            switch (accountFoundType) {

                case CURRENT ->
                    account = new CurrentAccount();
                case SAVINGS ->
                    account = new SavingsAccount();
                case PREMIUM ->
                    account = new PremiumAccount();
            }
            account.withDraw(accountNumber, amountToWithdraw);
        } else {
            System.out.println("---------------------------No Account Found------------------ \n");
        }
    }

    public AccountType findAccountTypeByNumber(String accountNumber) {
        List<Customer> bankAccountDetails = GDBankApplication.bankAccountDetails;
        boolean accountFound = false;
        AccountType accountType = null;
        for (Customer customerDetail : bankAccountDetails) {
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound = true;
                accountType = (customerDetail.getAccount().getAccountType());
                break;
            }
        }
        if (!accountFound) {
            throw new AccountException("Account not found please enter correct number");
        }
        return Optional.of(accountType).get();
    }

    public String findAccountDetails(String accountNumber) {

        List<Customer> bankAccountDetails = GDBankApplication.bankAccountDetails;
        Customer customerBankAccountDetails = null;
        boolean accountFound = false;
        BigDecimal interestAmount = new BigDecimal(0);

        for (Customer customerDetail : bankAccountDetails) {
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound = true;
                if (new Date().getDate() == 30 && customerDetail.getAccount().getAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
                    switch (customerDetail.getAccount().getAccountType()) {

                        case PREMIUM ->
                                interestAmount = BigDecimal.valueOf(interest(customerDetail.getAccount().getAmount().doubleValue(), 1, PREMIUM_INTEREST_RATE));

                        case CURRENT ->
                                interestAmount = BigDecimal.valueOf(interest(customerDetail.getAccount().getAmount().doubleValue(), 1, CURRENT_INTEREST_RATE));

                        case SAVINGS ->
                                interestAmount = BigDecimal.valueOf(interest(customerDetail.getAccount().getAmount().doubleValue(), 1, SAVINGS_INTEREST_RATE));

                    }

                    customerDetail.getAccount().setInterestAmount(BigDecimal.valueOf(interestAmount.doubleValue()));
                }
                customerDetail.getAccount().setAmount(customerDetail.getAccount().getAmount().add(interestAmount));
                customerBankAccountDetails = customerDetail;
            }
        }

        if (!accountFound) {
            throw new AccountException("Account not found please enter correct number");
        }
        return customerBankAccountDetails.toString();

    }

    public void getAllCustomerDetails() {

        BigDecimal interestAmount = new BigDecimal(0);
        for (Customer customer : bankAccountDetails) {
            if (new Date().getDate() == 30 && customer.getAccount().getAmount().compareTo(BigDecimal.valueOf(0)) > 0) {

                switch (customer.getAccount().getAccountType()) {

                    case PREMIUM ->
                            interestAmount = BigDecimal.valueOf(interest(customer.getAccount().getAmount().doubleValue(), 1, PREMIUM_INTEREST_RATE));

                    case CURRENT ->
                            interestAmount = BigDecimal.valueOf(interest(customer.getAccount().getAmount().doubleValue(), 1, CURRENT_INTEREST_RATE));

                    case SAVINGS ->
                            interestAmount = BigDecimal.valueOf(interest(customer.getAccount().getAmount().doubleValue(), 1, SAVINGS_INTEREST_RATE));

                }

                customer.getAccount().setInterestAmount(interestAmount);
            }
            customer.getAccount().setAmount(customer.getAccount().getAmount().add(interestAmount));
            System.out.println(customer);
        }
    }
}