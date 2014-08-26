package model.admin;


import common.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:26
 *
 * @author Alexander Vlasov
 */
public class Field  {
    private Cell[][] field;
    private List<Ship>ships;
    private final int width,height;
    private int killed;



    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field=new Cell[width][height];
        ships=new ArrayList<>();
        createCells();
        killed=0;

    }

    public void createCells(){
        for (int i = 0; i < field.length; i++) {
            Cell[] collumn = field[i];
            for (int j = 0; j < collumn.length; j++) {
                collumn[j]=new Cell(new Coord(i,j));

            }

        }
    }

    public Cell[][] getField() {
        return field;
    }

    /**
     * проверяет, можно ли расположить корабль с заданными координатами на поле с расставленными ранее кораблями
     * @param ship
     * @return
     */
    public boolean canPlace(Ship ship){
        for (int i = 0; i < ships.size(); i++) {
            Ship alreadyPlaced = ships.get(i);
            if (ship.isCrossing(alreadyPlaced))return false;
        }
        if (!inBorders(ship))return false;
        return true;
    }

    /**
     * Устанавливает в ячейки корабль
     * Удаляет временные корабли, установленные при восстановлении убитых кораблей, когда поле является вражьим
     * @param ship
     */
    public void place(Ship ship){
        ships.add(ship);
        for (Coord coord : ship.getShipCoords()) {
            Cell cell=field[coord.getX()][coord.getY()];
            Ship temp=cell.getShip();
            if (temp!=null)ships.remove(temp);
            cell.setShip(ship);
        }
    }

    /**
     * Проверяет, находится ли весь корабль в границах поля
     * @param ship
     * @return true, если находится
     */
    private boolean inBorders(Ship ship){
        for (Coord coord : ship.getShipCoords()) {
            if (coord.getX()<0||coord.getY()<0||coord.getX()>=width||coord.getY()>=height)return false;
        }
        return true;
    }

    /**
     * Ячейки вдали от кораблей
     * @return
     */
    private List<Coord>getFreeCoords(){
        List<Coord>res=new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                res.add(new Coord(i,j));
            }
        }
        for (Ship ship : ships) {
            for (int i = 0; i < ship.getAroundCoords().length; i++) {
                Coord coord = ship.getAroundCoords()[i];
                res.remove(coord);

            }
        }
        return res;
    }

    /**
     * Удаляет из ячеек корабль
     * @param ship
     */
    public void unPlace(Ship ship){
        if (ships.remove(ship)){
            for (int i = 0; i < ship.getShipCoords().length; i++) {
                Coord coord = ship.getShipCoords()[i];
                field[coord.getX()][coord.getY()].setShip(null);
            }
        }
    }

    /**
     * расставляет рандомно указанные корабли
     * @param ships
     */
    public void setRandom(List<Ship>ships){
//        Collections.sort(ships);
        while (!trySetRandom(ships));
    }
    public boolean trySetRandom(List<Ship>ships){

        for (int i = 0; i < ships.size(); i++) {
            Ship ship=ships.get(i);
            Random random=new Random();
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            ship.setCoords(new Coord(x, y));
            if (random.nextBoolean())ship.changeDirection();
            if (canPlace(ship))place(ship);
            else{
                ship.changeDirection();
                if (canPlace(ship))place(ship);
                else{
                    unPlace(ships);
                    return false;
                }
            }
        }
        return true;
    }

    public void setShoot(Coord coord){
        field[coord.getX()][coord.getY()].setShoot(true);
    }
    private void unPlace(List<Ship>ships){
        for (Ship ship : ships) {
            unPlace(ship);
        }
    }

    public void addKilled() {
        killed++;
    }

    public void printField(){
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.getShip()==null){
                    if (cell.isShoot())System.out.print(". ");
                    else System.out.print("  ");
                }
                else{
                    if (cell.isShoot())System.out.print("* ");
                    else System.out.print("O ");
                }
            }
            System.out.println();
        }
    }
}
