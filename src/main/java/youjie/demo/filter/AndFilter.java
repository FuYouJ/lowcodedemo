package youjie.demo.filter;

import youjie.demo.filter.operation.IOperation;

import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:25
 */

public class AndFilter extends FilterBase {

    public AndFilter(List<IOperation> operations) {
        super(FilterType.AND, operations);
    }
}
