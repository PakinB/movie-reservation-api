CREATE SEQUENCE IF NOT EXISTS schedule_id_seq START 1 INCREMENT 1;

CREATE TABLE schedules (
    schedule_id     INTEGER PRIMARY KEY DEFAULT nextval('schedule_id_seq'),
    start_date      DATE    NOT NULL,
    end_date        DATE    NOT NULL,
    show_time       TIME    NOT NULL,
    available_seats INTEGER NOT NULL,
    movie_id        INTEGER NOT NULL,

    CONSTRAINT fk_schedule_movie FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);
