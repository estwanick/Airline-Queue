import java.util.Iterator;

public class Dispatch {

    double avgCoachArrival;
    double avgCoachService;
    double avgFirstArrival;
    double avgFirstService;
    Queue fcStations = new Queue<Queue>();
    Queue ccStations = new Queue<Queue>();
    Queue firstClass_station1 = new Queue<Integer>();
    Queue firstClass_station2 = new Queue<Integer>();
    Queue coachClass_station1 = new Queue<Integer>();
    Queue coachClass_station2 = new Queue<Integer>();
    Queue coachClass_station3 = new Queue<Integer>();
    PriorityQueue fcPassengers = new PriorityQueue<Passenger>(10);
    PriorityQueue ccPassengers = new PriorityQueue<Passenger>(30);


    public Dispatch(double avgCoachArrival, double avgCoachService, double avgFirstArrival, double avgFirstService) {
        fcPassengers = passengerGenerator(10, CONSTANTS.FIRSTCLASS);
        ccPassengers = passengerGenerator(10, CONSTANTS.COACHCLASS);
        this.avgCoachArrival = avgCoachArrival;
        this.avgCoachService = avgCoachService;
        this.avgFirstArrival = avgFirstArrival;
        this.avgFirstService = avgFirstService;
        initializeServiceQueues();
        startBoarding();
    }

    public void startBoarding() {
        inOrderDispersal();
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

    private void addToQueue(Passenger passenger) {

    }

    private void getServiceTime(String flightClass) {

    }

    private void setArrivalTime(String flightClass) {

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

//    public void printPassengers() {
//        Iterator<Passenger> fcIterator = fcPassengers.iterator();
//        Iterator<Passenger> ccIterator = ccPassengers.iterator();
//        System.out.println("Coach Passengers");
//        while(ccIterator.hasNext()) {
//            System.out.print(ccIterator.next().getPassengerNumber() + ", ");
//        }
//        System.out.println("");
//        System.out.println("First Class Passengers");
//        while(fcIterator.hasNext()) {
//            System.out.print(fcIterator.next().getPassengerNumber()  + ", ");
//        }
//    }

    private void initializeServiceQueues() {
        fcStations.enqueue(firstClass_station1);
        fcStations.enqueue(firstClass_station2);
        ccStations.enqueue(coachClass_station1);
        ccStations.enqueue(coachClass_station2);
        ccStations.enqueue(coachClass_station3);
    }

    private PriorityQueue passengerGenerator(int passengerCount, String passengerClass) {
        PriorityQueue pq = new PriorityQueue(10);
        for(int i=0; i < passengerCount; i++) {
            pq.add(new Passenger(i, passengerClass, (int)(Math.random()*100) ));
        }
        return pq;
    }

}
