public class Passenger {

    private int passengerNumber;
    private String seatingClass;
    private double arrivalTime;

    public Passenger(int passengerNumber, String seatingClass, double arrivalTime) {
        this.passengerNumber = passengerNumber;
        this.seatingClass = seatingClass;
        this.arrivalTime = arrivalTime;
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
