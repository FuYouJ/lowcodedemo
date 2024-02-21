package youjie.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author FuYouJ(youjie_fu @ intsig.net)
 * @createTime 2024/2/21 13:10
 * @since 2.4.0
 */
public class QueryDSLParserV2 {
    public static Map<String, Object> parseConfigDSL(Map<String, Object> queryDSL){
        Map<String, Object> params = new HashMap<>();
        return parseConfigDSL(queryDSL, params);
    }

    /**
     *
     * @param queryDSL 待替换变量的DSL，对于没有被替换的部分，需要删除。
     * @param params params是变量和值的对应，用于替换DSL
     * @return
     */
    public static Map<String,Object> parseConfigDSL(Map<String, Object> queryDSL,Map<String,Object> params){
        Map<String,Object> collectMap = new HashMap<>(queryDSL.size());
        for (Map.Entry<String, Object> queryItem : queryDSL.entrySet()) {
            String queryKey = queryItem.getKey();
            Object queryValue = queryItem.getValue();
            if (isVariableKey(queryKey)){
                queryKey = extractVariable(queryKey);
                //解析不了，跳过
                if (!params.containsKey(queryKey)){
                    continue;
                }
                queryKey = (String) params.get(queryKey);
            }
            if (isVariableVal(queryValue)){
                String queryVal = extractVariable((String) queryValue);
                //解析不了，跳过
                if(!params.containsKey(queryVal)){
                    continue;
                }
                queryValue = params.get(queryVal);
            }
            if (isListVal(queryValue)){
                boolean existVariable = false;
                List<Object> queryList = (List<Object>) queryValue;
                for (Object query : queryList) {
                    if (isVariableVal(query)){
                        String queryVal = extractVariable((String) query);
                        if (!params.containsKey(queryVal)){
                            existVariable = true;
                            break;
                        }
                    }
                }
            }
        }
        return collectMap;
    }

    private static boolean isVariableKey(String key){
        if (key == null || key.isEmpty()){
            return false;
        }
        return key.startsWith("${") && key.endsWith("}");
    }

    private static boolean isListVal(Object value){
        return value instanceof List;
    }

    private static boolean isVariableVal(Object key){
        if(Objects.isNull(key)){
            return false;
        }
        if (key instanceof String){
            return isVariableKey((String) key);
        }
        return false;
    }

    private static String extractVariable(String str) {
        int startIndex = str.indexOf("${");
        if (startIndex != -1) {
            int endIndex = str.indexOf("}", startIndex);
            if (endIndex != -1) {
                return str.substring(startIndex + 2, endIndex);
            }
        }
        return ""; // 如果没有找到变量，则返回空字符串
    }
}
