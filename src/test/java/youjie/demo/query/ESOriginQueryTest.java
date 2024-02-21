package youjie.demo.query;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import youjie.demo.QueryDSLParser;

import java.util.Map;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2024/2/20 22:27
 */
public class ESOriginQueryTest {
    @Test
    public void testAllVariableMissJson() {
        String json = allVariableMiss();
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParser.parseConfigDSL(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
    }

    @Test
    public void testOneOfVariableMiss() {
        String json = getJsonByPath("src/test/resources/test3.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParser.parseConfigDSL(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
    }

    @Test
    public void testSimple() {
        String json = getJsonByPath("src/test/resources/test1.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParser.parseConfigDSL(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
    }

    @Test
    public void testXYQ() {
        String json = getJsonByPath("src/test/resources/json数据.txt");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
    }

    private String allVariableMiss() {
        return getJsonByPath("src/test/resources/testQuery.json");
    }

    private String getJsonByPath(String filePath) {
        return JsonUtil.getJsonByPath(filePath);
    }
}
