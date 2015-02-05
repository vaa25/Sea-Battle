package view.networks.serializators;

import java.util.*;

/**
 * @author Alexander Vlasov
 */
public class CollectionSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Set f = new HashSet<>();
        f.add(1);
        f.add(1);
        f.add(6);
        f.add(1);
        f.add(1);
        Serializator serializator = new Serializator();
        byte[] bytes = Serializator.debuild(f);
        System.out.println(Arrays.toString(bytes));
        Set res = (Set) Serializator.build(bytes);
        Iterator iterator = res.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public byte[] debuild(Object value) {
        Collection list = (Collection) value;
        Object[] content = list.toArray();
        byte[][] elements = new byte[content.length][];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = Serializator.debuild(content[i]);
        }
        byte[] bytes = Packer.pack(value.getClass(), elements);
        bytes[0] = Serializator.COLLECTION;
        return bytes;

    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        StringSerializator serializator = new StringSerializator();
        String typeClassName = (String) serializator.build(bytes, off + 5);
        byte[][] splitted = Splitter.split(bytes, off);
        Class clazz = null;
        try {
            clazz = Class.forName(typeClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection res = null;
        try {
            res = (Collection) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (res instanceof List) {
            List list = (List) res;
            for (int i = 0; i < splitted.length; i++) {
                list.add(Serializator.build(splitted[i]));
            }
            return list;
        }
        if (res instanceof Set) {
            Set set = (Set) res;
            for (int i = 0; i < splitted.length; i++) {
                set.add(Serializator.build(splitted[i]));
            }
            return set;
        }
        return res;
    }


}
