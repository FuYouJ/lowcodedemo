package youjie.demo;

import java.util.*;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2024/2/20 23:34
 */

public class QueryDSLParser {
    public static Map<String, Object> parseConfigDSL(Map<String, Object> queryDSL){
        return parseConfigDSL(queryDSL,new HashMap<>());
    }
    public static Map<String, Object> parseConfigDSL(Map<String, Object> queryDSL,Map<String,Object> params) {
        Map<String, Object> remainMap = new HashMap<>();
        //todo 对key的处理
        for (Map.Entry<String, Object> entry : queryDSL.entrySet()) {
            String originKey = entry.getKey();
            String key = originKey;
            if (originKey.startsWith("${") && originKey.endsWith("}")){
                String extractVariable = extractVariable(originKey);
                if (params.containsKey(extractVariable)){
                    key = (String) params.get(extractVariable);
                }else {
                    continue;
                }
            }

            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> subMap = (Map<String, Object>) value;
                Map<String, Object> parsedSubMap = parseConfigDSL(subMap);
                //为true时才跳过，否则报错
                if (parsedSubMap.size() == 1 && parsedSubMap.containsKey("optional")) {
                    continue;
                }
                if (parsedSubMap.isEmpty()) {
                    continue;
                }
                remainMap.put(key, parsedSubMap);
            } else if (value instanceof List) {
                List<Object> subList = (List<Object>) value;
                List<Object> parsedSubList = new ArrayList<>();
                boolean containsVariables = false;
                for (Object subItem : subList) {
                    if (subItem instanceof String) {
                        String strValue = (String) subItem;
                        if (strValue.contains("${")) {
                            containsVariables = true;
                            break;
                        }
                    }
                }
                if (!containsVariables) {
                    remainMap.put(key, value);
                } else {
                    for (Object subItem : subList) {
                        if (subItem instanceof String) {
                            String strValue = (String) subItem;
                            if (!strValue.contains("${")) {
                                parsedSubList.add(strValue);
                            }
                        } else if (subItem instanceof Map) {
                            Map<String, Object> parsedSubMap = parseConfigDSL((Map<String, Object>) subItem);
                            if (!parsedSubMap.isEmpty()) {
                                parsedSubList.add(parsedSubMap);
                            }
                        }
                    }
                    if (!parsedSubList.isEmpty()) {
                        remainMap.put(key, parsedSubList);
                    }
                }
            } else if (value instanceof String) {
                String strValue = (String) value;
                if (strValue.contains("${")) {
                    // Assuming variables are surrounded by "${" and "}"
                    String extractVariable = extractVariable(strValue);
                    if (params.containsKey(extractVariable)){
                        remainMap.put(key, params.get(extractVariable));
                    }
                } else {
                    remainMap.put(key, value);
                }
            } else {
                remainMap.put(key, value);
            }
        }
        return remainMap;
    }

    public static String extractVariable(String str) {
        int startIndex = str.indexOf("${");
        if (startIndex != -1) {
            int endIndex = str.indexOf("}", startIndex);
            if (endIndex != -1) {
                return str.substring(startIndex + 2, endIndex);
            }
        }
        return ""; // 如果没有找到变量，则返回 null
    }

}