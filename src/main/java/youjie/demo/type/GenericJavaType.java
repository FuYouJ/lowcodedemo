package youjie.demo.type;

import lombok.Getter;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 00:31
 *
 * @author fuyouj
 */
@Getter
public abstract class GenericJavaType<T> implements JavaType<T> {
    private final String name;

    public GenericJavaType(String name) {
        this.name = name;
    }
}
