package model;

import java.util.*;

// abstract base class for all system users
public abstract class User {
    protected String id;
    protected String name;
    protected String contact;

    public User(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    // returns formatted user details
    public abstract String displayInfo();
}
