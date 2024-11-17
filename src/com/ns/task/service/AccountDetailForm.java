package com.ns.task.service;

import com.ns.task.GDBankApplication;
import com.ns.task.bean.AccountStatus;
import com.ns.task.bean.AccountType;
import com.ns.task.bean.BankAccount;
import com.ns.task.bean.Customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AccountDetailForm {
    private static final String FILE_PATH = "C:\\Users\\gthathireddy\\OneDrive - NextSphere Technologies\\SelfDevelopment\\BankApplication\\src\\BankAccounts.txt";
    private AccountType accountType;
    private String accountNumber;
    private BigDecimal depositAmount;
    Long phone;
    static Scanner scanner = new Scanner(System.in);

    List<Customer> bankAccountDetails = GDBankApplication.bankAccountDetails;

    public void takingAccountDetails() throws IOException {

        Customer customerBankAccount = new Customer();
        BankAccount bankAccount = new BankAccount();
        System.out.print("Enter Pan-card number:");
        String panCard = scanner.next();

        boolean existingConfirmation = existingCustomer(panCard);
        if (!existingConfirmation) {
            System.out.print("Enter Name:");
            String name = scanner.next();

            System.out.print("Enter Mail-Id:");
            String mail = scanner.next();
            do {
                try {
                    System.out.print("Enter Phone Number:");
                    String phoneInput = scanner.next();
                    phone = Long.parseLong(phoneInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

            setAccountType();

            LocalDate accountOpenedDate = LocalDate.now();

            System.out.println("Enter Address:");
            String address = scanner.next();

            customerBankAccount.setName(name);
            customerBankAccount.setMail(mail);
            customerBankAccount.setPanCard(panCard);

            customerBankAccount.setAddress(address);

            bankAccount.setAmount(depositAmount);
            bankAccount.setOpeningBalance(depositAmount);
            bankAccount.setOpenedDate(accountOpenedDate);
            bankAccount.setAccountNumber(accountNumber);
            bankAccount.setAccountType(accountType);
            bankAccount.setStatus(AccountStatus.ACTIVE);
            bankAccount.setLoyaltyPoints(0);

            customerBankAccount.setAccount(bankAccount);//adding account to customer class
            bankAccountDetails.add(customerBankAccount);// adding bank details to list

            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Account Created success fully Your account number is-" + accountNumber);
            System.out.println("--------------------------------------------------------------------------------------");

            Path file = Paths.get(FILE_PATH);
            String customerDataToStore = String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%d,%s,%s,%d,%.2f,%d",
                    name, mail, phone, address,
                    panCard,
                    accountNumber, depositAmount, AccountStatus.ACTIVE, 0,
                    accountOpenedDate, accountType, 0, depositAmount, 0);

            customerDataToStore = customerDataToStore + System.lineSeparator();

            Files.write(file, customerDataToStore.getBytes(), StandardOpenOption.APPEND);

        } else {
            System.out.println("Already an account found for " + accountNumber);
        }
    }

    public void setAccountType() {
        System.out.print("Enter Amount to deposit:");
        BigDecimal amountToDeposit = new BigDecimal(0);
        do {
            try {
                String amountInput = scanner.next();
                amountToDeposit = new BigDecimal(amountInput);
                break;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("-------------------------Please enter valid amount--------------------");
            }
        } while (true);

        int selectedAccountType;
        do {
            try {
                System.out.print("""
                        Select your Account Type:
                        1.Savings\s
                        2.Current\s
                        3.Premium""");
                String selectedInput = scanner.next();

                selectedAccountType = Integer.parseInt(selectedInput);
                break;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Please selected valid Account type");
            }
        } while (true);
        int accountNumberGenerator = (int) (Math.random() * 1000);

        switch (selectedAccountType) {
            case 1 -> {
                accountType = AccountType.SAVINGS;

                if ((amountToDeposit.compareTo(BigDecimal.valueOf(5000)) >= 0)) {
                    depositAmount = amountToDeposit;
                } else {
                    System.err.println("For your selected account type i.e Savings account " +
                            "you need an minimum deposit of Rs.5000/-");
                    setAccountType();
                }

                accountNumber = "SA" + accountNumberGenerator;

            }
            case 2 -> {
                accountType = AccountType.CURRENT;

                if ((amountToDeposit.compareTo(BigDecimal.valueOf(0)) >= 0) && (amountToDeposit.compareTo(BigDecimal.valueOf(0)) > 0)) {
                    depositAmount = amountToDeposit.add(BigDecimal.valueOf(500000.0));
                } else {
                    System.out.println("For your selected account type i.e Current account " +
                            "you need an minimum deposit of Rs.0/-");
                    setAccountType();
                }

                accountNumber = "CA" + accountNumberGenerator;

            }
            case 3 -> {
                accountType = AccountType.PREMIUM;

                if ((amountToDeposit.compareTo(BigDecimal.valueOf(100000)) >= 0) && (amountToDeposit.compareTo(BigDecimal.valueOf(0)) > 0)) {
                    depositAmount = amountToDeposit;
                } else {
                    System.out.println("For your selected account type i.e Premium account " +
                            "you need an minimum deposit of Rs.1Lakh-");
                    setAccountType();
                }
                accountNumber = "PA" + accountNumberGenerator;
            }
            default -> {
                System.err.println("Please select the above mentioned account type only");
                setAccountType();
            }
        }
    }

    public boolean existingCustomer(String panCard) {
        for (Customer customerDetails : bankAccountDetails) {
            if (panCard.equals(customerDetails.getPanCard())) {
                return true;
            }
        }
        return false;
    }
}
