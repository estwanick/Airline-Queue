# Airline-Queue

# CS610 Programming Assignment 1

### Configure Simulation Parameters:

In Main.java you can configure the following properties of the simulation

Dispatch passengerDispatch = new Dispatch( <br />
    int simulationDuration, <br />
    int avgCoachArrival, <br />
    int avgCoachService, <br />
    int avgFirstArrival, <br />
    int avgFirstService, <br />
    int absLateRange, <br />
    int avgServiceBuffer, <br />
    int fcPassengerCount, <br />
    int ccPassengerCount <br />
) <br />

##### simulationDuration
The time at which the service queues will stop accepting passengers

##### avgCoachArrival
The average interval at which coach class passengers will arrive, subject to a random +/- absLateRange

##### avgCoachService
The average time it takes to service a coach passenger, subject to a random +/- avgServiceBuffer

##### avgFirstArrival
The average interval at which first class passengers will arrive, subject to a random +/- absLateRange

##### avgFirstService
The average time it takes to service a first class passenger, subject to a random +/- avgServiceBuffer

##### absLateRange
+/- a random delay within the absolute value of this value to the arrival time of passengers

##### avgServiceBuffer
+/- a random delay within the absolute value of this value to the service time a passengers

##### fcPassengerCount
Total number of first class passengers

##### ccPassengerCount
Total number of coach passengers
