package exceptions;

import java.util.*;

// checked exception when no delivery agent is available
public class NoAgentAvailableException extends Exception {
    public NoAgentAvailableException(String m) {
        super(m);
    }
}
