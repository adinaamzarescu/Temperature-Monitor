--liquibase formatted sql
--changeset adina.amzarescu:create_city_table splitStatement:true andDelimiter:;

CREATE TABLE IF NOT EXISTS tema2.Orase (
    id SERIAL PRIMARY KEY,
    id_tara INT REFERENCES tema2.tari(id) NOT NULL,
    nume_oras VARCHAR(255) NOT NULL ,
    latitudine DECIMAL(9, 6) NOT NULL ,
    longitudine DECIMAL(9, 6) NOT NULL ,
    CONSTRAINT orase_unic UNIQUE (id_tara, nume_oras)
);
