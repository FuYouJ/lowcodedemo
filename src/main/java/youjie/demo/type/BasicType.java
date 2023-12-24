package youjie.demo.type;

/**
 * 基本数据类型
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 21:18
 * @author fuyouj
 */

public class BasicType<T> implements JavaType<T> {
    public static final BasicType<String> STRING_TYPE =
            new BasicType<>(String.class, DataType.STRING);
    public static final BasicType<Boolean> BOOLEAN_TYPE =
            new BasicType<>(Boolean.class, DataType.BOOLEAN);
    public static final BasicType<Void> VOID_TYPE = new BasicType<>(Void.class, DataType.NULL);

    private final Class<T> typeClass;
    private final DataType dataType;

    public BasicType(Class<T> typeClass, DataType dataType) {
        this.typeClass = typeClass;
        this.dataType = dataType;
    }


    @Override
    public Class<T> getTypeClass() {
        return this.typeClass;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }
}
