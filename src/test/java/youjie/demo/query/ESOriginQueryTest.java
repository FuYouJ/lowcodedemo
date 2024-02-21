package youjie.demo.query;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import youjie.demo.Main;
import youjie.demo.QueryDSLParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
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
    public void testOneOfVariableMiss(){
        String json = getJsonByPath("src/test/resources/oneOfEmpty.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParser.parseConfigDSL(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
    }

    @Test
    public void testSimple(){
        String json = getJsonByPath("src/test/resources/simple.json");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
        Map map = QueryDSLParser.parseConfigDSL(queryDSL);
        System.out.println(JSONObject.toJSONString(map));
    }
    @Test
    public void testXYQ(){
        String json = getJsonByPath("src/test/resources/json数据.txt");
        Map queryDSL = JSONObject.parseObject(json, Map.class);
    }

    private String allVariableMiss() {
        return getJsonByPath("src/test/resources/testQuery.json");
    }

    private String getJsonByPath(String filePath){
        StringBuilder jsonString = new StringBuilder();
        try {
            // 读取 JSON 文件内容
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
        } catch (Exception e) {
            return "";
        }
        return jsonString.toString();
    }
}
