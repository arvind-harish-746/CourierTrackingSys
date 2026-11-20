package service;

import java.util.*;
import model.Parcel;

// service managing parcel storage and lookup operations
public class ParcelService {
    private List<Parcel> parcelList;

    public ParcelService() {
        this.parcelList = new ArrayList<>();
    }

    // stores a new parcel
    public void addParcel(Parcel p) {
        parcelList.add(p);
    }

    // finds parcel by id using standard loop
    public Parcel getParcel(String id) {
        for (int i = 0; i < parcelList.size(); i++) {
            Parcel p = parcelList.get(i);
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // removes parcel by id using standard loop
    public Parcel removeParcel(String id) {
        for (int i = 0; i < parcelList.size(); i++) {
            Parcel p = parcelList.get(i);
            if (p.getId().equalsIgnoreCase(id)) {
                parcelList.remove(i);
                return p;
            }
        }
        return null;
    }

    // returns list of all parcels
    public List<Parcel> getAllParcels() {
        return parcelList;
    }
}
