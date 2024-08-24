package com.ns.task.account;

import com.ns.task.exception.AccountException;
import com.ns.task.exception.WithdrawException;
import com.ns.task.bean.Customer;
import com.ns.task.GDBankApplication;
import com.ns.task.bean.AccountStatus;
import com.ns.task.bean.AccountType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class SavingsAccount implements IAccount {
    private static final int MAX_WITHDRAW_LIMIT = 10;
    List<Customer> bankAccountDetails = GDBankApplication.bankAccountDetails;

    @Override
    public void withDraw(String accountNumber, double amountToWithdraw) throws WithdrawException {

        boolean accountFound = false;
            for (Customer customerAccounts : bankAccountDetails) {
                if (accountNumber.equals(customerAccounts.getAccount().getAccountNumber())) {

                    accountFound = true;

                    if (customerAccounts.getAccount().isStatus() == AccountStatus.ACTIVE) {

                        if (customerAccounts.getAccount().getAmount().compareTo(BigDecimal.valueOf(amountToWithdraw))>0 &&
                                (customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw))).compareTo(BigDecimal.valueOf(SAVINGS_MIN_BALANCE)) <= SAVINGS_MIN_BALANCE){
                            if (new Date().getDate() == 1) {
                                customerAccounts.getAccount().setWithdrawAttempts(0);
                            }   
                            if (customerAccounts.getAccount().getWithdrawAttempts() <= MAX_WITHDRAW_LIMIT) {

                                double totalAmount = customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw)).doubleValue();
                                customerAccounts.getAccount().setAmount(BigDecimal.valueOf(totalAmount));

                                System.out.println("Amount has been withdrawn of Rs:" + amountToWithdraw + "from accountNumber" +
                                        accountNumber);

                                int totalWithdrawAttempts = customerAccounts.getAccount().getWithdrawAttempts() + 1;
                                customerAccounts.getAccount().setWithdrawAttempts(totalWithdrawAttempts);

                                customerAccounts.getAccount().setWithdrawAttempts(totalWithdrawAttempts);
                            } else {
                                throw  new WithdrawException("You exceeded your withdraw limit");
                            }


                        } else {
                            throw new WithdrawException("You should enter the amount less then your account balance" +
                                    "(OR) Please enter amount above your account type limit");
                        }
                    } else {
                        throw new AccountException("Your account is inactive please contact our support team");
                    }
                }
            }
        if (!accountFound) {
            throw new AccountException("Account not found, please enter correct account Number");
        }
    }

    public void deposit(String accountNumber, double depositAmount) {

        boolean accountFound = false;
        for (Customer customerDetail : bankAccountDetails) {
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound = true;
                double totalAmount = customerDetail.getAccount().getAmount().add(BigDecimal.valueOf(depositAmount)).doubleValue();
                customerDetail.getAccount().setAmount(BigDecimal.valueOf(totalAmount));
                if (customerDetail.getAccount().getAccountType() == AccountType.PREMIUM && depositAmount >= 10000) {
                    customerDetail.getAccount().setLoyaltyPoints(customerDetail.getAccount().getLoyaltyPoints() + 10);
                }
            }
        }
        if (!accountFound) {
            throw new AccountException("--------------No account found please enter correct number----------------------------");

        }
    }

}
