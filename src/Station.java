public class Station {

    private boolean busy;
    private String type;

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

    public boolean processPassengers(Queue passengers, int time) {
        //System.out.println(passengers + " at time " + time);

        if(passengers.getTotal() != 0) {
            Passenger passenger = (Passenger) passengers.peek().getData();
            if(passenger.getArrivalTime() <= time) {

                if(passenger.getStartProcessingTime() > 0 &&
                        time == passenger.getStartProcessingTime() + passenger.getProcessingDuration()) {
                    passengers.dequeue();

                    System.out.println("Finished " + passenger.getPassengerNumber() + " at time " + time);

                } else if(passenger.getStartProcessingTime() == 0) {
                    passenger.setStartProcessingTime(time);
                    passenger.setProcessingDuration(2);
                    System.out.println("Started " + passenger.getPassengerNumber() + " at time " + time);
                }


            }
        }

        return true;
    }
}
