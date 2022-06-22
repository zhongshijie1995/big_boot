--liquibase formatted sql

-- changeset 标准化投产:1
create table zsj (
    zsj_id varchar(2),
    zsj_name varchar(30)
);
-- rollback drop table zsj

