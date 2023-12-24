package youjie.demo.source;

import lombok.Getter;
import youjie.demo.condition.ICondition;

import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 22:44
 */
@Getter
public class ESDataSource implements ISource {

    private String index;
    private List<Field<?>> fields;
    private final SourceType sourceType = SourceType.ES7;

    public ESDataSource(String index, List<Field<?>> fields) {
        this.fields = fields;
    }
}
