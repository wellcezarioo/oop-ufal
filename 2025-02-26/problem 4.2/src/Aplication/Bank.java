package Aplication;

import Entities.Account;

import java.util.Scanner;

public class Bank {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            int number;
            System.out.println("Enter account data");
            System.out.print("Number: ");
            number = sc.nextInt();
            sc.nextLine();

            String holder;
            System.out.print("Holder: ");
            holder = sc.nextLine();

            double balance;
            System.out.print("Initial balance: ");
            balance = sc.nextDouble();

            double withdrawLimit;
            System.out.print("Withdraw limit: ");
            withdrawLimit = sc.nextDouble();

            Account account = new Account(number, holder, balance, withdrawLimit);

            double withdraw;
            System.out.print("\nEnter amount for withdraw: ");
            withdraw = sc.nextDouble();

            account.withdraw(withdraw);
            System.out.println("New balance: " + account.getBalance());

        }
        catch (IllegalArgumentException e){
            System.out.println("Withdrae error: " + e.getMessage());
        }

        sc.close();

    }
}
