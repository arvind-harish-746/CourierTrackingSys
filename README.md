# CourierTrackingSystem

a terminal-based courier and parcel tracking system built in java using scanner without any gui or database.

## core features
1. parcel booking and cancellation
2. delivery agent assignment (using a custom fifo queue built from scratch)
3. status updates enforcing lifecycle: Booked -> Picked Up -> In Transit -> Out for Delivery -> Delivered
4. tracking history
5. view all booked parcels and registered delivery agents

## project structure
```
CourierTrackingSystem/
├── README.md
├── .gitignore
└── src/main/java/
    ├── model/
    │   ├── User.java
    │   ├── Customer.java
    │   ├── Agent.java
    │   ├── Parcel.java
    │   └── StatusUpdate.java
    ├── util/
    │   └── IdGenerator.java
    ├── exceptions/
    │   ├── InvalidParcelException.java
    │   ├── NoAgentAvailableException.java
    │   └── InvalidStatusTransitionException.java
    ├── datastructures/
    │   └── AgentQueue.java
    ├── service/
    │   ├── ParcelService.java
    │   ├── BookingService.java
    │   ├── AgentService.java
    │   └── TrackingService.java
    └── app/
        └── Main.java
```

## build and run
compile all files:
```bash
javac -d bin $(find src/main/java -name "*.java")
```

run the application:
```bash
java -cp bin app.Main
```
