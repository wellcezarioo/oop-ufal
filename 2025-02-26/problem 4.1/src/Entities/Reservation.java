package Entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(int roomNumber, Date checkIn, Date checkOut) {
        this.roomNumber = roomNumber;
        updateDates(checkIn, checkOut);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {

        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public int duration(){
        return (int)((checkOut.getTime() - checkIn.getTime())/(1000*60*60*24));
    }

    public void updateDates(Date checkIn, Date checkOut) throws IllegalArgumentException{

        Date now = new Date();
        if(checkIn.after(checkOut)){
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        if (checkIn.before(now)){
            throw new IllegalArgumentException("Reservation dates for updates must be must be in future");
        }

        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Room "
                + getRoomNumber()
                + ", Check-in: "
                + sdf.format(getCheckIn())
                + ", Check-out: "
                + sdf.format(getCheckOut())
                + ", "
                + duration()
                + " nights";
    }


}
