package common;

/**
 * Представляет собой
 */
public interface ModelInterface {
    public static final int CLEAN=1;
    public static final int SHIP=2;
    public static final int SHOOTED=3;
    public static final int SHOOTEDSHIP=4;
    public int[][] getMyField();
    public int[][] getEnemyField();

}
