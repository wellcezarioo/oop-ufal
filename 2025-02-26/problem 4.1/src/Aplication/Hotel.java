package Aplication;

import Entities.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Hotel {
    public static void main(String[] args) {
        try {

            Scanner sc = new Scanner(System.in);

            System.out.print("Room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Check-in date (dd/mm/yyyy) ");

            String data = sc.nextLine();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date checkInDate = format.parse(data);


            System.out.print("Check-out date (dd/mm/yyyy) ");

            data = sc.nextLine();

            Date checkOutDate = format.parse(data);

            Reservation reservation = new Reservation(roomNumber, checkInDate, checkOutDate);
            System.out.println(reservation);

            System.out.println("\nEnter date to update reservation: ");
            System.out.print("Check-in date (dd/mm/yyyy) ");

            data = sc.nextLine();
            checkInDate = format.parse(data);

            System.out.print("Check-out date (dd/mm/yyyy) ");

            data = sc.nextLine();
            checkOutDate = format.parse(data);

            reservation.updateDates(checkInDate, checkOutDate);
            System.out.println(reservation);

            sc.close();

        }

        catch ( ParseException e ){
            System.out.println("Wasn't possible to covert date data");
        }

        catch ( IllegalArgumentException e ){
            System.out.println(e.getMessage());
        }

        catch (RuntimeException e){
            System.out.println("Unexpected error");
        }
    }
}
