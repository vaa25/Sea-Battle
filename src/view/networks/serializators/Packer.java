package view.networks.serializators;

/**
 * Класс преобразовывает заданный класс с уже преобразованными полями в массив байтов
 *
 * @author Alexander Vlasov
 */
public class Packer {

    private static byte[] classNameBytes;
    private static byte[][] fieldsBytes;
    private static byte[] result;
    private static int wholeLength;

    /**
     * xx xx xx xx - длина итогового массива (int), извлекается  convertBytesToInt()
     * xx - длина имени класса (byte)
     * xx ... xx - массив имени класса (String)
     * xx - количество полей (byte)
     * xx ... xx - поля
     * поля:
     * xx xx xx xx - длина массива поля, извлекается  convertBytesToInt()
     * xx - длина имени класса поля (byte)
     * xx ... xx - массив имени класса поля
     * xx ... xx - массив значения поля, может содержать свои поля.
     *
     * @param clazz заданный класс
     * @param bytes его преобразованные поля
     *
     * @return
     */
    static byte[] pack(Class clazz, byte[][] bytes) {
        classNameBytes = new StringSerializator().debuild(clazz.getName());
        fieldsBytes = bytes;
        wholeLength = getLength();
        result = new byte[wholeLength];
        result[0] = Serializator.CLASS;
        packClassName();
        packFields();
        return result;
    }

    private static void packFields() {
        int startIndex = 6 + classNameBytes.length;
        result[startIndex - 1] = (byte) fieldsBytes.length;
        for (int i = 0; i < fieldsBytes.length; i++) {
            System.arraycopy(fieldsBytes[i], 0, result, startIndex, fieldsBytes[i].length);
            startIndex += fieldsBytes[i].length;
        }
    }

    private static void packClassName() {
        System.arraycopy(Util.convertIntToBytes(wholeLength), 0, result, 1, 4);
        System.arraycopy(classNameBytes, 0, result, 5, classNameBytes.length);
    }

    private static int getLength() {
        int length = 1 + 4 + 1 + classNameBytes.length;
        for (int i = 0; i < fieldsBytes.length; i++) {
            length += fieldsBytes[i].length;
        }
        return length;
    }
}
