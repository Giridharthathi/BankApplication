package com.ns.task.account;

import com.ns.task.bean.AccountStatus;
import com.ns.task.bean.Customer;
import com.ns.task.exception.WithdrawException;

import java.math.BigDecimal;

import static com.com.ns.task.GDBankApplication.bankAccountDetails;

public final class CurrentAccount implements IAccount {
    private static final double OVERDRAFT_LIMIT = 500000.0;

    @Override
    public void withDraw(String accountNumber, double amountToWithdraw) {
        try {
            boolean accountFound = false;
            for (Customer customerAccounts : bankAccountDetails) {

                if (accountNumber.equals(customerAccounts.getAccount().getAccountNumber())) {
                    accountFound = true;
                    if (customerAccounts.getAccount().isStatus() == AccountStatus.ACTIVE) {
                        BigDecimal overDraftLimit = BigDecimal.valueOf(OVERDRAFT_LIMIT);
                        BigDecimal totalOverDraftLimitWithBalance = customerAccounts.getAccount().getAmount().add(overDraftLimit);

                        if (customerAccounts.getAccount().getAmount().compareTo(BigDecimal.valueOf(amountToWithdraw)) > 0) {

                            BigDecimal totalAmount = customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw));
                            customerAccounts.getAccount().setAmount(totalAmount);
                            System.out.println("Amount has been withdrawn of Rs:" + amountToWithdraw + "from accountNumber" +
                                    accountNumber);
                            break;

                        } else if (totalOverDraftLimitWithBalance.compareTo(BigDecimal.valueOf(amountToWithdraw)) >= 0) {
                            System.out.println("Your are using over draft limit, you limit is:" + ((customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw))).add(overDraftLimit)));

                            customerAccounts.getAccount().setAmount(customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw)));//total balance after with draw

                            System.out.println("Amount has been withdrawn of Rs:" + amountToWithdraw + "from accountNumber" +
                                    accountNumber);
                        } else {
                            throw new WithdrawException("You have exhausted your Overdraft limit as  well as your balance is zero");
                        }
                    } else {
                        throw new WithdrawException("Your account is inactive please contact our support team");
                    }
                }
            }
            if (!accountFound) {
                System.out.println("Account not found");
            }
        } catch (WithdrawException withdrawException) {
            System.out.println(withdrawException.getMessage());
        }
    }

    public void deposit(String accountNumber, double depositAmount) {

        boolean accountFound = false;

        for (Customer customerDetail : bankAccountDetails) {
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound = true;
                BigDecimal totalAmount = customerDetail.getAccount().getAmount().add(BigDecimal.valueOf(depositAmount));
                customerDetail.getAccount().setAmount(totalAmount);
                break;
            }
        }
        if (!accountFound) {
            System.out.println("--------------No account found please enter correct number----------------------------");
        }
    }
}
