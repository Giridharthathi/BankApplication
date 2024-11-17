package com.ns.task.bean;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private String mail;
    private Long phoneNumber;
    private String address;
    private String panCard;
    private BankAccount account;

    public Customer() {
    }

    public Customer(String name, String mail, Long phoneNumber, String address, String panCard, BankAccount account) {
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.panCard = panCard;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", panCard='" + panCard + '\'' +
                ", \n \taccount=" + account +
                '}';
    }
}
