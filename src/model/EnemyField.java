package model;


import common.Coord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:26
 *
 * @author Alexander Vlasov
 */
public class EnemyField extends Field {
    private List<Ship> reconstructedShips;
    private List<Coord> wrecks;

    public EnemyField(int width, int height) {
        super(width, height);
        reconstructedShips = new ArrayList<>();
        wrecks = new ArrayList<>();

    }

    public Ship setKilled(Coord coord) {
        setHurt(coord);
        addKilled();
        Ship killedShip = reconstructKilledShip(coord);
        place(killedShip);
        reconstructedShips.add(killedShip);
        return killedShip;
    }

    public void setHurt(Coord coord) {
        setShoot(coord);
        wrecks.add(coord);
        getCell(coord).setHurt(true);
    }

    /**
     * восстанавливает убитый корабль по обломкам
     *
     * @param shootCoord координата последнего удара
     *
     * @return
     */
    public Ship reconstructKilledShip(Coord shootCoord) {
        Set<Coord> wrecks = new HashSet<>();
        searchWrecks(wrecks, shootCoord);
        Ship ship = new Ship(wrecks.size());
        Coord[] coords = ship.getShipCoords();
        int i = 0;
        for (Coord wreck : wrecks) {
            coords[i++] = wreck;
        }
        reconstructedShips.add(ship);
        return ship;
    }

    /**
     * находит цепочку рядом стоящих обломков и удаляет их из общего списка обломков
     *
     * @param coords начальная точка поиска
     * @param coord  set найденных координат обломков
     */
    private void searchWrecks(Set<Coord> coords, Coord coord) {
        if (coords.add(coord)) {
            int x = coord.getX();
            int y = coord.getY();
            Coord newCoord;
            if (x > 0 && (wrecks.remove(newCoord = coord.getLeft()))) searchWrecks(coords, newCoord);
            if (y > 0 && (wrecks.remove(newCoord = coord.getUp()))) searchWrecks(coords, newCoord);
            if (x < width - 1 && (wrecks.remove(newCoord = coord.getRight()))) searchWrecks(coords, newCoord);
            if (y < height - 1 && (wrecks.remove(newCoord = coord.getDown()))) searchWrecks(coords, newCoord);
        }
    }


}
