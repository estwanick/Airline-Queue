public class Passenger implements Comparable{

    private int passengerNumber;
    private String seatingClass;
    private int arrivalTime;
    private int startProcessingTime;
    private int processingDuration;

    public Passenger(int passengerNumber, String seatingClass, int arrivalTime) {
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

    public int getArrivalTime() { return arrivalTime; }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getStartProcessingTime() {
        return startProcessingTime;
    }

    public void setStartProcessingTime(int startProcessingTime) {
        this.startProcessingTime = startProcessingTime;
    }

    public int getProcessingDuration() {
        return processingDuration;
    }

    public void setProcessingDuration(int processingDuration) {
        this.processingDuration = processingDuration;
    }

    @Override
    public int compareTo(Object o) {
        if(this.getPassengerNumber() == ((Passenger)o).getPassengerNumber()) {
            return 0;
        } else if (this.getArrivalTime() > ((Passenger)o).getArrivalTime()){
            return 1;
        } else {
            return -1;
        }

    }
}
