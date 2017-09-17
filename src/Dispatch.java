import java.util.Iterator;

public class Dispatch {

    Passenger [] firstClass;
    Passenger [] coachClass;
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
    Queue fcPassengers = new Queue<Passenger>();
    Queue ccPassengers = new Queue<Passenger>();


    public Dispatch(double avgCoachArrival, double avgCoachService, double avgFirstArrival, double avgFirstService) {
        passengerGenerator(fcPassengers, 10, CONSTANTS.FIRSTCLASS);
        passengerGenerator(ccPassengers, 10, CONSTANTS.COACHCLASS);
        this.avgCoachArrival = avgCoachArrival;
        this.avgCoachService = avgCoachService;
        this.avgFirstArrival = avgFirstArrival;
        this.avgFirstService = avgFirstService;

        initializeServiceQueues();
    }

    public void startBoarding() {

    }

    private void addToQueue(Passenger passenger) {

    }

    private void getServiceTime(String flightClass) {

    }

    private void setArrivalTime(String flightClass) {

    }

    public void printPassengers() {
        Iterator<Passenger> fcIterator = fcPassengers.iterator();
        Iterator<Passenger> ccIterator = ccPassengers.iterator();
        System.out.println("Coach Passengers");
        while(ccIterator.hasNext()) {
            System.out.print(ccIterator.next().getPassengerNumber() + ", ");
        }
        System.out.println("");
        System.out.println("First Class Passengers");
        while(fcIterator.hasNext()) {
            System.out.print(fcIterator.next().getPassengerNumber()  + ", ");
        }
    }

    private void initializeServiceQueues() {
        fcStations.enqueue(firstClass_station1);
        fcStations.enqueue(firstClass_station2);
        ccStations.enqueue(coachClass_station1);
        ccStations.enqueue(coachClass_station2);
        ccStations.enqueue(coachClass_station3);
    }

    private Queue passengerGenerator(Queue queue, int passengerCount, String passengerClass) {
        for(int i=0; i < passengerCount; i++) {
            queue.enqueue(new Passenger(i, passengerClass, Math.random()*10 ));
        }
        return queue;
    }

}
