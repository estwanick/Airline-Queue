import java.util.Iterator;

public class Dispatch {

    int simulationDuration;
    int avgCoachArrival;
    int avgCoachService;
    int avgFirstArrival;
    int avgFirstService;
    int avgServiceBuffer;

    Statistics stats = new Statistics();

    Queue fcStationsLine = new Queue<Queue>();
    Queue ccStationsLine = new Queue<Queue>();

    Station fc1, fc2, cc1, cc2, cc3;

    PriorityQueue fcPassengers = new PriorityQueue<Passenger>(10);
    PriorityQueue ccPassengers = new PriorityQueue<Passenger>(10);

    public Dispatch(int simulationDuration, int avgCoachArrival, int avgCoachService, int avgFirstArrival, int avgFirstService,
                    int absLateRange, int avgServiceBuffer, int fcPassengerCount, int ccPassengerCount) {

        this.avgCoachArrival = avgCoachArrival;
        this.avgCoachService = avgCoachService;
        this.avgFirstArrival = avgFirstArrival;
        this.avgFirstService = avgFirstService;
        this.avgServiceBuffer = avgServiceBuffer;
        this.simulationDuration = simulationDuration;

        fcPassengers = passengerGenerator(fcPassengerCount, CONSTANTS.FIRSTCLASS, avgFirstArrival, absLateRange);
        ccPassengers = passengerGenerator(ccPassengerCount, CONSTANTS.COACHCLASS, avgCoachArrival, absLateRange);

        fc1 = new Station(CONSTANTS.FIRSTCLASS + "-1", avgFirstService, avgCoachService, stats);
        fc2 = new Station(CONSTANTS.FIRSTCLASS + "-2", avgFirstService, avgCoachService, stats);
        cc1 = new Station(CONSTANTS.COACHCLASS + "-1", avgFirstService, avgCoachService, stats);
        cc2 = new Station(CONSTANTS.COACHCLASS + "-2", avgFirstService, avgCoachService, stats);
        cc3 = new Station(CONSTANTS.COACHCLASS + "-3", avgFirstService, avgCoachService, stats);

        startSimulation();
        stats.setAllowedDuration(simulationDuration);
        stats.outputStats();
    }

    public void placeInQueue(Passenger passenger, int time) {
        //System.out.println("\t Putting " + passenger.getPassengerNumber() + " in queue: " + passenger.getArrivalTime());
        if(passenger.getSeatingClass() == CONSTANTS.FIRSTCLASS) {
            fcStationsLine.enqueue(passenger);
            System.out.println("\t Putting " + passenger.getSeatingClass() + ": " + passenger.getPassengerNumber() + " in queue FC at time: " + passenger.getArrivalTime());
        } else {
            // Place in coach, or first class if all coach queues are filled and there are empty fc queues
            if(cc1.isBusy() && cc2.isBusy() && cc3.isBusy()) {
                System.out.println("\t **all coach stations are busy over flow to first class if possible");
                if(!fc1.isBusy() || !fc2.isBusy()) {
                    fcStationsLine.enqueue(passenger);
                    System.out.println("\t **over flow into fc station queue");
                    System.out.println("\t Putting " + passenger.getSeatingClass() + ": " + passenger.getPassengerNumber() + " in queue FC at time: " + passenger.getArrivalTime());
                } else {
                    ccStationsLine.enqueue(passenger);
                    System.out.println("\t **fc stations are busy over flow not possible, moving to coach queue");
                    System.out.println("\t Putting " + passenger.getSeatingClass() + ": " + passenger.getPassengerNumber() + " in queue CC at time: " + passenger.getArrivalTime());
                }
            } else {
                ccStationsLine.enqueue(passenger);
                System.out.println("\t Putting " + passenger.getSeatingClass() + ": " + passenger.getPassengerNumber() + " in queue CC at time: " + passenger.getArrivalTime());
            }
        }
    }

