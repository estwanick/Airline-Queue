public class Passenger {

    private int passengerNumber;
    private String seatingClass;

    public Passenger(int passengerNumber, String seatingClass) {
        this.passengerNumber = passengerNumber;
        this.seatingClass = seatingClass;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public String getSeatingClass() {
        return seatingClass;
    }

    public void setSeatingClass(String seatingClass) {
        this.seatingClass = seatingClass;
    }
}
