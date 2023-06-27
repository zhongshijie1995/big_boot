package com.zhongshijie1995.big_boot.base.util.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongshijie1995.big_boot.base.util.gen.Uuid;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EntityWithUuidService {

    @Resource
    private Uuid uuid;

    public <T extends EntityWithUuid> T save(BaseMapper<T> mapper, T entity) {
        if (entity.getUuid() == null) {
            entity.setUuid(uuid.gen());
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
        return entity;
    }
}
