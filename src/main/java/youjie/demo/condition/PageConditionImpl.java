package youjie.demo.condition;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:07
 */

public class PageConditionImpl extends ConditionBase {

    /**
     *  后面的是默认值
     *  "pageName": "currenPage:1",
     *  "sizeName": "pageSize:50",
     */
    private final String pageName;
    private final String sizeName;
    public PageConditionImpl(String pageName, String sizeName) {
        super(ConditionType.PAGE);
        this.pageName = pageName;
        this.sizeName = sizeName;
    }
}
