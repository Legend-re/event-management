package org.legendre.eventmanagement.ticket;

import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.guest.Guest;

public class BookTicket {

    private String ticketId;
    private TicketStatus ticketStatus;
    //    private String guestFullName;
    private Guest guest;
    private Event event;

    public BookTicket(Event event, Guest guest, /*String guestFullName,*/ TicketStatus ticketStatus, String ticketId) {
        this.event = event;
        this.guest = guest;
//        this.guestFullName = guestFullName;
        this.ticketStatus = ticketStatus;
        this.ticketId = ticketId;
    }

    public BookTicket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", ticketStatus=" + ticketStatus +
                //               ", guestFullName='" + guestFullName + '\'' +
                ", guest=" + guest +
                ", host=" + event +
                '}';
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    /*
        public String getGuestFullName() {
            return guestFullName;
        }

        public void setGuestFullName(String guestFullName) {
            this.guestFullName = guestFullName;
        }

    */
    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
