import java.util.Iterator;

public class Dispatch {

    int simulationDuration;
    final int closeQueue = 40;
    double avgCoachArrival;
    double avgCoachService;
    double avgFirstArrival;
    double avgFirstService;

    Queue fcArrivalQueue = new Queue<Queue>();
    Queue ccArrivalQueue = new Queue<Queue>();

    Queue fcStationsLine = new Queue<Queue>();
    Queue ccStationsLine = new Queue<Queue>();

    Station fc1 = new Station(CONSTANTS.FIRSTCLASS + "1");
    Station fc2 = new Station(CONSTANTS.FIRSTCLASS + "2");

    Station cc1 = new Station(CONSTANTS.COACHCLASS + "1");
    Station cc2 = new Station(CONSTANTS.COACHCLASS + "2");
    Station cc3 = new Station(CONSTANTS.COACHCLASS + "3");

    PriorityQueue fcPassengers = new PriorityQueue<Passenger>(4);
    PriorityQueue ccPassengers = new PriorityQueue<Passenger>(4);


    public Dispatch(int simulationDuration, double avgCoachArrival, double avgCoachService, double avgFirstArrival, double avgFirstService) {
        //fcPassengers = passengerGenerator(10, CONSTANTS.FIRSTCLASS);
        //ccPassengers = passengerGenerator(10, CONSTANTS.COACHCLASS);

        fcPassengers.add(new Passenger(1, CONSTANTS.FIRSTCLASS, 5));
        fcPassengers.add(new Passenger(2, CONSTANTS.FIRSTCLASS, 5));
        fcPassengers.add(new Passenger(3, CONSTANTS.FIRSTCLASS, 6));
        fcPassengers.add(new Passenger(4, CONSTANTS.FIRSTCLASS, 7));
        fcPassengers.add(new Passenger(5, CONSTANTS.FIRSTCLASS, 7));
        fcPassengers.add(new Passenger(6, CONSTANTS.FIRSTCLASS, 7));
        fcPassengers.add(new Passenger(7, CONSTANTS.FIRSTCLASS, 10));
        fcPassengers.add(new Passenger(8, CONSTANTS.FIRSTCLASS, 12));
        ccPassengers.add(new Passenger(11, CONSTANTS.COACHCLASS, 5));
        ccPassengers.add(new Passenger(11, CONSTANTS.COACHCLASS, 5));
        ccPassengers.add(new Passenger(13, CONSTANTS.COACHCLASS, 5));
        ccPassengers.add(new Passenger(14, CONSTANTS.COACHCLASS, 15));
        ccPassengers.add(new Passenger(15, CONSTANTS.COACHCLASS, 6));
        ccPassengers.add(new Passenger(16, CONSTANTS.COACHCLASS, 7));
        ccPassengers.add(new Passenger(17, CONSTANTS.COACHCLASS, 17));
        ccPassengers.add(new Passenger(18, CONSTANTS.COACHCLASS, 7));

        this.avgCoachArrival = avgCoachArrival;
        this.avgCoachService = avgCoachService;
        this.avgFirstArrival = avgFirstArrival;
        this.avgFirstService = avgFirstService;
        this.simulationDuration = simulationDuration;
        startSimulation();
    }

    public void placeInQueue(Passenger passenger, int time) {
        //System.out.println("\t Putting " + passenger.getPassengerNumber() + " in queue: " + passenger.getArrivalTime());
        if(passenger.getSeatingClass() == CONSTANTS.FIRSTCLASS) {
            // Place in first class queue
            fcStationsLine.enqueue(passenger);
        } else {
            // Place in coach, or first class if all coach queues are filled and there are empty fc queues
            if(cc1.isBusy() && cc2.isBusy() && cc3.isBusy()) {
                System.out.println("all coach stations are busy over flow to first class if possible");
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
        //startBoarding();
        int timer = 0;
        Passenger fp;
        Passenger cp;
        Passenger fpPeek;
        Passenger cpPeek;
        Passenger fpMin;
        Passenger cpMin;
        Boolean passengerFound;
        while(timer <= simulationDuration) {
            System.out.println("Time: " + timer + " ");

            while(fcPassengers.peek() != null && ((Passenger) fcPassengers.peek()).getArrivalTime() == timer) {
                fpMin = (Passenger)fcPassengers.fetchMin();
                placeInQueue(fpMin, timer);
            }

            while(ccPassengers.peek() != null && ((Passenger) ccPassengers.peek()).getArrivalTime() == timer) {
                cpMin = (Passenger)ccPassengers.fetchMin();
                placeInQueue(cpMin, timer);
            }

            fc1.processPassengers(fcStationsLine, timer);
            fc2.processPassengers(fcStationsLine, timer);
            //check if cc1, cc2, cc3 are busy if so check if fc1, fc2 are busy
            //if all coach stations are busy process coach at fc
            //if all fc are busy place passenger at coach station 
            cc1.processPassengers(ccStationsLine, timer);
            cc2.processPassengers(ccStationsLine, timer);
            cc3.processPassengers(ccStationsLine, timer);


            timer++;

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

    private PriorityQueue passengerGenerator(int passengerCount, String passengerClass) {
        PriorityQueue pq = new PriorityQueue(10);
        for(int i=0; i < passengerCount; i++) {
            pq.add(new Passenger(i, passengerClass, (int)(Math.random()*closeQueue) ));
        }
        return pq;
    }

}
