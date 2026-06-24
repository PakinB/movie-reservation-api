CREATE SEQUENCE IF NOT EXISTS seat_id_seq START 1 INCREMENT 1;

CREATE TABLE seats (
    seat_id     INTEGER     PRIMARY KEY DEFAULT nextval('seat_id_seq'),
    seat_number INTEGER     NOT NULL,
    seat_row    VARCHAR(30) NOT NULL,
    seat_status VARCHAR(50) NOT NULL,
    schedule_id INTEGER     NOT NULL,

    CONSTRAINT fk_seat_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id)
);
