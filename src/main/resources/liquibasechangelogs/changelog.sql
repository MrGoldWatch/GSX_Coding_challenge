--liquibase formatted sql

--changeset Hosein:logixboard
CREATE TABLE IF NOT EXISTS todolist (
    name text,
    date_created bigint,
    updateTs bigint,
    owner character varying,
    todolist_id uuid,
    PRIMARY KEY (todolist_id)
    );

CREATE TABLE IF NOT EXISTS todolistitem (
    id uuid,
    label text,
    date_added bigint,
    updateTs bigint,
    completed boolean,
    todolist_id uuid,
    PRIMARY KEY (id),
    FOREIGN KEY (todolist_id) REFERENCES todolist(todolist_id)
    );