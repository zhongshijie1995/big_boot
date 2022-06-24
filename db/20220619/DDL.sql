--liquibase formatted sql

--changeset 钟世杰:1 labels:标准化项目1.0 context:所有分行
--comment: 标准化项目1.0建表脚本
create table zsj (
    zsj_id varchar(2),
    zsj_name varchar(30)
);
-- rollback drop table zsj;
