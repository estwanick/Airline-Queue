import java.util.HashMap;
import java.util.Iterator;

public class Statistics {

    int totalDuration;
    int fcMaxQueueLength = 0;
    int ccMaxQueueLength = 0;
    Queue fc1Completed = new Queue<Passenger>();
    Queue fc2Completed = new Queue<Passenger>();
    Queue cc1Completed = new Queue<Passenger>();
    Queue cc2Completed = new Queue<Passenger>();
    Queue cc3Completed = new Queue<Passenger>();
    Queue globalCompleted = new Queue<Passenger>();
    HashMap<String, Queue> qMap = new HashMap<>();


    public Statistics() {
        qMap.put("FIRSTCLASS-1", fc1Completed);
        qMap.put("FIRSTCLASS-2", fc2Completed);
        qMap.put("COACHCLASS-1", cc1Completed);
        qMap.put("COACHCLASS-2", cc2Completed);
        qMap.put("COACHCLASS-3", cc3Completed);
        qMap.put("ALL", globalCompleted);
    }

    public void outputStats() {
        System.out.println("--------------------");
        System.out.println("Total Duration: " + getTotalDuration());
        System.out.println("Coach Max Queue length: " + getCcMaxQueueLength());
        System.out.println("First Max Queue length: " + getFcMaxQueueLength());
        getQueueStats("FIRSTCLASS-1");
    }

    private void getQueueStats(String station) {
        System.out.println("Stats for: " + station);
        Queue completed = qMap.get(station);
        Iterator<Passenger> passengerIterator = completed.iterator();
        Passenger passenger;
        int passengerCount = completed.getTotal();
        int avgWaitingTime = 0;
        int maxWaitingTime = 0;
        int occupancyRate = 0;
        int waitingTime = 0;
        int sumWaitingTime = 0;

        while(passengerIterator.hasNext()) {
            passenger = passengerIterator.next();
            waitingTime = passenger.getStartProcessingTime() - passenger.getArrivalTime();
            sumWaitingTime += waitingTime;

            if(waitingTime > maxWaitingTime) {
                maxWaitingTime = waitingTime;
            }

            logPassenger(passenger);
        }

        avgWaitingTime = sumWaitingTime/passengerCount;
        System.out.println("Max waiting time: " + maxWaitingTime);
        System.out.println("Average waiting time: " +  avgWaitingTime);
    }

    private void logPassenger(Passenger passenger) {
        System.out.println("Passenger: " + passenger.getPassengerNumber());
        System.out.println("\t Arrival time: " + passenger.getArrivalTime());
        System.out.println("\t Start Processing: " + passenger.getStartProcessingTime());
        System.out.println("\t Processing Time: " + passenger.getProcessingDuration());
        System.out.println("\t Seating Class: " + passenger.getSeatingClass());

    }

    public void enqueue(Passenger passenger, String station) {
        switch(station) {
            case "FIRSTCLASS-1":
                fc1Completed.enqueue(passenger);
                break;
            case "FIRSTCLASS-2":
                fc2Completed.enqueue(passenger);
                break;
            case "COACHCLASS-1":
                cc1Completed.enqueue(passenger);
                break;
            case "COACHCLASS-2":
                cc2Completed.enqueue(passenger);
                break;
            case "COACHCLASS-3":
                cc3Completed.enqueue(passenger);
                break;
            default:
                break;
        }
        globalCompleted.enqueue(passenger);
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getFcMaxQueueLength() {
        return fcMaxQueueLength;
    }

    public void setFcMaxQueueLength(int fcMaxQueueLength) {
        if(this.fcMaxQueueLength < fcMaxQueueLength) {
            this.fcMaxQueueLength = fcMaxQueueLength;
        }
    }

    public int getCcMaxQueueLength() {
        return ccMaxQueueLength;
    }

    public void setCcMaxQueueLength(int ccMaxQueueLength) {
        if(this.ccMaxQueueLength < ccMaxQueueLength) {
            this.ccMaxQueueLength = ccMaxQueueLength;
        }
    }
}
