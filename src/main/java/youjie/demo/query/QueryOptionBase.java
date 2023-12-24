package youjie.demo.query;

import lombok.Getter;
import youjie.demo.condition.ICondition;
import youjie.demo.source.ISource;

/**
 * @author fuyouj
 */
@Getter
public abstract class QueryOptionBase implements QueryOption {
    private final QueryOptionType type;

    private final ISource source;

    private final String methodName;

    private final ICondition condition;

    public QueryOptionBase(QueryOptionType type,
                              ISource source,
                              String methodName,
                              ICondition condition) {
        this.type = type;
        this.source = source;
        this.methodName = methodName;
        this.condition = condition;
    }
}
