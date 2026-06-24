CREATE SEQUENCE IF NOT EXISTS ticket_id_seq START 1 INCREMENT 1;

CREATE TABLE tickets (
    ticket_id     INTEGER        PRIMARY KEY DEFAULT nextval('ticket_id_seq'),
    user_id       INTEGER        NOT NULL,
    seat_id       INTEGER        NOT NULL,
    schedule_id   INTEGER        NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    ticket_status VARCHAR(50)    NOT NULL,

    CONSTRAINT fk_ticket_user     FOREIGN KEY (user_id)     REFERENCES users(id),
    CONSTRAINT fk_ticket_seat     FOREIGN KEY (seat_id)     REFERENCES seats(seat_id),
    CONSTRAINT fk_ticket_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id)
);
