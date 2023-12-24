package youjie.demo.filter.operation;

import lombok.Getter;
import youjie.demo.type.JavaType;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:30
 */
@Getter
public class EqualOperation extends OperationBase {

    private final String name;
    private final JavaType<?> javaType;

    public EqualOperation(boolean optional, String name, JavaType<?> javaType) {
        super(IOperationType.EQ,optional);
        this.name = name;
        this.javaType = javaType;
    }
}
