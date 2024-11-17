package com.ns.task;

import com.ns.task.bean.Customer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class mainClass {
     public static void main(String[] args) {
        System.out.println("Main method");
//
        List<Customer> list = GDBankApplication.bankAccountDetails;

         List<Customer> list1 = list.stream().sorted(Comparator.comparing(Customer::getPanCard).reversed()).toList();
         System.out.println(list1);
     }
}
