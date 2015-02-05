package view.networks.serializators;

/**
 * @author Alexander Vlasov
 */
public class Util {
    /**
     * Разложение int в четыре байта. Используется как для данных.
     *
     * @param k int
     *
     * @return byte[4]
     */
    public static byte[] convertIntToBytes(int k) {
        byte[] res = new byte[4];
        res[0] = (byte) (k);
        res[1] = (byte) (k >> 8);
        res[2] = (byte) (k >> 16);
        res[3] = (byte) (k >> 24);
        return res;
    }

    /**
     * Длина в массиве указанная в массиве со смещением
     *
     * @param bytes массив
     * @param off   смещение
     *
     * @return длина
     */
    static int convertBytesToInt(byte[] bytes, int off) {
        byte[] lengthB = new byte[4];
        System.arraycopy(bytes, off, lengthB, 0, 4);
        return (Byte.toUnsignedInt(lengthB[3]) << 24) +
                (Byte.toUnsignedInt(lengthB[2]) << 16) +
                (Byte.toUnsignedInt(lengthB[1]) << 8) +
                Byte.toUnsignedInt(lengthB[0]);
    }

    /**
     * Извлекает длину массива преобразованного объекта
     *
     * @param bytes массив преобразованного объекта
     *
     * @return длина
     */

    public static int convertBytesToInt(byte[] bytes) {
        return convertBytesToInt(bytes, 0);
    }
}
