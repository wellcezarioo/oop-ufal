package Aplication;

import Entities.Company;
import Entities.Individual;
import Entities.TaxPayer;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Taxes {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of taxes payers: ");
        int payers = sc.nextInt();

        sc.nextLine();

        ArrayList<TaxPayer> payerlist= new ArrayList <TaxPayer>();

        for(int i = 1; i <= payers; i++){
            System.out.println("Payer #" + i + " data:");
            String payerType;

            System.out.print("Individual or company (i/c)? ");
            payerType = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Anual income: ");
            double anualIncome = sc.nextDouble();

            if(payerType.equals("i")){

                System.out.print("Health expenditures: ");
                double expenditures = sc.nextDouble();
                payerlist.add(new Individual(name, anualIncome, expenditures));
            }
            else if(payerType.equals("c")){

                System.out.print("Number of employees: ");
                int employees = sc.nextInt();
                payerlist.add(new Company(name, anualIncome, employees));
            }

            sc.nextLine();
        }

        double sum = 0;
        System.out.println("\nTAXES PAID: ");
        for(TaxPayer i: payerlist){
            System.out.println(i.getName() + " $ " + String.format("%.2f", i.calculateTaxes()));
            sum += i.calculateTaxes();
        }

        System.out.print("\nTOTAL TAXES: $ " + String.format("%.2f", sum));
        sc.close();
    }
}