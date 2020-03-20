package com.tianhy.result;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.*;

/**
 * {@link}
 *
 * @Desc: 对结果集进行处理
 * @Author: thy
 * @CreateTime: 2019/4/27
 **/
@Slf4j
public class MyResultHandler implements ResultHandler {
    public static Gson gson = new Gson();

    private final Map<String,Object> mappedResults = new HashMap();
    @Override
    public void handleResult(ResultContext resultContext) {
        Map<String,Object> map = (Map) resultContext.getResultObject();
            log.debug("resultHandler : "+gson.toJson(map));
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
//            log.debug("key : "+stringObjectEntry.getKey());
//            log.debug("value : "+stringObjectEntry.getValue().toString());
            mappedResults.put(stringObjectEntry.getKey(),stringObjectEntry.getValue());
        }
    }
    public Map getMappedResults(){
        return mappedResults;
    }
}
