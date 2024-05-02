--liquibase formatted sql
--changeset adina.amzarescu:create_country_table splitStatement:true andDelimiter:;

CREATE TABLE IF NOT EXISTS tema2.Tari (
    id SERIAL PRIMARY KEY,
    nume_tara VARCHAR(255) UNIQUE NOT NULL ,
    latitudine DECIMAL(9, 6) NOT NULL ,
    longitudine DECIMAL(9, 6) NOT NULL
);