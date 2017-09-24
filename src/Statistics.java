import java.util.HashMap;

public class Statistics {

    int totalDuration;
    int fcMaxQueueLength = 0;
    int ccMaxQueueLength = 0;
    Queue completed = new Queue<Passenger>();
    HashMap<String, Queue> stationInfo = new HashMap<>();

    public Statistics() { }

    public void outputStats() {
        System.out.println("--------------------");
        System.out.println("Total Duration: " + getTotalDuration());
        System.out.println("Coach Max Queue length: " + getCcMaxQueueLength());
        System.out.println("First Max Queue length: " + getFcMaxQueueLength());
        completed.getTotal();
    }

    public void enqueue(Passenger passenger, String station) {
        completed.enqueue(passenger);
        stationInfo.put(station, completed);
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
