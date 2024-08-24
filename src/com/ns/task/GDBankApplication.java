package com.ns.task;

import com.ns.task.account.IAccount;
import com.ns.task.account.CurrentAccount;
import com.ns.task.account.PremiumAccount;
import com.ns.task.account.SavingsAccount;
import com.ns.task.bean.AccountType;
import com.ns.task.exception.WithdrawException;
import com.ns.task.bean.Customer;
import com.ns.task.fileManager.GetFileData;
import com.ns.task.fileManager.WriteToFile;
import com.ns.task.service.AccountDetailForm;
import com.ns.task.service.BankOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GDBankApplication {
    private static final Scanner scanner = new Scanner(System.in);
    public static final WriteToFile writeUpdatedDataToFile = new WriteToFile();
    public static final List<Customer> bankAccountDetails = new ArrayList<>();


    public static void main(String[] args) throws IOException, WithdrawException {
        GetFileData getFileData = new GetFileData();
        getFileData.getDataFromFileAndAddToList();

        new GDBankApplication().selectOperation();
    }

    public void selectOperation() throws WithdrawException, IOException {
        String continueOperation = "Y";
        BankOperation bankOperation = new BankOperation();
        do {
            System.out.print("""
                    Select the operation to continue:
                    1.Open Account\s
                    2.Withdraw\s
                    3.Deposit\s
                    4.Show account details\s
                    5.Deactivate Account\s
                    6.Activate Account\s
                    7.Get All Customer\s
                    8.End Operation""");
            String operation = scanner.next();

            switch (operation) {
                case "1" -> {
                    AccountDetailForm accountDetailForm = new AccountDetailForm();
                    accountDetailForm.takingAccountDetails();
                }
                case "2" -> bankOperation.withDraw();
                case "3" -> bankOperation.deposit();
                case "4" -> {
                    System.out.println("To see your account details enter your account number");
                    String accountNumber = scanner.next();
                    System.out.println(bankOperation.findAccountDetails(accountNumber));

                }
                case "5" -> {
                    System.out.println("Enter the account number to IDLE");
                    String accountNumber = scanner.next();
                    IAccount.idleTheAccount(accountNumber);
                    writeUpdatedDataToFile.writeUpdatedData();
                }
                case "6" -> {
                    System.out.println("Enter the account number to ACTIVATE");
                    String accountNumber = scanner.next();
                    IAccount.activateTheAccount(accountNumber);
                    writeUpdatedDataToFile.writeUpdatedData();
                }
                case "7" -> bankOperation.getAllCustomerDetails();
                case "8" -> continueOperation = "N";
                default ->  System.err.println("Not a valid Response");
            }
        } while (continueOperation.equalsIgnoreCase("Y"));
    }
}