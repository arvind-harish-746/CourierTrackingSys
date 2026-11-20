package util;

import java.util.*;

// static utility class for generating sequential ids
public class IdGenerator {
    private static int parcelCounter = 1;
    private static int agentCounter = 1;

    // generates formatted parcel id like par001
    public static String generateParcelId() {
        String id = "PAR";
        if (parcelCounter < 10) {
            id = id + "00" + parcelCounter;
        } else if (parcelCounter < 100) {
            id = id + "0" + parcelCounter;
        } else {
            id = id + parcelCounter;
        }
        parcelCounter++;
        return id;
    }

    // generates formatted agent id like agt01
    public static String generateAgentId() {
        String id = "AGT";
        if (agentCounter < 10) {
            id = id + "0" + agentCounter;
        } else {
            id = id + agentCounter;
        }
        agentCounter++;
        return id;
    }
}
