package youjie.demo.query;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import youjie.demo.condition.ICondition;
import youjie.demo.condition.PageConditionImpl;
import youjie.demo.filter.AndFilter;
import youjie.demo.filter.FilterBase;
import youjie.demo.filter.operation.EqualOperation;
import youjie.demo.filter.operation.IOperation;
import youjie.demo.source.ESDataSource;
import youjie.demo.source.Field;
import youjie.demo.type.BasicType;
import youjie.demo.type.DataType;
import youjie.demo.type.JavaType;
import youjie.demo.type.ListType;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
        ESDataSource dataSource = parseDataSource(jsonObject);
        ICondition condition = parseCondition(jsonObject.getObject("condition", Map.class));
        postMappingOption = new PostMappingOption(dataSource,postMapping,condition);

        toConvertESQuery(dataSource,condition);
    }

    private void toConvertESQuery(ESDataSource dataSource, ICondition condition) {
        List<Field<?>> fields = dataSource.getFields();
        String index = dataSource.getIndex();
        PageConditionImpl pageCondition = (PageConditionImpl) condition;
        List<IOperation> operations = ((FilterBase) pageCondition.getFilter()).getOperations();
        Long currentPageIndex = pageCondition.getCurrentPageIndex();
        Long size = pageCondition.getSize();
        SearchRequest.Builder searchRequest = new SearchRequest.Builder();
        searchRequest
                .from(currentPageIndex.intValue())
                .size(size.intValue());
        BoolQuery.Builder builder = new BoolQuery.Builder();
        for (Field<?> field : fields) {
            String name = field.getName();
            builder.must(t -> t.term(te -> te.field(name).value(1L)));
        }
        searchRequest.query(q -> q.bool(builder.build()));
        System.out.println(JSON.toJSONString(searchRequest.build()));
    }

    private ESDataSource parseDataSource(JSONObject jsonObject) {
        Map<String,Object> source = jsonObject.getObject("source", Map.class);
        String index = (String) source.get("index");
        JSONArray array = (JSONArray) source.get("fields");
        List<Field<?>> fields = parseArray(array);
        return new ESDataSource(index,fields);
    }

    private ICondition parseCondition(Map condition) {
        String pageName = (String) condition.get("pageName");
        String sizeName = (String) condition.get("sizeName");
        JSONObject filter = (JSONObject) condition.get("filter");
        JSONArray and = (JSONArray) filter.get("and");
        List<IOperation> operations = new ArrayList<>();
        for (Object o : and) {
            JSONObject jo = (JSONObject) o;
            IOperation op = parseOp(jo);
            operations.add(op);
        }
        PageConditionImpl pageCondition = new PageConditionImpl(pageName,sizeName,new AndFilter(operations));
        return pageCondition;
    }

    private IOperation parseOp(JSONObject jo) {
        return new EqualOperation(true,"role",new ListType(DataType.STRING));
    }

    private List<Field<?>> parseArray(JSONArray array) {
        List<Field<?>> fields = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = (JSONObject) array.get(i);
            String name = o.getObject("name", String.class);
            String javaType = o.getObject("javaType", String.class);
            JavaType<?> parsedType = parseJavaType(javaType);
            String as = o.getObject("as", String.class);
            Field<?> field = new Field<>(name,parsedType,as);
            fields.add(field);
        }
        return fields;
    }

    private JavaType<?> parseJavaType(String javaType) {
        if (javaType.equalsIgnoreCase("string")){
            return BasicType.STRING_TYPE;
        }else {
            return new ListType(DataType.STRING);
        }
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
