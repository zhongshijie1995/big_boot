--liquibase formatted sql

-- changeset 标准化投产:1
insert into zsj values ('1', 'zsj');
insert into zsj values ('2', 'jsz');
-- rollback delete from zsj where zsj_id in ('1', '2')