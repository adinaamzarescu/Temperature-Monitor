--liquibase formatted sql
--changeset adina.amzarescu:create_temperature_table splitStatement:true andDelimiter:;

CREATE TABLE IF NOT EXISTS tema2.Temperaturi (
    id SERIAL PRIMARY KEY,
    valoare DECIMAL(5, 2) NOT NULL ,
    timestamp TIMESTAMP NOT NULL ,
    id_oras INT REFERENCES tema2.orase(id) NOT NULL ,
    CONSTRAINT temperaturi_unic UNIQUE (id_oras, timestamp)
);