package me.ride.exceptions;

import java.sql.SQLException;

public class CarNotFoundException extends SQLException {
    public CarNotFoundException(String reason) {
        super(reason);
    }

    public CarNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
