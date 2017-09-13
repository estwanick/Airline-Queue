public class Main {
    public static void main(String[] args) {

        Queue fcPassengers = new Queue<Passenger>();
        Queue ccPassengers = new Queue<Passenger>();
        Queue fcStations = new Queue<Queue>();
        Queue ccStations = new Queue<Queue>();
        Queue firstClass_station1 = new Queue<Integer>();
        Queue firstClass_station2 = new Queue<Integer>();
        Queue coachClass_station1 = new Queue<Integer>();
        Queue coachClass_station2 = new Queue<Integer>();
        Queue coachClass_station3 = new Queue<Integer>();
        fcStations.enqueue(firstClass_station1);
        fcStations.enqueue(firstClass_station2);
        ccStations.enqueue(coachClass_station1);
        ccStations.enqueue(coachClass_station2);
        ccStations.enqueue(coachClass_station3);

        fcPassengers.enqueue(new Passenger(1, CONSTANTS.FIRSTCLASS));
        fcPassengers.enqueue(new Passenger(2, CONSTANTS.FIRSTCLASS));
        fcPassengers.enqueue(new Passenger(3, CONSTANTS.FIRSTCLASS));

    }
}