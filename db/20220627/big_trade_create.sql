create table operate_tips
(
    create_time timestamp default CURRENT_TIMESTAMP COMMENT '创建时间',
    tips        text comment  '操作提示'
);
