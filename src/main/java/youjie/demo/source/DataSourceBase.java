package youjie.demo.source;

import lombok.Getter;
import youjie.demo.filter.Filter;

import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 22:44
 */
@Getter
public class DataSourceBase implements ISource {
    private  List<Field<?>> fields;
    private  Filter filter;
    private final SourceType sourceType = SourceType.ES7;

    public DataSourceBase(List<Field<?>> fields, Filter filter) {
        this.fields = fields;
        this.filter = filter;
    }
}
