package model.admin;

import common.Coord;
import common.CurrentStatisticInterface;
import common.ModelInterface;
import common.ShootResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Coord shootCoord;
    public main(int width, int height) {
        this.width = width;
        this.height = height;
        enemyField=new Field(width,height);
        currentStatistic=new CurrentStatistic();
    }

    @Override
    public int[][] getMyField() {
        return getField(myField.getField());

    }

    /**
     *
     * @param field - массив ячеек
     * @return массив
     */
    private int[][]getField(Cell[][] field){
        int[][] res=new int[width][height];
        for (int i = 0; i < field.length; i++) {
            Cell[] collumn = field[i];
            for (int j = 0; j < collumn.length; j++) {
                Cell cell = collumn[j];
                if (cell.getShip()!=null){
                    if (cell.isShoot()){
                        if (cell.getShip().isAlive())res[i][j]= SHOOTEDSHIP;
                        else res[i][j]=KILLEDSHIP;
                    }
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

    @Override
    public CurrentStatisticInterface getCurrentStatistic() {
        return currentStatistic;
    }

    /**
     * помечает вражеское поле в соответствии с сообщением от оппонента о результатах выстрела
     * @param shootResult сообщение
     */
    public void setShootResult(ShootResult shootResult){
        switch (shootResult){
            case KILLED:
                enemyField.addKilled();
                enemyField.place(constructKilledShip());
            case HURT:
                enemyField.place(new Ship(1));  // устанавливает временный корабль-маркер
            case MISSED:
                enemyField.setShoot(shootCoord);
        }

    }

    /**
     * восстанавливает убитый корабль по временным кораблям-маркерам
     * @return
     */
    private Ship constructKilledShip(){
        Set<Coord>wrecks=new HashSet<>();
        searchWrecks(wrecks,shootCoord);
        Ship ship= new Ship(wrecks.size());
        Coord[]coords=ship.getShipCoords();
        int i=0;
        for (Coord wreck : wrecks) {
            coords[i++]=wreck;
        }
        return ship;
    }

    /**
     * находит цепочку рядом стоящих частей корабля
     * @param coords начальная точка поиска
     * @param coord set найденных координат
     */
    private void searchWrecks(Set<Coord>coords, Coord coord){
        if (coords.add(coord)){
            int x=coord.getX();
            int y=coord.getY();
            Cell[][]field=enemyField.getField();
            if (x>0&&field[x-1][y].getShip()!=null)searchWrecks(coords, new Coord(x-1,y));
            if (y>0&&field[x][y-1].getShip()!=null)searchWrecks(coords, new Coord(x,y-1));
            if (x<width-1&&field[x+1][y].getShip()!=null)searchWrecks(coords, new Coord(x+1,y));
            if (x<height-1&&field[x][y+1].getShip()!=null)searchWrecks(coords, new Coord(x,y+1));
        }
    }

    public static void main(String[] args) {
        Field my=new Field(10,10);
        List<Ship>ships=new ArrayList<>();
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        my.setRandom(ships);
        my.printField();
    }

}
