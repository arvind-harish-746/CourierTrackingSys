package exceptions;

import java.util.*;

// checked exception for illegal parcel status lifecycle transitions
public class InvalidStatusTransitionException extends Exception {
    public InvalidStatusTransitionException(String m) {
        super(m);
    }
}
