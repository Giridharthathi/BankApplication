package com.ns.task.account;

import com.ns.task.bean.AccountStatus;
import com.ns.task.bean.AccountType;
import com.ns.task.bean.Customer;
import com.ns.task.exception.AccountException;
import com.ns.task.exception.WithdrawException;

import java.math.BigDecimal;

import static com.com.ns.task.GDBankApplication.bankAccountDetails;

public final class PremiumAccount implements IAccount {
    public PremiumAccount() {
    }

    @Override
    public void withDraw(String accountNumber, double amountToWithdraw) throws WithdrawException {
        boolean accountFound = false;
            for (Customer customerAccounts : bankAccountDetails) {
                if (accountNumber.equals(customerAccounts.getAccount().getAccountNumber())) {
                    accountFound = true;
                    if (customerAccounts.getAccount().isStatus() == AccountStatus.ACTIVE) {
                        if (customerAccounts.getAccount().getAmount().
                                compareTo(BigDecimal.valueOf(amountToWithdraw))>0) {
                            double totalAmount = customerAccounts.getAccount().getAmount().subtract(BigDecimal.valueOf(amountToWithdraw)).doubleValue();
                            customerAccounts.getAccount().setAmount(BigDecimal.valueOf(totalAmount));
                            System.out.println("Amount has been withdrawn of Rs:" + amountToWithdraw + "from accountNumber" +
                                    accountNumber);
                            break;
                        } else {
                            throw new WithdrawException("You should enter the amount less then your account");
                        }
                    } else {
                        throw new WithdrawException("Your account is inactive please contact our support team");
                    }
                }
            }
            if (accountFound) {
                throw new AccountException("----------------------------Account not found---------------------");
            }
    }
    public void deposit(String accountNumber, double depositAmount) {

        boolean accountFound = false;

        for(Customer customerDetail : bankAccountDetails){
            if (accountNumber.equals(customerDetail.getAccount().getAccountNumber())) {
                accountFound= true;
                double totalAmount = customerDetail.getAccount().getAmount().add(BigDecimal.valueOf(depositAmount)).doubleValue();
                customerDetail.getAccount().setAmount(BigDecimal.valueOf(totalAmount));
                if (customerDetail.getAccount().getAccountType() == AccountType.PREMIUM && depositAmount >= 10000) {
                    customerDetail.getAccount().setLoyaltyPoints(customerDetail.getAccount().getLoyaltyPoints() + 10);
                    break;
                }
            }
        }
        if(!accountFound){
            throw new AccountException("--------------No account found please enter correct number----------------------------");

        }
    }

}
