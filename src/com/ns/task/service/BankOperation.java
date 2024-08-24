package com.ns.task.service;

import com.ns.task.GDBankApplication;
import com.ns.task.account.CurrentAccount;
import com.ns.task.account.IAccount;
import com.ns.task.account.PremiumAccount;
import com.ns.task.account.SavingsAccount;
import com.ns.task.bean.AccountType;
import com.ns.task.bean.BankAccount;
import com.ns.task.bean.Customer;
import com.ns.task.exception.AccountException;
import com.ns.task.exception.WithdrawException;
import com.ns.task.fileManager.GetFileData;
import com.ns.task.fileManager.WriteToFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static com.ns.task.GDBankApplication.bankAccountDetails;
import static com.ns.task.GDBankApplication.writeUpdatedDataToFile;
import static com.ns.task.account.IAccount.*;

public class BankOperation {
    private final static Scanner scanner = new Scanner(System.in);
    private static final WriteToFile writeToFile = new WriteToFile();


    public void withDraw() throws WithdrawException {
        System.out.print("Enter your account Number:");
        String accountNumber = scanner.next();
        AccountType accountFoundType = findAccountTypeByNumber(accountNumber);

        if (accountFoundType != null) {
            double amountToWithdraw;
            do {
                try {
                    System.out.print("Enter amount to withdraw:");
                    String amountInput = scanner.next();
                    amountToWithdraw = Double.parseDouble(amountInput);
                    if (amountToWithdraw < 0) {
                        System.out.println("Please enter amount greater than 0");
                    }
                    break;
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Please enter valid amount to withdraw");
                }
            } while (true);

            IAccount account;
            switch (accountFoundType) {

                case CURRENT -> {
                    account = new CurrentAccount();
                    account.withDraw(accountNumber, amountToWithdraw);
                }
                case SAVINGS -> {
                    account = new SavingsAccount();
                    account.withDraw(accountNumber, amountToWithdraw);
                }
                case PREMIUM -> {
                    account = new PremiumAccount();
                    account.withDraw(accountNumber, amountToWithdraw);
                }
            }
            writeToFile.writeUpdatedData();
        } else {
            System.out.println("---------------------------No Account Found------------------ \n");
        }
    }

    public void deposit(){
        IAccount account;
        System.out.println("Enter your Account number:");
        String accountNumber = scanner.next();
        AccountType accountType = findAccountTypeByNumber(accountNumber);
        double depositAmount = amountInput();
        switch (accountType) {
            case PREMIUM -> {
                account = new PremiumAccount();
                account.deposit(accountNumber,depositAmount);
            }
            case CURRENT -> {
                account = new CurrentAccount();
                account.deposit(accountNumber,depositAmount);
            }
            case SAVINGS -> {
                account = new SavingsAccount();
                account.deposit(accountNumber,depositAmount);
            }
        }
        writeUpdatedDataToFile.writeUpdatedData();
    }
    private double amountInput(){
    double depositAmount;
        while (true) {
            try {
                System.out.print("Enter amount to deposit");
                String amountInput = scanner.next();
                depositAmount = Double.parseDouble(amountInput);
                if (depositAmount > 0) {
                    return depositAmount;
                } else {
                    System.out.println("Please enter amount greater than ZERO");
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println("----------Please enter valid amount to withdraw-----------");
            }
        }
    }

    public AccountType findAccountTypeByNumber(String accountNumber) {
        List<Customer> bankAccountDetails = GDBankApplication.bankAccountDetails;
        boolean accountFound = false;
        AtomicReference<AccountType> accountType = new AtomicReference<>();
        for (Customer customerDetail : bankAccountDetails) {
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound = true;
                accountType.set(customerDetail.getAccount().getAccountType());
            }
        }
        if (!accountFound) {
            throw new AccountException("Account not found please enter correct number");
        }
        return Optional.of(accountType).get().get();
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
        writeToFile.writeUpdatedData();
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
        writeToFile.writeUpdatedData();

    }
}