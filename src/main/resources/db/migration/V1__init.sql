CREATE TABLE IF NOT EXISTS book_ticket
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    ticket_id     VARCHAR(255) NULL,
    ticket_status SMALLINT NULL,
    guest_id      BIGINT NULL,
    event_id      BIGINT NULL,
    CONSTRAINT pk_bookticket PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255) NULL,
    location VARCHAR(255) NULL,
    date     datetime NULL,
    host     VARCHAR(255) NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS guest
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    email_address VARCHAR(255) NULL,
    CONSTRAINT pk_guest PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS guest_booked_tickets
(
    guest_id          BIGINT NOT NULL,
    booked_tickets_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS host
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    bio   VARCHAR(255) NULL,
    CONSTRAINT pk_host PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ticket
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    total_tickets      INT NOT NULL,
    total_tickets_sold INT NOT NULL,
    tickets_left       INT NOT NULL,
    event_name         VARCHAR(255) NULL,
    CONSTRAINT pk_ticket PRIMARY KEY (id)
);

ALTER TABLE guest_booked_tickets
    ADD CONSTRAINT uc_guest_booked_tickets_bookedtickets UNIQUE (booked_tickets_id);

ALTER TABLE book_ticket
    ADD CONSTRAINT FK_BOOKTICKET_ON_EVENT FOREIGN KEY (event_id) REFERENCES event (id);

ALTER TABLE book_ticket
    ADD CONSTRAINT FK_BOOKTICKET_ON_GUEST FOREIGN KEY (guest_id) REFERENCES guest (id);

ALTER TABLE guest_booked_tickets
    ADD CONSTRAINT fk_guebootic_on_book_ticket FOREIGN KEY (booked_tickets_id) REFERENCES book_ticket (id);

ALTER TABLE guest_booked_tickets
    ADD CONSTRAINT fk_guebootic_on_guest FOREIGN KEY (guest_id) REFERENCES guest (id);