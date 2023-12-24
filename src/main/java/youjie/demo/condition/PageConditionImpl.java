package youjie.demo.condition;

import lombok.Getter;
import youjie.demo.filter.Filter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 15:07
 */
@Getter
public class PageConditionImpl extends ConditionBase {

    /**
     *  后面的是默认值
     *  "pageName": "currenPage:1",
     *  "sizeName": "pageSize:50",
     */
    private final String pageName;
    private final String sizeName;
    public PageConditionImpl(String pageName, String sizeName, Filter filter) {
        super(ConditionType.PAGE, filter);
        this.pageName = pageName;
        this.sizeName = sizeName;
    }

    public Long getCurrentPageIndex(){
        Long page = Long.valueOf(pageName.substring(pageName.indexOf(":")+1));
        Long size = Long.valueOf(sizeName.substring(sizeName.indexOf(":")+1));
        return (page - 1) * size;
    }

    public Long getSize(){
        return Long.valueOf(sizeName.substring(sizeName.indexOf(":")+1));
    }
}
