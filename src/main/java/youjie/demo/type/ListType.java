package youjie.demo.type;

import java.util.List;

/**
 * List类型
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 00:28
 */

public class ListType extends GenericJavaType<List>{
    private DataType dataType;
    public ListType(String name) {
        super(name);
    }

    @Override
    public Class<List> getTypeClass() {
        return List.class;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }
}
