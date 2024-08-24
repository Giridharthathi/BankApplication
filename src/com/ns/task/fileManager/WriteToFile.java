package com.ns.task.fileManager;

import com.ns.task.bean.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.ns.task.GDBankApplication.bankAccountDetails;

public class WriteToFile {
    private static final String FILE_PATH ="C:\\Users\\gthathireddy\\OneDrive - NextSphere Technologies\\SelfDevelopment\\BankApplication\\src\\BankAccounts.txt";

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
