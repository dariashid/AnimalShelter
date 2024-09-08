-- liquibase formatted sql

create table Client (
id Serial,
chat_Id Integer primary key,
name Text,
has_pet Boolean,
phone Text);