package model.admin;

import common.ModelInterface;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 25.08.2014
 * Time: 17:55
 *
 * @author Alexander Vlasov
 */
public class main implements ModelInterface{
    private Field myField;
    private Field enemyField;
    private int width,height;
    private CurrentStatistic currentStatistic;
    public main(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int[][] getMyField() {
        return getField(myField.getField());

    }
    private int[][]getField(Cell[][] field){
        int[][] res=new int[width][height];
        for (int i = 0; i < field.length; i++) {
            Cell[] collumn = field[i];
            for (int j = 0; j < collumn.length; j++) {
                Cell cell = collumn[j];
                if (cell.getShip()!=null){
                    if (cell.isShoot())res[i][j]= SHOOTEDSHIP;
                    else res[i][j]=SHIP;
                }else{
                    if (cell.isShoot())res[i][j]=SHOOTED;
                    else res[i][j]=CLEAN;
                }
            }

        }
        return res;
    }
    @Override
    public int[][] getEnemyField() {
        return getField(enemyField.getField());
    }
}
