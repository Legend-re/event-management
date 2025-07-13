package org.legendre.eventmanagement.event;

import java.time.LocalDateTime;

public class Event {

    private String name;
    private String location;
    private LocalDateTime date;
    private String host;

    public Event() {
    }

    public Event(String name, String location, LocalDateTime date, String host) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", host='" + host + '\'' +
                '}';
    }
}
