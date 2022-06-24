--liquibase formatted sql

--changeset 钟世杰:1 labels:标准化项目1.0 context:所有分行
--comment: 标准化项目1.0数据铺底
insert into zsj values ('1', 'zsj');
insert into zsj values ('2', 'jsz');
-- rollback delete from zsj where zsj_id in ('1', '2');