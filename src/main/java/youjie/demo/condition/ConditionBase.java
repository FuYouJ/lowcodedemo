package youjie.demo.condition;

import lombok.Getter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:05
 */
@Getter
public class ConditionBase implements ICondition {
    private final ConditionType conditionType;

    public ConditionBase(ConditionType conditionType) {
        this.conditionType = conditionType;
    }
}
