--liquibase formatted sql
--changeset adina.amzarescu:create_city_table splitStatement:true andDelimiter:;

CREATE TABLE IF NOT EXISTS tema2.Orase (
    id SERIAL PRIMARY KEY,
    id_tara INT REFERENCES tema2.tari(id),
    nume_oras VARCHAR(255),
    latitudine DECIMAL(9, 6),
    longitudine DECIMAL(9, 6),
    CONSTRAINT orase_unic UNIQUE (id_tara, nume_oras)
);
