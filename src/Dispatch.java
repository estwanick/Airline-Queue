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
        fcPassengers = passengerGenerator(fcPassengerCount, CONSTANTS.FIRSTCLASS, avgFirstArrival, absLateRange);
        ccPassengers = passengerGenerator(ccPassengerCount, CONSTANTS.COACHCLASS, avgCoachArrival, absLateRange);
//        printFcPassengers();
//        printCcPassengers();


//        fcPassengers.add(new Passenger(1, CONSTANTS.FIRSTCLASS, 5));
//        fcPassengers.add(new Passenger(2, CONSTANTS.FIRSTCLASS, 5));
//        fcPassengers.add(new Passenger(3, CONSTANTS.FIRSTCLASS, 6));
//        fcPassengers.add(new Passenger(4, CONSTANTS.FIRSTCLASS, 7));
//        fcPassengers.add(new Passenger(5, CONSTANTS.FIRSTCLASS, 7));
//        fcPassengers.add(new Passenger(6, CONSTANTS.FIRSTCLASS, 7));
//        fcPassengers.add(new Passenger(7, CONSTANTS.FIRSTCLASS, 10));
//        fcPassengers.add(new Passenger(8, CONSTANTS.FIRSTCLASS, 12));
//        ccPassengers.add(new Passenger(11, CONSTANTS.COACHCLASS, 5));
//        ccPassengers.add(new Passenger(11, CONSTANTS.COACHCLASS, 5));
//        ccPassengers.add(new Passenger(13, CONSTANTS.COACHCLASS, 5));
//        ccPassengers.add(new Passenger(14, CONSTANTS.COACHCLASS, 15));
//        ccPassengers.add(new Passenger(15, CONSTANTS.COACHCLASS, 6));
//        ccPassengers.add(new Passenger(16, CONSTANTS.COACHCLASS, 7));
//        ccPassengers.add(new Passenger(17, CONSTANTS.COACHCLASS, 17));
//        ccPassengers.add(new Passenger(18, CONSTANTS.COACHCLASS, 7));

        fc1 = new Station(CONSTANTS.FIRSTCLASS + "-1", avgFirstService, avgCoachService, stats);
        fc2 = new Station(CONSTANTS.FIRSTCLASS + "-2", avgFirstService, avgCoachService, stats);
        cc1 = new Station(CONSTANTS.COACHCLASS + "-1", avgFirstService, avgCoachService, stats);
        cc2 = new Station(CONSTANTS.COACHCLASS + "-3", avgFirstService, avgCoachService, stats);
        cc3 = new Station(CONSTANTS.COACHCLASS + "-3", avgFirstService, avgCoachService, stats);

        this.avgCoachArrival = avgCoachArrival;
        this.avgCoachService = avgCoachService;
        this.avgFirstArrival = avgFirstArrival;
        this.avgFirstService = avgFirstService;
        this.avgServiceBuffer = avgServiceBuffer;
        this.simulationDuration = simulationDuration;
        startSimulation();
        stats.outputStats();
    }

    public void placeInQueue(Passenger passenger, int time) {
        //System.out.println("\t Putting " + passenger.getPassengerNumber() + " in queue: " + passenger.getArrivalTime());
        if(passenger.getSeatingClass() == CONSTANTS.FIRSTCLASS) {
            fcStationsLine.enqueue(passenger);
        } else {
            // Place in coach, or first class if all coach queues are filled and there are empty fc queues
            if(cc1.isBusy() && cc2.isBusy() && cc3.isBusy()) {
                //System.out.println("all coach stations are busy over flow to first class if possible");
                if(!fc1.isBusy() || !fc2.isBusy()) {
                    System.out.println("over flow into fc station queue");
                    fcStationsLine.enqueue(passenger);
                } else {
                    System.out.println("fc stations are busy over flow not possible, moving to coach queue");
                    ccStationsLine.enqueue(passenger);
                }
            } else {
                ccStationsLine.enqueue(passenger);
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
                ccPassengers.emptyHeap();
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
