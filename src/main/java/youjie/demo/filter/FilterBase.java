package youjie.demo.filter;

import lombok.Getter;
import youjie.demo.filter.operation.IOperation;

import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/20 23:55
 * @author fuyouj
 */
@Getter
public abstract class FilterBase implements Filter {
    private final FilterType filterType;

    private final List<IOperation> operations;

    public FilterBase(FilterType filterType, List<IOperation> operations) {
        this.filterType = filterType;
        this.operations = operations;
    }
}
