package youjie.demo.query;

import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2024/2/21 22:59
 */

public class JsonUtil {


    public static Map<String,Object> getMapByPath(String filePath){
        String jsonByPath = getJsonByPath(filePath);
        LinkedHashMap linkedHashMap = JSONObject.parseObject(jsonByPath, LinkedHashMap.class);
        return linkedHashMap;
    }
    public static String getJsonByPath(String filePath) {
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
