package youjie.demo.filter.operation;

import lombok.Getter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:30
 */
@Getter
public class OperationBase implements IOperation {
    private final IOperationType operationType;

    private final boolean optional;

    public OperationBase(IOperationType operationType, boolean optional) {
        this.operationType = operationType;
        this.optional = optional;
    }
}
