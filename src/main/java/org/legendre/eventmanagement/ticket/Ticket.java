package org.legendre.eventmanagement.ticket;

public class Ticket {

    private int totalTickets;
    private int totalTicketsSold;
    private int ticketsLeft;
    private String eventName;

    public Ticket(int totalTickets, int totalTicketsSold, int ticketsLeft, String eventName) {
        this.totalTickets = totalTickets;
        this.totalTicketsSold = totalTicketsSold;
        this.ticketsLeft = ticketsLeft;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "totalTickets=" + totalTickets +
                ", totalTicketsSold=" + totalTicketsSold +
                ", ticketsLeft=" + ticketsLeft +
                ", host=" + eventName +
                '}';
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(int totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(int ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
