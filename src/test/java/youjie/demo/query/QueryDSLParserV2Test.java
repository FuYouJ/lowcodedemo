package youjie.demo.query;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import youjie.demo.QueryDSLParserV2;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2024/2/21 22:59
 */

public class QueryDSLParserV2Test {

    @Test
    public void test1SubArrayQuery() {
        String json = JsonUtil.getJsonByPath("src/test/resources/test1SubArrayQuery.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParserV2.parseConfigQuery(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
        Assertions.assertEquals(map.size(),0);
    }

    /**
     * 数组查询，两个变量，其中一个不能被替换
     */
    @Test
    public void test2_1SubArrayQuery() {
        String json = JsonUtil.getJsonByPath("src/test/resources/test2-1SubArrayQuery.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map parsed = QueryDSLParserV2.parseConfigQuery(queryDSL);
        Assertions.assertEquals(parsed,JsonUtil.getMapByPath("src/test/resources/test2-1SubArrayQuery-ans.json"));
    }


    /**
     * 数组查询，两个变量，均不能被替换，导致查询是空的
     */
    @Test
    public void test2_2SubArrayQuery(){
        String json = JsonUtil.getJsonByPath("src/test/resources/test2-2SubArrayQuery.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map parsed = QueryDSLParserV2.parseConfigQuery(queryDSL);
        Assertions.assertEquals(parsed,JsonUtil.getMapByPath("src/test/resources/test2-2SubArrayQuery-ans.json"));
    }

    /**
     * 正常解析，没有变量
     */
    @Test
    public void test2_3SubArrayQuery(){
        String json = JsonUtil.getJsonByPath("src/test/resources/test2-3SubArrayQuery.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map parsed = QueryDSLParserV2.parseConfigQuery(queryDSL);
        Assertions.assertEquals(parsed,JsonUtil.getMapByPath("src/test/resources/test2-3SubArrayQuery.json"));
    }

    /**
     * 变量替换测试
     */
    @Test
    public void test2_4ReplaceVariableTest(){
        String json = JsonUtil.getJsonByPath("src/test/resources/test2-4RelaceVariableTest.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map<String,Object> params = new HashMap<>();
        params.put("name","name");
        params.put("nameVar","aaa");
        params.put("name2","name2");
        params.put("nameVar2","bbb");
        Map parsed = QueryDSLParserV2.parseConfigQuery(queryDSL,params);
        Assertions.assertEquals(parsed,JsonUtil.getMapByPath("src/test/resources/test2-4RelaceVariableTest-ans.json"));
    }

    /**
     * 真实查询 bool嵌套 must 里有一个变量
     * 变量的查询还有干扰信息 比如 boost : 1
     */
    @Test
    public void test3(){
        String json = JsonUtil.getJsonByPath("src/test/resources/test3.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map parsed = QueryDSLParserV2.parseConfigQuery(queryDSL);
        Assertions.assertEquals(parsed,JsonUtil.getMapByPath("src/test/resources/test3-ans.json"));
    }
}
