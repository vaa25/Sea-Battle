package view.networks.serializators;


import view.Person;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Сериализатор для объектов
 * <p>
 * Структура итогового массива байтов byte[]bytes:
 * <p>
 * xx xx xx xx - длина итогового массива (int), извлекается  convertBytesToInt(byte[] bytes)
 * xx - длина имени класса (byte)
 * xx ... xx - массив имени класса (String)
 * xx - количество полей (byte)
 * xx ... xx - поля
 * поля:
 * xx xx xx xx - длина массива поля, извлекается  convertBytesToInt(byte[] bytes)
 * xx - длина имени класса поля (byte)
 * xx ... xx - массив имени класса поля
 * xx ... xx - массив значения поля, может содержать свои поля.
 * <p>
 * *длина имени класса (поля) включает в себя себя и остальные данные, принадлежащие классу (полю)
 *
 * @author Alexander Vlasov
 */
public class ObjectSerializator implements SerializatorInterface {


    public static void main(String[] args) {
        test(new Serializator(), new Person("Alex"));

    }

    private static void test(Serializator serializator, Object object) {
        System.out.println(serializator.getClass().getSimpleName());
        byte[] bytes = Serializator.debuild(object);
        System.out.println(Arrays.toString(bytes));

        System.out.println(Serializator.build(bytes));
        System.out.println();
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        if (bytes[off] == Serializator.CLASS) {
            StringSerializator serializator = new StringSerializator();

            String className = (String) serializator.build(bytes, off + 5);
            try {
                Class clazz = Class.forName(className);
                Object object = (clazz.newInstance());
                Field[] fields = clazz.getDeclaredFields();
                byte[][] splitted = Splitter.split(bytes);
                int j = 0;
                for (int i = 0; i < fields.length; i++) {

                    Field field = fields[i];
                    if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT) continue;
                    field.setAccessible(true);
                    Object value = Serializator.build(splitted[j++]);
                    field.set(object, value);
                }
                return object;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public byte[] debuild(Object object) {
        Class clazz = object.getClass();

        Field[] declaredFields = clazz.getDeclaredFields();
        int fieldsToTransit = 0;
        int end = declaredFields.length;
        for (int i = 0; i < end; i++) {
            int modifiers = declaredFields[i].getModifiers();
            if ((modifiers & Modifier.TRANSIENT) != Modifier.TRANSIENT) fieldsToTransit++;
        }
        byte[][] bytes = new byte[fieldsToTransit][];
        int j = 0;
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if ((declaredFields[i].getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT) continue;
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                bytes[j++] = Serializator.debuild(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return Packer.pack(clazz, bytes);
    }


}
