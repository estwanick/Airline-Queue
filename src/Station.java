public class Station {

    private boolean busy;
    private String type;

    public Station(String type) {
        this.busy = false;
        this.type = type;
    }

    public boolean isBusy() {
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

    public boolean processing(Passenger passenger, int time) {
        return true;
    }
}
