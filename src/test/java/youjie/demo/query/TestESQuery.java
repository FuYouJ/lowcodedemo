package youjie.demo.query;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Map;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 16:16
 */

public class TestESQuery {

    @Test
    public void testPageQuery() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("page.json");
        JSONObject jsonObject = parseInputStream(stream);
        PostMappingOption postMappingOption;

        PostMapping postMapping = jsonObject.getObject("postMapping", PostMapping.class);


    }

    public static <T> T getObj(Map<String,Object> jsonObject,String path,Class<T> clazz){
        String[] split = path.split("\\.");
        Map<String,Object> map = jsonObject;
        for (int i = 0; i < split.length; i++) {
            Object o = map.get(split[i]);
            if (o instanceof Map){
                map = (Map<String, Object>) o;
            }else {
                return (T)o;
            }
        }
        return null;
    }

    public static JSONObject parseInputStream(InputStream inputStream) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(jsonBuilder.toString());
    }
}
