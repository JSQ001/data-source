package com.eicas.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.fillStrategy(metaObject, "createdTime", LocalDateTime.now());
        this.fillStrategy(metaObject, "updatedTime", LocalDateTime.now());
        this.fillStrategy(metaObject, "createdBy",0L);
        this.fillStrategy(metaObject, "updatedBy",0L);
        //reference_numbers
        this.fillStrategy(metaObject, "deleted",false);
        System.out.println(metaObject.getObjectWrapper());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.fillStrategy(metaObject, "updatedBy", 0L);
        this.fillStrategy(metaObject, "deleted", false);
    }
}
