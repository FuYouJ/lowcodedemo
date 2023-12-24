package youjie.demo.source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import youjie.demo.type.JavaType;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 22:04
 * @author fuyouj
 */
@Getter
@Setter
@AllArgsConstructor
public class Field<T> {
    private String name;

    private JavaType<T> javaType;

    private String as;
}
