package service;

import java.util.*;
import model.Parcel;
import model.StatusUpdate;
import exceptions.InvalidParcelException;
import exceptions.InvalidStatusTransitionException;

// service managing parcel lifecycle transitions and tracking history
public class TrackingService {
    private ParcelService parcelService;
    private Map<String, List<StatusUpdate>> historyMap;
    private String[] lifecycle = {"Booked", "Picked Up", "In Transit", "Out for Delivery", "Delivered"};

    public TrackingService(ParcelService ps) {
        this.parcelService = ps;
        this.historyMap = new HashMap<>();
    }

    // records the initial booking event at the time of creation
    public void recordInitialBooking(String pid) {
        List<StatusUpdate> list = new ArrayList<>();
        list.add(new StatusUpdate("Booked", new Date().toString()));
        historyMap.put(pid, list);
    }

    // validates and updates status enforcing strict sequential order
    public boolean updateStatus(String pid, String nextStatus) {
        Parcel p = parcelService.getParcel(pid);
        if (p == null) {
            System.out.println("status update failed: parcel not found with id " + pid);
            return false;
        }

        if (p.getStatus().equalsIgnoreCase("Delivered")) {
            System.out.println("invalid status transition: parcel has already been delivered");
            return false;
        }
        if (p.getStatus().equalsIgnoreCase("Cancelled")) {
            System.out.println("invalid status transition: parcel is cancelled");
            return false;
        }

        int currIdx = -1;
        for (int i = 0; i < lifecycle.length; i++) {
            if (lifecycle[i].equalsIgnoreCase(p.getStatus())) {
                currIdx = i;
                break;
            }
        }

        int nextIdx = -1;
        for (int i = 0; i < lifecycle.length; i++) {
            if (lifecycle[i].equalsIgnoreCase(nextStatus)) {
                nextIdx = i;
                break;
            }
        }

        if (currIdx == -1) {
            System.out.println("invalid status transition: current status " + p.getStatus() + " cannot be transitioned");
            return false;
        }

        if (nextIdx == -1) {
            System.out.println("invalid status target: '" + nextStatus + "'. Valid options are: Picked Up, In Transit, Out for Delivery, Delivered");
            return false;
        }

        if (nextIdx != currIdx + 1) {
            System.out.println("invalid status transition: sequence order is wrong. Cannot transition from " + p.getStatus() + " to " + nextStatus);
            if (currIdx + 1 < lifecycle.length) {
                System.out.println("next expected status in sequence is: " + lifecycle[currIdx + 1]);
            }
            return false;
        }

        List<StatusUpdate> list = historyMap.get(pid);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new StatusUpdate(p.getStatus(), new Date().toString()));
            historyMap.put(pid, list);
        }

        p.setStatus(lifecycle[nextIdx]);
        list.add(new StatusUpdate(lifecycle[nextIdx], new Date().toString()));
        System.out.println("status updated successfully to: " + p.getStatus());
        return true;
    }

    // retrieves tracking history list for a parcel
    public List<StatusUpdate> getHistory(String pid) {
        List<StatusUpdate> list = historyMap.get(pid);
        if (list == null) {
            Parcel p = parcelService.getParcel(pid);
            if (p != null) {
                list = new ArrayList<>();
                list.add(new StatusUpdate(p.getStatus(), new Date().toString()));
                historyMap.put(pid, list);
            } else {
                return new ArrayList<>();
            }
        }
        return list;
    }

    // returns lifecycle array
    public String[] getLifecycle() {
        return lifecycle;
    }
}
