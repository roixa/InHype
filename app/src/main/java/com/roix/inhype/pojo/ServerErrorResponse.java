package com.roix.inhype.pojo;

/**
 * Created by roix on 25.06.2017.
 */

public class ServerErrorResponse extends Exception {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
