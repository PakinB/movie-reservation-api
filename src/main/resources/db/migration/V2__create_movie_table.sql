CREATE SEQUENCE IF NOT EXISTS movies_id_seq START 1 INCREMENT 1;

CREATE TABLE movies (
    movie_id    INTEGER      PRIMARY KEY DEFAULT nextval('movies_id_seq'),
    movie_name  VARCHAR(255) NOT NULL,
    movie_year  INTEGER      NOT NULL,
    genre       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    image_url   VARCHAR(255) NOT NULL
);
