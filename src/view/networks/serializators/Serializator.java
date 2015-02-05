package view.networks.serializators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Сериализация - разложение объектов на байты.
 * <p>
 * Структура итогового массива байтов byte[]bytes:
 * xx - код примитивного класса (0 ... 126) или маркер непримитивного (127)
 * xx xx xx xx - длина итогового массива (int), извлекается convertBytesToInt(byte[] bytes)
 * xx ... xx - массив имени класса (String)
 * xx - количество полей (byte)
 * xx ... xx - поля
 * <p>
 * непримитивные поля и классы: рассматриваются как классы внутри класса
 * xx - 127
 * xx xx xx xx - длина массива поля, извлекается  convertBytesToInt(byte[] bytes)
 * xx ... xx - массив имени класса поля
 * xx ... xx - массив значения объекта поля, может содержать свои поля.
 * <p>
 * примитивные поля и классы: рассматриваются как примитивные классы внутри класса
 * xx - код определенного примитивного класса
 * xx ... xx - данные фиксированной для каждого примитивного класса длины (кроме String)
 *
 * @author Alexander Vlasov
 */
public class Serializator {
    static final byte STRING = 10;
    static final byte INTEGER = 9;
    static final byte BOOLEAN = 8;
    static final byte CLASS = 127;
    static final byte SHORT = 7;
    static final byte BYTE = 6;
    static final byte LONG = 5;
    static final byte FLOAT = 4;
    static final byte DOUBLE = 3;
    static final byte BIGINTEGER = 2;
    static final byte BIGDECIMAL = 1;
    static final byte ARRAY = 11;
    static final byte COLLECTION = 12;
    static final byte ENUM = 13;

    private static final Map<Class, Byte> codes = new HashMap<>();
    private static final Map<Byte, Integer> lengths = new HashMap<>();

    static {
        codes.put(Boolean.class, BOOLEAN);
        codes.put(boolean.class, BOOLEAN);
        codes.put(Integer.class, INTEGER);
        codes.put(int.class, INTEGER);
        codes.put(Double.class, DOUBLE);
        codes.put(double.class, DOUBLE);
        codes.put(Short.class, SHORT);
        codes.put(short.class, SHORT);
        codes.put(Byte.class, BYTE);
        codes.put(byte.class, BYTE);
        codes.put(Long.class, LONG);
        codes.put(long.class, LONG);
        codes.put(Float.class, FLOAT);
        codes.put(float.class, FLOAT);
        codes.put(String.class, STRING);
        codes.put(BigInteger.class, BIGINTEGER);
        codes.put(BigDecimal.class, BIGDECIMAL);
        codes.put(Collection.class, COLLECTION);
        codes.put(List.class, COLLECTION);
        codes.put(ArrayList.class, COLLECTION);
        codes.put(LinkedList.class, COLLECTION);
        codes.put(Set.class, COLLECTION);
        codes.put(HashSet.class, COLLECTION);
        codes.put(SortedSet.class, COLLECTION);
        codes.put(TreeSet.class, COLLECTION);
        codes.put(Enum.class, ENUM);

        lengths.put(BOOLEAN, 2);
        lengths.put(INTEGER, 5);
        lengths.put(FLOAT, 5);
        lengths.put(SHORT, 3);
        lengths.put(BYTE, 2);
        lengths.put(LONG, 9);
        lengths.put(DOUBLE, 9);
    }

    private static SerializatorInterface selectSerializator(byte code) {
        switch (code) {
            case BOOLEAN:
                return new BooleanSerializator();
            case INTEGER:
                return new IntegerSerializator();
            case DOUBLE:
                return new DoubleSerializator();
            case STRING:
                return new StringSerializator();
            case SHORT:
                return new ShortSerializator();
            case BYTE:
                return new ByteSerializator();
            case LONG:
                return new LongSerializator();
            case FLOAT:
                return new FloatSerializator();
            case BIGINTEGER:
                return new BigIntegerSerializator();
            case BIGDECIMAL:
                return new BigDecimalSerializator();
            case ARRAY:
                return new ArraySerializator();
            case COLLECTION:
                return new CollectionSerializator();
            case ENUM:
                return new EnumSerializator();
            default:
                return new ObjectSerializator();
        }
    }

    public static synchronized byte[] debuild(Object object) {
        return selectSerializator(selectCode(object.getClass())).debuild(object);
    }

    private static byte selectCode(Class clazz) {
        if (clazz.isArray()) {
            return ARRAY;
        } else if (clazz.isEnum()) {
            return ENUM;
        } else {
            return getCode(clazz);
        }
    }

    public static synchronized Object build(byte[] bytes) {
        return selectSerializator(bytes[0]).build(bytes);

    }

    public static byte getCode(Class clazz) {
        if (containsCode(clazz)) {
            return codes.get(clazz);
        } else {
            return CLASS;
        }
    }

    public static boolean containsCode(Class clazz) {
        if (clazz.isArray() || clazz.isEnum()) {
            return true;
        } else {
            return codes.containsKey(clazz);
        }
    }

    public static int getLength(byte code) {
        if (lengths.containsKey(code)) {
            return lengths.get(code);
        } else {
            return -1;
        }
    }
}
