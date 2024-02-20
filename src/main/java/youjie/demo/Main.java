package youjie.demo;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.alibaba.fastjson2.JSON;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 16:08
 */

public class Main {
    public static void main(String[] args) {
        SearchRequest.Builder searchRequest = new SearchRequest.Builder();
        SearchRequest.Builder a1 = searchRequest.query(q -> q.bool(
                b -> b.must(m -> m.term(t -> t.field("a").value(1)))
        ));
        SearchRequest a = a1.build();
        System.out.println(JSON.toJSONString(searchRequest));
        System.out.println(JSON.toJSONString(a));
        System.out.println(a.toString());
    }
}
