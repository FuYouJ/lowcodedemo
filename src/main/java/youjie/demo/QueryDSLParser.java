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
    public static Map<String, Object> parseConfigDSL(Map<String, Object> queryDSL, Map<String, Object> params) {
        Map<String, Object> remainMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : queryDSL.entrySet()) {
            String originKey = entry.getKey();
            String key = originKey;
            Object value = entry.getValue();

            // 检查 key 是否包含变量
            if (key.contains("${")) {
                String extractVariable = extractVariable(key);
                if (params.containsKey(extractVariable)) {
                    key = (String) params.get(extractVariable);
                } else {
                    // 如果变量未被替换，则跳过整个查询
                    continue;
                }
            }

            if (value instanceof Map) {
                Map<String, Object> subMap = (Map<String, Object>) value;
                Map<String, Object> parsedSubMap = parseConfigDSL(subMap, params);
                // 为 true 时才跳过，否则报错
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
                            Map<String, Object> parsedSubMap = parseConfigDSL((Map<String, Object>) subItem, params);
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
                    // 检查变量是否被替换
                    String extractVariable = extractVariable(strValue);
                    if (params.containsKey(extractVariable)) {
                        remainMap.put(key, params.get(extractVariable));
                    } else {
                        // 如果变量未被替换，则跳过整个查询
                        continue;
                    }
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
        return ""; // 如果没有找到变量，则返回空字符串
    }




}
