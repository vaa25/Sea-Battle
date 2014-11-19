package view.networks.serializators;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class ArraySerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Integer[][] f = new Integer[][]{{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}};
        System.out.println(f.getClass().getComponentType());
        Serializator serializator = new Serializator();
        byte[] bytes = serializator.debuild(f);
        System.out.println(Arrays.toString(bytes));
        Integer[][] o = (Integer[][]) serializator.build(bytes);
        for (int i = 0; i < o.length; i++) {
            for (int j = 0; j < o[i].length; j++) {
                System.out.println(o[i][j]);
            }
            System.out.println();
        }
    }

    public byte[] debuild(Object value) {
        Object[] v = (Object[]) value;

        byte[][] elements = new byte[v.length][];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = Serializator.debuild(v[i]);
        }
        byte[] bytes = Serializator.pack(value.getClass().getComponentType(), elements);
        bytes[0] = Serializator.ARRAY;
        return bytes;

    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        StringSerializator serializator = new StringSerializator();
        String typeClassName = (String) serializator.build(bytes, off + 5);
        byte[][] splitted = Serializator.split(bytes, off);
        Class clazz = null;
        try {
            clazz = Class.forName(typeClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object res = (Array.newInstance(clazz, splitted.length));
        for (int i = 0; i < splitted.length; i++) {
            Object j = Serializator.build(splitted[i]);
            Array.set(res, i, j);
        }
        return res;
    }


}
