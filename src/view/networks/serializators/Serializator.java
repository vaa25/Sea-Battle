package view.networks.serializators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Сериализация - разложение объектов на байты.
 * <p>
 * Структура итогового массива байтов byte[]bytes:
 * xx - код примитивного класса (0 ... 126) или маркер непримитивного (127)
 * xx xx xx xx - длина итогового массива (int), извлекается getLength(byte[] bytes)
 * xx ... xx - массив имени класса (String)
 * xx - количество полей (byte)
 * xx ... xx - поля
 * <p>
 * непримитивные поля и классы: рассматриваются как классы внутри класса
 * xx - 127
 * xx xx xx xx - длина массива поля, извлекается  getLength(byte[] bytes)
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
    public static final byte STRING = 10;
    public static final byte INTEGER = 9;
    public static final byte BOOLEAN = 8;
    public static final byte CLASS = 127;
    public static final byte SHORT = 7;
    public static final byte BYTE = 6;
    public static final byte LONG = 5;
    public static final byte FLOAT = 4;
    public static final byte DOUBLE = 3;
    public static final byte BIGINTEGER = 2;
    public static final byte BIGDECIMAL = 1;
    public static final byte ARRAY = 11;
    public static final byte COLLECTION = 12;
    public static final byte ENUM = 13;


    private static Map<Class, Byte> codes = new HashMap<>();
    private static Map<Byte, Integer> lengths = new HashMap<>();

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
        Class clazz = object.getClass();
        byte code;
        if (clazz.isArray()) {
            code = ARRAY;
        } else if (clazz.isEnum()) {
            code = ENUM;
        } else {
            code = getCode(clazz);
        }
        return selectSerializator(code).debuild(object);
    }

    public static synchronized Object build(byte[] bytes) {
        return selectSerializator(bytes[0]).build(bytes);

    }

    public static byte getCode(Class clazz) {
        if (containsCode(clazz)) return codes.get(clazz);
        else return CLASS;
    }

    public static boolean containsCode(Class clazz) {
        if (clazz.isArray() || clazz.isEnum()) {
            return true;
        } else {
            return codes.containsKey(clazz);
        }
    }

    /**
     * Извлекает длину массива преобразованного объекта
     *
     * @param bytes массив преобразованного объекта
     *
     * @return длина
     */

    public static int getLength(byte[] bytes) {
        return getLength(bytes, 0);

    }

    public static int getLength(byte code) {
        if (lengths.containsKey(code)) return lengths.get(code);
        return -1;

    }

    /**
     * Длина в массиве указанная в массиве со смещением
     *
     * @param bytes массив
     * @param off   смещение
     *
     * @return длина
     */
    static int getLength(byte[] bytes, int off) {
        byte[] lengthB = new byte[4];
        System.arraycopy(bytes, off, lengthB, 0, 4);
        return (Byte.toUnsignedInt(lengthB[3]) << 24) +
                (Byte.toUnsignedInt(lengthB[2]) << 16) +
                (Byte.toUnsignedInt(lengthB[1]) << 8) +
                Byte.toUnsignedInt(lengthB[0]);
    }


    /**
     * Извлечение отдельных полей в виде массивов из главного массива
     * <p>
     * xx - код примитивного класса или маркер непримитивного
     * xx xx xx xx - длина итогового массива (int), извлекается  getLength(byte[] bytes)
     * xx - длина имени класса (byte)
     * xx ... xx - массив имени класса (String)
     * xx - количество полей (byte)
     * xx ... xx - поля
     * поля:
     * xx - определенный примитивный класс или нет
     * xx xx xx xx - длина массива поля, извлекается  getLength(byte[] bytes)
     * xx - длина имени класса поля (byte)
     * xx ... xx - массив имени класса поля
     * xx ... xx - массив значения поля, может содержать свои поля.
     * <p>
     * для примитивных
     * xx - код примитивного класса
     * xx ... xx - данные фиксированной для каждого примитивного класса длины
     * *длина имени класса (поля) включает в себя себя и остальные данные, принадлежащие классу (полю)
     *
     * @param bytes главный массив
     *
     * @return массив массивов полей
     */
    public static byte[][] split(byte[] bytes) {
        return split(bytes, 0);
    }

    public static byte[][] split(byte[] bytes, int off) {
//        if (codes.containsKey(bytes[off])) return new byte[][]{bytes};
//        else {
        int classNameLen = getLength(bytes, off + 6);
        int startIndex = off + 6 + classNameLen;
        int amount = bytes[startIndex - 1];
        byte[][] res = new byte[amount][];
        for (int i = 0; i < amount; i++) {
            Integer len;
            if ((len = lengths.get(bytes[startIndex])) == null) {
                len = getLength(bytes, startIndex + 1);
            }
            byte[] body = new byte[len];
            System.arraycopy(bytes, startIndex, body, 0, len);
            res[i] = body;
            startIndex += len;
        }
        return res;

//        }
    }

    /**
     * xx xx xx xx - длина итогового массива (int), извлекается  getLength(byte[] bytes)
     * xx - длина имени класса (byte)
     * xx ... xx - массив имени класса (String)
     * xx - количество полей (byte)
     * xx ... xx - поля
     * поля:
     * xx xx xx xx - длина массива поля, извлекается  getLength(byte[] bytes)
     * xx - длина имени класса поля (byte)
     * xx ... xx - массив имени класса поля
     * xx ... xx - массив значения поля, может содержать свои поля.
     *
     * @param clazz
     * @param bytes
     *
     * @return
     */
    public static byte[] pack(Class clazz, byte[][] bytes) {
//        if (!containsCode(clazz)) {                   // не примитивный класс
        String className = clazz.getName();
        StringSerializator serializator = new StringSerializator();
        byte[] classNameB = serializator.debuild(className);
        int length = 1 + 4 + 1 + classNameB.length;
        for (int i = 0; i < bytes.length; i++) {
            length += bytes[i].length;
        }
        byte[] res = new byte[length];
        byte[] len = setLength(length);
        res[0] = CLASS;
        System.arraycopy(len, 0, res, 1, 4);
        System.arraycopy(classNameB, 0, res, 5, classNameB.length);
        int startIndex = 6 + classNameB.length;
        res[startIndex - 1] = (byte) bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            System.arraycopy(bytes[i], 0, res, startIndex, bytes[i].length);
            startIndex += bytes[i].length;
        }
        return res;

//        } else {
//            return bytes[0];
//        }
    }

    /**
     * Разложение int в четыре байта. Используется как для данных.
     *
     * @param k int
     *
     * @return byte[4]
     */
    public static byte[] setLength(int k) {
        byte[] res = new byte[4];
        res[0] = (byte) (k);
        res[1] = (byte) (k >> 8);
        res[2] = (byte) (k >> 16);
        res[3] = (byte) (k >> 24);
        return res;
    }


}
