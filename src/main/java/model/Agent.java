package model;

import java.util.*;

// delivery agent subclass handling parcel assignments
public class Agent extends User {
    private boolean available;
    private int currentLoad;

    public Agent(String id, String name, String contact) {
        super(id, name, contact);
        this.available = true;
        this.currentLoad = 0;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean b) {
        this.available = b;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int l) {
        this.currentLoad = l;
    }

    @Override
    public String displayInfo() {
        return "agent id: " + id + " | name: " + name + " | contact: " + contact + " | available: " + available + " | load: " + currentLoad;
    }
}
