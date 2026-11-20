package model;

import java.util.*;

// parcel entity tracking weight delivery status and assigned agent
public class Parcel {
    private String id;
    private String sender;
    private String receiver;
    private double weight;
    private String status;
    private String assignedAgentId;

    public Parcel(String id, String sender, String receiver, double weight) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        if (weight >= 0) {
            this.weight = weight;
        } else {
            this.weight = 0.0;
        }
        this.status = "Booked";
        this.assignedAgentId = null;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String s) {
        this.sender = s;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String r) {
        this.receiver = r;
    }

    public double getWeight() {
        return weight;
    }

    // rejects negative weight values
    public void setWeight(double w) {
        if (w < 0) {
            return;
        }
        this.weight = w;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public String getAssignedAgentId() {
        return assignedAgentId;
    }

    public void setAssignedAgentId(String aid) {
        this.assignedAgentId = aid;
    }
}
