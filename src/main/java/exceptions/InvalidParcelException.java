package exceptions;

import java.util.*;

// checked exception for parcel lookup and validation failures
public class InvalidParcelException extends Exception {
    public InvalidParcelException(String m) {
        super(m);
    }
}
