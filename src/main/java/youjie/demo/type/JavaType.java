package youjie.demo.type;

import java.io.Serializable;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 00:07
 */

public interface JavaType<T> extends Serializable {

    Class<T> getTypeClass();

    DataType getDataType();

}
