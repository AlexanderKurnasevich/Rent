package me.ride.exception;

import java.sql.SQLException;

public class OrderNotFoundException extends SQLException {

    public OrderNotFoundException(String reason) {
        super(reason);
    }

    public OrderNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
