public class Statistics {

    int totalDuration;
    int fcMaxQueueLength;
    int ccMaxQueueLength;

    public Statistics() { }

    public void outputStats() {
        System.out.println("--------------------");
        System.out.println("Total Duration: " + getTotalDuration());
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
        this.fcMaxQueueLength = fcMaxQueueLength;
    }

    public int getCcMaxQueueLength() {
        return ccMaxQueueLength;
    }

    public void setCcMaxQueueLength(int ccMaxQueueLength) {
        this.ccMaxQueueLength = ccMaxQueueLength;
    }
}
