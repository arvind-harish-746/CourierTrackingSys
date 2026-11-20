package model;

import java.util.*;

// customer subclass representing parcel booking clients
public class Customer extends User {
    private List<String> bookedParcelIds;

    public Customer(String id, String name, String contact) {
        super(id, name, contact);
        this.bookedParcelIds = new ArrayList<>();
    }

    public List<String> getBookedParcelIds() {
        return bookedParcelIds;
    }

    public void addBookedParcelId(String pid) {
        bookedParcelIds.add(pid);
    }

    @Override
    public String displayInfo() {
        return "customer id: " + id + " | name: " + name + " | contact: " + contact + " | total bookings: " + bookedParcelIds.size();
    }
}
