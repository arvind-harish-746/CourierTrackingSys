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

    // validates and updates status enforcing strict sequential order
    public void updateStatus(String pid, String nextStatus) throws InvalidStatusTransitionException, InvalidParcelException {
        Parcel p = parcelService.getParcel(pid);
        if (p == null) {
            throw new InvalidParcelException("parcel not found with id " + pid);
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
            throw new InvalidStatusTransitionException("current status " + p.getStatus() + " cannot be transitioned");
        }
        if (nextIdx == -1) {
            throw new InvalidStatusTransitionException("invalid status target " + nextStatus);
        }
        if (nextIdx != currIdx + 1) {
            throw new InvalidStatusTransitionException("cannot transition from " + p.getStatus() + " to " + nextStatus);
        }

        List<StatusUpdate> list = historyMap.get(pid);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new StatusUpdate(p.getStatus(), new Date().toString()));
            historyMap.put(pid, list);
        }

        p.setStatus(lifecycle[nextIdx]);
        list.add(new StatusUpdate(lifecycle[nextIdx], new Date().toString()));
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
