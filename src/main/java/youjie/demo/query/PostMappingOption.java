package youjie.demo.query;

import lombok.Getter;
import youjie.demo.condition.ICondition;
import youjie.demo.source.ISource;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 22:17
 */
@Getter
public class PostMappingOption extends QueryOptionBase {

    private final PostMapping postMapping;
    public PostMappingOption(ISource source,
                             PostMapping postMapping,
                             ICondition condition) {
        super(QueryOptionType.POSTMAPPING, source, postMapping.getMethodName(), condition);
        this.postMapping = postMapping;
    }

}
