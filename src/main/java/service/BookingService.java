package service;

import java.util.*;
import model.Parcel;
import util.IdGenerator;
import exceptions.InvalidParcelException;

// service handling booking and cancellation of parcels
public class BookingService {
    private ParcelService parcelService;
    private TrackingService trackingService;

    public BookingService(ParcelService ps) {
        this.parcelService = ps;
    }

    public BookingService(ParcelService ps, TrackingService ts) {
        this.parcelService = ps;
        this.trackingService = ts;
    }

    public void setTrackingService(TrackingService ts) {
        this.trackingService = ts;
    }

    // books a new parcel and stores it in parcel service
    public Parcel bookParcel(String s, String r, double w) {
        String pid = IdGenerator.generateParcelId();
        Parcel p = new Parcel(pid, s, r, w);
        parcelService.addParcel(p);
        if (trackingService != null) {
            trackingService.recordInitialBooking(pid);
        }
        return p;
    }

    // cancels a booked parcel if not yet picked up
    public boolean cancelParcel(String pid) {
        Parcel p = parcelService.getParcel(pid);
        if (p == null) {
            System.out.println("cancellation failed: parcel not found with id " + pid);
            return false;
        }
        if (!p.getStatus().equalsIgnoreCase("Booked")) {
            System.out.println("cancellation failed: cannot cancel parcel with status " + p.getStatus());
            return false;
        }
        p.setStatus("Cancelled");
        parcelService.removeParcel(pid);
        System.out.println("parcel " + pid + " cancelled successfully");
        return true;
    }
}
