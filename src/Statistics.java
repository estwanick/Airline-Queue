import java.util.HashMap;

public class Statistics {

    int totalDuration;
    int fcMaxQueueLength = 0;
    int ccMaxQueueLength = 0;
    Queue fc1Completed = new Queue<Passenger>();
    Queue fc2Completed = new Queue<Passenger>();
    Queue cc1Completed = new Queue<Passenger>();
    Queue cc2Completed = new Queue<Passenger>();
    Queue cc3Completed = new Queue<Passenger>();

    public Statistics() { }

    public void outputStats() {
        System.out.println("--------------------");
        System.out.println("Total Duration: " + getTotalDuration());
        System.out.println("Coach Max Queue length: " + getCcMaxQueueLength());
        System.out.println("First Max Queue length: " + getFcMaxQueueLength());

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
