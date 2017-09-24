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

    private Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    private void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    public boolean isBusy() {
        return getCurrentPassenger() != null;
    }


    public void processPassengers(Queue passengerQueue, int time) {
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
                    System.out.println("\t\t" + getType() + ": Finished " + passenger.getSeatingClass() + passenger.getPassengerNumber() + " at time " + time);

                } else if(passenger.getStartProcessingTime() == 0) {
                    passenger.setStartProcessingTime(time);
                    passenger.setProcessingDuration(2);
                    System.out.println("\t\t" + getType() + ": Started " + passenger.getSeatingClass() + passenger.getPassengerNumber() + " at time " + time);
                }
            }
        }
    }

    /*
        This method will process first class customers, if all coach stations are busy and there are available
        first class stations this first class station will process coach customers
     */
//    public void processPassengers(Queue fcQueue, Queue ccQueue, int time) {
//        Passenger passenger = getCurrentPassenger();
//        if(fcQueue.getTotal() > 0 || ccQueue.getTotal() > 0 || getCurrentPassenger() != null) {
//            if(passenger == null) {
//                if(fcQueue.getTotal() == 0 && ccQueue.getTotal() > 0) {
//                    setCurrentPassenger((Passenger)ccQueue.dequeue().getData());
//                    passenger = getCurrentPassenger();
//                } else {
//                    setCurrentPassenger((Passenger)fcQueue.dequeue().getData());
//                    passenger = getCurrentPassenger();
//                }
//
//
//            }
//            if(passenger.getArrivalTime() <= time) {
//                if(passenger.getStartProcessingTime() > 0 &&
//                        time == passenger.getStartProcessingTime() + passenger.getProcessingDuration()) {
//                    currentPassenger = null; //Ready to process new passenger
//                    System.out.println("\t\t" + getType() + ": Finished " + passenger.getSeatingClass() + passenger.getPassengerNumber() + " at time " + time);
//
//                } else if(passenger.getStartProcessingTime() == 0) {
//                    passenger.setStartProcessingTime(time);
//                    passenger.setProcessingDuration(2);
//                    System.out.println("\t\t" + getType() + ": Started " + passenger.getSeatingClass() + passenger.getPassengerNumber() + " at time " + time);
//                }
//            }
//        }
//    }
}
