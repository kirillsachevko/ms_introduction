CREATE TABLE IF NOT EXISTS mp3metadata
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL ,
    artist   VARCHAR(255) NOT NULL ,
    album    VARCHAR(255) NOT NULL ,
    duration VARCHAR(50) NOT NULL ,
    year     VARCHAR(4) NOT NULL
);