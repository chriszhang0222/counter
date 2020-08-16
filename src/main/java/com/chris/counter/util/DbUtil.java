package com.chris.counter.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbUtil {

    private static DbUtil dbUtil = null;
    private DbUtil(){

    }

    @PostConstruct
    private void init(){
        dbUtil = new DbUtil();
        dbUtil.setSqlSessionTemplate(this.sqlSessionTemplate);
    }

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    private void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static long getId(){
        Long res = dbUtil.getSqlSessionTemplate().selectOne(
                "testMapper.queryBalance"
        );
        if(res == null)
            return -1;
        return res;
    }


}
