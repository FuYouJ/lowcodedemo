package youjie.demo;

import java.util.*;

/**
 * @author FuYouJ(youjie_fu @ intsig.net)
 * @createTime 2024/2/21 13:10
 * @since 2.4.0
 */
public class QueryDSLParserV2 {
    public static Map<String, Object> parseConfigQuery(Map<String, Object> queryDSL) {
        Map<String, Object> params = new HashMap<>();
        return parseConfigQuery(queryDSL, params);
    }

    /**
     * @param needParseQuery 待替换变量的query，对于没有被替换的部分，需要删除。
     * @param params   params是变量和值的对应，用于替换DSL
     * @return
     */
    public static Map<String, Object> parseConfigQuery(Map<String, Object> needParseQuery, Map<String, Object> params) {
        Map<String, Object> collectMap = new LinkedHashMap<>(needParseQuery.size());
        //有变量没有被替换成功，那么这整个查询都不能要了
        boolean existVariable = false;
        for (Map.Entry<String, Object> queryItem : needParseQuery.entrySet()) {
            String queryKey = queryItem.getKey();
            Object queryValue = queryItem.getValue();

            //判断是不是变量key
            if (isVariableKey(queryKey)) {
                queryKey = extractVariable(queryKey);
                //解析不了，跳过
                if (!params.containsKey(queryKey)) {
                    existVariable = true;
                    continue;
                }
                queryKey = (String) params.get(queryKey);
            }

            //判断是不是变量value
            if (isVariableVal(queryValue)) {
                String queryVal = extractVariable((String) queryValue);
                //解析不了，跳过
                if (!params.containsKey(queryVal)) {
                    existVariable = true;
                    continue;
                }
                queryValue = params.get(queryVal);
            }

            //判断是不是List
            if (isListVal(queryValue)) {
                List<Object> needParseQueryList = (List<Object>) queryValue;
                List<Object> parsedQueryList = new ArrayList<>();
                for (Object subQuery : needParseQueryList) {
                    if (subQuery instanceof Map) {
                        Map<String, Object> parsedSubQuery = parseConfigQuery((Map<String, Object>) subQuery, params);
                        if (!parsedSubQuery.isEmpty()) {
                            parsedQueryList.add(parsedSubQuery);
                        }
                    } else {
                        //不是查询 直接加入 fixme 没想到其他情况
                        parsedQueryList.add(subQuery);
                    }
                }
                //所有子查询都是无效的
                if (parsedQueryList.isEmpty()){
                    continue;
                }
                collectMap.put(queryKey,parsedQueryList);
            }

            //是个单查询
            else if (isMap(queryValue)){
                Map<String, Object> parsedQuery = parseConfigQuery((Map<String, Object>) queryValue, params);
                if (parsedQuery.isEmpty()){
                    continue;
                }
                collectMap.put(queryKey,parsedQuery);
            }
            else {
                collectMap.put(queryKey,queryValue);
            }
        }
        if (existVariable){
            return new HashMap<>();
        }
        return collectMap;
    }

    private static boolean isMap(Object obj){
        if (Objects.isNull(obj)){
            return false;
        }
        return obj instanceof Map;
    }

    private static boolean isVariableKey(String key) {
        if (key == null || key.isEmpty()) {
            return false;
        }
        return key.startsWith("${") && key.endsWith("}");
    }

    private static boolean isListVal(Object value) {
        return value instanceof List;
    }

    private static boolean isVariableVal(Object key) {
        if (Objects.isNull(key)) {
            return false;
        }
        if (key instanceof String) {
            return isVariableKey((String) key);
        }
        return false;
    }


    /**
     * 解析变量名字
     *
     * @param str ${var}
     * @return var
     */
    private static String extractVariable(String str) {
        int startIndex = str.indexOf("${");
        if (startIndex != -1) {
            int endIndex = str.indexOf("}", startIndex);
            if (endIndex != -1) {
                return str.substring(startIndex + 2, endIndex);
            }
        }
        // 如果没有找到变量，则返回空字符串
        return "";
    }

    private static boolean isListMap(List<?> list) {
        if (null == list || list.isEmpty()) {
            return false;
        }
        boolean isMap = true;
        for (Object o : list) {
            if (o instanceof Map) {
                continue;
            }
            return false;
        }
        return isMap;
    }
}
