package youjie.demo.type;

import java.util.List;

/**
 * List类型
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 00:28
 */

public class ListType extends GenericJavaType<List>{
    private final DataType dataType;
    public ListType(DataType dataType) {
        this.dataType = dataType;
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
