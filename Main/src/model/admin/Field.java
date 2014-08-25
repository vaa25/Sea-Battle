package model.admin;


import common.Coord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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




    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field=new Cell[width][height];
        ships=new ArrayList<>();
        createCells();

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

    public boolean canPlace(Ship ship){
        for (int i = 0; i < ships.size(); i++) {
            Ship alreadyPlaced = ships.get(i);
            if (ship.isCrossing(alreadyPlaced))return false;
        }
        if (!inBorders(ship))return false;
        return true;
    }

    public void place(Ship ship){
        ships.add(ship);
        for (Coord coord : ship.getShipCoords()) {
            field[coord.getX()][coord.getY()].setShip(ship);
        }
    }

    private boolean inBorders(Ship ship){
        for (Coord coord : ship.getShipCoords()) {
            if (coord.getX()<0||coord.getY()<0||coord.getX()>=width||coord.getY()>=height)return false;
        }
        return true;
    }
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

    public void unPlace(Ship ship){
        if (ships.remove(ship)){
            for (int i = 0; i < ship.getShipCoords().length; i++) {
                Coord coord = ship.getShipCoords()[i];
                field[coord.getX()][coord.getY()].setShip(null);
            }
        }
    }
    public boolean setRandom(List<Ship>ships){
        Collections.sort(ships);
        for (int i = 0; i < ships.size(); i++) {
            Ship ship=ships.get(i);

        }
        return false;
    }


}
