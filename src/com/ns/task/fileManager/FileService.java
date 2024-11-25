package com.ns.task.fileManager;

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
import java.util.Date;
import java.util.List;

import static com.ns.task.GDBankApplication.bankAccountDetails;

public class FileService {
    private static final String FILE_PATH ="src\\BankAccounts.txt";

    public void getDataFromFileAndAddToList() throws IOException {
        Customer customer;
        BankAccount bankAccount;

        Path file = Paths.get(FILE_PATH);
        if (!Files.exists(file)) {
            Files.createFile(file);
        }

        List<String> customersData = Files.readAllLines(file);

        for (String customerDetail : customersData) {

            customer = new Customer();
            bankAccount = new BankAccount();
            String[] customerData = customerDetail.split(",");

            customer.setName(customerData[0]);
            customer.setMail(customerData[1]);
            customer.setPhoneNumber(Long.parseLong(customerData[2]));
            customer.setAddress(customerData[3]);
            customer.setPanCard(customerData[4]);

            bankAccount.setAccountNumber(customerData[5]);
            bankAccount.setAmount(new BigDecimal(customerData[6]));
            bankAccount.setStatus(AccountStatus.valueOf(customerData[7]));
            bankAccount.setInterestAmount(new BigDecimal(customerData[8]));
            bankAccount.setOpenedDate(LocalDate.parse(customerData[9]));
            bankAccount.setAccountType(AccountType.valueOf(customerData[10]));
            bankAccount.setLoyaltyPoints(Integer.parseInt(customerData[11]));
            bankAccount.setOpeningBalance(new BigDecimal(customerData[12]));
            bankAccount.setWithdrawAttempts(Integer.parseInt(customerData[13]));

            customer.setAccount(bankAccount);

            bankAccountDetails.add(customer);
        }
        if (new Date().getDate() == 1) {
            changingOpeningBalance();
        }
    }
    public void changingOpeningBalance() {
        for (Customer customerDetails : bankAccountDetails) {
            customerDetails.getAccount().setOpeningBalance(customerDetails.getAccount().getAmount());
        }
    }

    public void writeUpdatedData() {
        Path file = Paths.get(FILE_PATH);
        try {
            Files.newOutputStream(file, StandardOpenOption.TRUNCATE_EXISTING).close();
            for (Customer customerDetails : bankAccountDetails) {

                String customerDataToStore =
                        customerDetails.getName()+","+customerDetails.getMail()+","+customerDetails.getPhoneNumber()+","+
                                customerDetails.getAddress()+","+customerDetails.getPanCard()+","+
                                customerDetails.getAccount().getAccountNumber()+","+customerDetails.getAccount().getAmount()+","+
                                customerDetails.getAccount().isStatus()+","+customerDetails.getAccount().getInterestAmount()+","+
                                customerDetails.getAccount().getOpenedDate()+","+
                                customerDetails.getAccount().getAccountType()+","+customerDetails.getAccount().getLoyaltyPoints()+","+
                                customerDetails.getAccount().getOpeningBalance()+","+customerDetails.getAccount().getWithdrawAttempts()+"\n";

                Files.write(file, customerDataToStore.getBytes(), StandardOpenOption.APPEND);
            }
            System.out.println("Data has been successfully updated in the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while updating file contents: " + e.getMessage());
        }
    }
}
