package model;

import java.util.*;

// model representing status update and timestamp
public class StatusUpdate {
    private String status;
    private String timestamp;

    public StatusUpdate(String s, String t) {
        this.status = s;
        this.timestamp = t;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
