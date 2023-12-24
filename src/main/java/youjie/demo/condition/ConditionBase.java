package youjie.demo.condition;

import lombok.Getter;
import youjie.demo.filter.Filter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:05
 */
@Getter
public class ConditionBase implements ICondition {
    private final ConditionType conditionType;

    private final Filter filter;

    public ConditionBase(ConditionType conditionType, Filter filter) {
        this.conditionType = conditionType;
        this.filter = filter;
    }
}
