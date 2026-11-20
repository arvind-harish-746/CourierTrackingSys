package service;

import java.util.*;
import model.Parcel;
import util.IdGenerator;
import exceptions.InvalidParcelException;

// service handling booking and cancellation of parcels
public class BookingService {
    private ParcelService parcelService;

    public BookingService(ParcelService ps) {
        this.parcelService = ps;
    }

    // books a new parcel and stores it in parcel service
    public Parcel bookParcel(String s, String r, double w) {
        String pid = IdGenerator.generateParcelId();
        Parcel p = new Parcel(pid, s, r, w);
        parcelService.addParcel(p);
        return p;
    }

    // cancels a booked parcel if not yet picked up
    public void cancelParcel(String pid) throws InvalidParcelException {
        Parcel p = parcelService.getParcel(pid);
        if (p == null) {
            throw new InvalidParcelException("parcel not found with id " + pid);
        }
        if (!p.getStatus().equalsIgnoreCase("Booked")) {
            throw new InvalidParcelException("cannot cancel parcel with status " + p.getStatus());
        }
        p.setStatus("Cancelled");
        parcelService.removeParcel(pid);
    }
}