    public void startSimulation() {
        int timer = 0;
        Passenger fpMin;
        Passenger cpMin;
        while(ccPassengers.getSize() > 0 || fcPassengers.getSize() > 0 || stationsBusy()) {
            System.out.println("Time: " + timer + " ");

            // Place passenger in queue when they arrive
            if(timer > simulationDuration) {
                if(ccPassengers.getSize() > 0)
                    ccPassengers.emptyHeap();
                if(fcPassengers.getSize() > 0)
                    fcPassengers.emptyHeap();
            } else {
                while(fcPassengers.peek() != null && ((Passenger) fcPassengers.peek()).getArrivalTime() == timer) {
                    fpMin = (Passenger)fcPassengers.fetchMin();
                    placeInQueue(fpMin, timer);
                }

                while(ccPassengers.peek() != null && ((Passenger) ccPassengers.peek()).getArrivalTime() == timer) {
                    cpMin = (Passenger)ccPassengers.fetchMin();
                    placeInQueue(cpMin, timer);
                }
            }

            stats.setCcMaxQueueLength(ccStationsLine.getTotal());
            stats.setFcMaxQueueLength(fcStationsLine.getTotal());

            fc1.processPassengers(fcStationsLine, timer, avgServiceBuffer);
            fc2.processPassengers(fcStationsLine, timer, avgServiceBuffer);
            cc1.processPassengers(ccStationsLine, timer, avgServiceBuffer);
            cc2.processPassengers(ccStationsLine, timer, avgServiceBuffer);
            cc3.processPassengers(ccStationsLine, timer, avgServiceBuffer);

            if(timer == simulationDuration) {
                System.out.println("\t\t" + "**Passengers after this will miss their flight");
            }

            timer++;
        }

        stats.setTotalDuration(timer);
    }

    private boolean stationsBusy() {
        if(cc1.isBusy() || cc2.isBusy() || cc3.isBusy() || fc1.isBusy() || fc2.isBusy()) {
            return true;
        } else {
            return false;
        }
    }

    private void inOrderDispersal() {
        while(fcPassengers.getSize() > 0 || ccPassengers.getSize() > 0) {
            if(fcPassengers.getSize() > 0 && ccPassengers.getSize() > 0) {
                if(lessThan((Passenger)fcPassengers.peek(), (Passenger)ccPassengers.peek())) {
                    Passenger fp = (Passenger)fcPassengers.fetchMin();
                    System.out.println("Popped: FC" + fp.getPassengerNumber());
                } else {
                    Passenger cp = (Passenger)ccPassengers.fetchMin();
                    System.out.println("Popped: CC" + cp.getPassengerNumber());
                }
            } else {
                if(fcPassengers.getSize() - 1 > 0) {
                    Passenger fp = (Passenger)fcPassengers.fetchMin();
                    System.out.println("Popped last: FC" + fp.getPassengerNumber());
                } else if(ccPassengers.getSize() - 1 > 0) {
                    Passenger cp = (Passenger)fcPassengers.fetchMin();
                    System.out.println("Popped last: CC" + cp.getPassengerNumber());
                }
            }
        }
    }

    private boolean greaterThan(Passenger x, Passenger y) {
        return ((Comparable<Passenger>) x).compareTo(y) > 0;
    }

    private boolean lessThan(Passenger x, Passenger y) {
        return ((Comparable<Passenger>) x).compareTo(y) < 0;
    }

    private PriorityQueue getFcPassengers() {
        return fcPassengers;
    }

    private PriorityQueue getCcPassengers() {
        return ccPassengers;
    }

    public void printFcPassengers() {
        while(fcPassengers.getSize() > 0) {
            Passenger removedPassenger = ((Passenger)fcPassengers.fetchMin());
            System.out.println("FC Passenger #" + removedPassenger.getPassengerNumber());
            System.out.println("Arrival Time: " + removedPassenger.getArrivalTime());
            System.out.println("---");
        }
    }

    public void printCcPassengers() {
        while(ccPassengers.getSize() > 0) {
            Passenger removedPassenger = ((Passenger)ccPassengers.fetchMin());
            System.out.println("CC Passenger #" + removedPassenger.getPassengerNumber());
            System.out.println("Arrival Time: " + removedPassenger.getArrivalTime());
            System.out.println("---");
        }
    }

    private PriorityQueue passengerGenerator(int passengerCount, String passengerClass, int avgFirstArrival, int randomBuffer) {
        PriorityQueue pq = new PriorityQueue(passengerCount);
        for(int i=1; i <= passengerCount; i++) {
            int randomArrival = avgFirstArrival * i + randomInRange(-randomBuffer, randomBuffer);
            if(randomArrival < 0) {
                randomArrival = 0;
            }
            pq.add(new Passenger(i, passengerClass, randomArrival));
        }
        return pq;
    }

    private int randomInRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
