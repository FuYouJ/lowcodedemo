package youjie.demo.filter;

import lombok.Getter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/20 23:55
 * @author fuyouj
 */
@Getter
public abstract class FilterBase implements Filter {
    private final FilterType filterType;

    public FilterBase(FilterType filterType) {
        this.filterType = filterType;
    }
}
