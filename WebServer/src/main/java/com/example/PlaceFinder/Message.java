package com.example.PlaceFinder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private long id;
    private String username;
    private LocalDateTime dateTime;
    private String message;
    private final String format = "dd/MM/yyyy HH:mm:ss";

    public Message() {}

    public Message(long id, String username, LocalDateTime dateTime, String message) {
        this.id = id;
        this.username = username;
        this.dateTime = dateTime;
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTimeFromString(String dateTime) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(format);
        this.dateTime = LocalDateTime.parse(dateTime, dateTimeFormat);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFormattedDateTime() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(format);
        return dateTime.format(dateTimeFormat);
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        String result = "Id: " + id + "\n";
        result += "Username: " + username + "\n";
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(format);
        result += "Date and time: " + dateTime.format(dateTimeFormat) + "\n";
        result += "Message: " + message;
        return result;
    }
}
