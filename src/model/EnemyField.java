package model;


import common.Coord;

import java.util.*;

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

    public List<Ship> getReconstructedShips() {
        return reconstructedShips;
    }

    public Ship setKilled(Coord coord) {
        setHurt(coord);
        addKilled();
        Ship killedShip = reconstructKilledShip(coord);
        killedShip.kill();
        place(killedShip);
        reconstructedShips.add(killedShip);
        return killedShip;
    }

    public void setHurt(Coord coord) {
        setShooted(coord);
        wrecks.add(coord);
        getCell(coord).setHurted(true);
    }

    /**
     * восстанавливает убитый корабль по обломкам
     *
     * @param shootCoord координата последнего удара
     *
     * @return
     */
    private Ship reconstructKilledShip(Coord shootCoord) {
        Set<Coord> wrecks = new HashSet<>();
        searchWrecks(wrecks, shootCoord);
        Ship ship = new Ship(wrecks.size());
        Coord[] coords = new Coord[wrecks.size()];
        coords = wrecks.toArray(coords);
        Arrays.sort(coords);
        ship.setCoords(coords[0]);
        if (coords[0].getY() != coords[coords.length - 1].getY()) {
            ship.setOrientation(Orientation.Vertical);
        }
        return ship;
    }

    /**
     * находит цепочку рядом стоящих обломков и удаляет их из общего списка обломков
     *
     * @param coords set найденных координат цепочки обломков
     * @param coord  начальная точка поиска
     */
    private void searchWrecks(Set<Coord> coords, Coord coord) {
        if (coords.add(coord)) {
            Coord newCoord;
            if (coord.getX() > 0 && (wrecks.remove(newCoord = coord.getLeft()))) {
                searchWrecks(coords, newCoord);
            }
            if (coord.getY() > 0 && (wrecks.remove(newCoord = coord.getUp()))) {
                searchWrecks(coords, newCoord);
            }
            if (coord.getX() < width - 1 && (wrecks.remove(newCoord = coord.getRight()))) {
                searchWrecks(coords, newCoord);
            }
            if (coord.getY() < height - 1 && (wrecks.remove(newCoord = coord.getDown()))) {
                searchWrecks(coords, newCoord);
            }
        }
    }


}
