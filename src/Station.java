public class Station {

    private boolean busy;
    private String type;
    Passenger currentPassenger = null;

    public Station(String type) {
        this.busy = false;
        this.type = type;
    }

    public boolean getBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    public void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    public boolean processPassengers(Queue passengers, int time) {
        //System.out.println(passengers + " at time " + time);
        Passenger passenger = getCurrentPassenger();

        if(passengers.getTotal() > 0 || getCurrentPassenger() != null) {

            if(passenger == null) {
                setCurrentPassenger((Passenger)passengers.dequeue().getData());
                passenger = getCurrentPassenger();
            }

            if(passenger.getArrivalTime() <= time) {

                if(passenger.getStartProcessingTime() > 0 &&
                        time == passenger.getStartProcessingTime() + passenger.getProcessingDuration()) {

                    currentPassenger = null;
                    System.out.println("\t\t Finished " + passenger.getPassengerNumber() + " at time " + time);

                } else if(passenger.getStartProcessingTime() == 0) {

                    passenger.setStartProcessingTime(time);
                    passenger.setProcessingDuration(2);
                    System.out.println("\t\t Started " + passenger.getPassengerNumber() + " at time " + time);
                }


            }
        }

        return true;
    }
}
