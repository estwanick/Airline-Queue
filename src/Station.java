public class Station {

    private String type;
    Passenger currentPassenger = null;
    Statistics stats;
    int fcServiceTime;
    int ccServiceTime;

    public Station(String type, int fcServiceTime, int ccServiceTime, Statistics stats) {
        this.type = type;
        this.stats = stats;
        this.fcServiceTime = fcServiceTime;
        this.ccServiceTime = ccServiceTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFcServiceTime() {
        return fcServiceTime;
    }

    public void setFcServiceTime(int fcServiceTime) {
        this.fcServiceTime = fcServiceTime;
    }

    public int getCcServiceTime() {
        return ccServiceTime;
    }

    public void setCcServiceTime(int ccServiceTime) {
        this.ccServiceTime = ccServiceTime;
    }

    private Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    private void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    public boolean isBusy() {
        return getCurrentPassenger() != null;
    }


    public void processPassengers(Queue passengerQueue, int time, int serviceRange) {
        Passenger passenger = getCurrentPassenger();
        if(passengerQueue.getTotal() > 0 || getCurrentPassenger() != null) {
            if(passenger == null) {
                setCurrentPassenger((Passenger)passengerQueue.dequeue().getData());
                passenger = getCurrentPassenger();
            }
            if(passenger.getArrivalTime() <= time) {
                if(passenger.getStartProcessingTime() > 0 &&
                        time == passenger.getStartProcessingTime() + passenger.getProcessingDuration()) {
                    setCurrentPassenger(null);
                    passenger.setProcessed(true);
                    stats.enqueue(passenger, getType());
                    System.out.println("\t\t" + getType() + ": Finished " + passenger.getSeatingClass() + " Passenger " + passenger.getPassengerNumber() + " at time " + time);
                } else if(passenger.getStartProcessingTime() == 0) {
                    passenger.setStartProcessingTime(time);
                    if(passenger.getSeatingClass() == CONSTANTS.FIRSTCLASS) {
                        passenger.setProcessingDuration(getFcServiceTime() + randomInRange(0, serviceRange));
                    } else {
                        passenger.setProcessingDuration(getCcServiceTime() + randomInRange(0, serviceRange));
                    }
                    System.out.println("\t\t" + getType() + ": Started " + passenger.getSeatingClass() + " Passenger " + passenger.getPassengerNumber() + " at time " + time + ", servicing time " + passenger.getProcessingDuration());
                }
            }
        }
    }
    //Move into utils class
    private int randomInRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
