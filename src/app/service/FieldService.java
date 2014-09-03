package app.service;

import app.model.Cell;
import app.model.Field;
import app.model.Ship;
import app.model.enums.ShipSize;
import app.model.enums.ShipStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class represents functionality of Field class
 */
public class FieldService {

    /**
     * Creating Ship list with quantity-size specification
     */
    public List<Ship> initiateShipList() {
        // loading array from enum ShipSize
        List<ShipSize> shipSizes = Arrays.asList(ShipSize.values());
        List<Ship> shipList = new ArrayList<>();
        Collections.reverse(shipSizes);
        for (int i = 0; i < shipSizes.size(); i++) {
            for (int j = 1; j <= i + 1; j++) {
                shipList.add(new Ship(shipSizes.get(i)));
            }
        }
        return shipList;
    }

    /** TODO: something wrong
     * Deploy ship to field shipList
     *
     * @param ship - target ship to be placed
     * @return true if success
     */
    public boolean deployShipToField(Field field, Ship ship) {
        if (!ship.getCells().equals(null) && !field.getShipList().equals(null)) {
            for (Ship scannedShip : field.getShipList()) {
                if (scannedShip.getShipSize() == ship.getShipSize() &&
                        scannedShip.getShipStatus().equals(ShipStatus.AVAILABLE)) {
                    if (isShipSpotIsFree(field, ship)) {
                        scannedShip = ship;
                        scannedShip.setShipStatus(ShipStatus.BUSY);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * Deploy ship to field shipList
     *
     * @param ship - target ship to be placed
     * @return true if success
     */
//    public boolean deleteShipFromField(Field field, Ship ship) {
//        List<Ship> shipList = field.getShipList();
//        for (Ship targetShip: shipList) {
//            if (targetShip.equals(ship)) {
//                int count = 0;
//                for (Cell c : targetShip.getCells()) {
//                    if (c.getCellState() == CellState.HIT) { count++; }
//                }
//                if (count == targetShip.getCells().length) {
//                    targetShip.setShipStatus(ShipStatus.AVAILABLE);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * verifying that cells, where ship to be placed, are free
     *
     * @return boolean
     */
    public boolean isShipSpotIsFree(Field field, Ship ship) {
        if (ship.getCells().length > 0) {
            Cell[] shipPlusAura = new ShipService().getCellsAroundShip(ship);
            for (Ship shipFromList : field.getShipList()) {
                for (Cell cellFromField : shipFromList.getCells()) {
                    for (Cell thisShipCell : shipPlusAura) {
                        // TODO: NullPointerException
                        if (cellFromField != null) {
                            if (cellFromField.equals(thisShipCell)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public List<Ship> getAvailableShipList(Field field) {
        List<Ship> list = new ArrayList<>();
        for (Ship ship : field.getShipList()) {
            if (ship.getShipStatus().equals(ShipStatus.AVAILABLE)) {
                list.add(ship);
            }
        }
        return list;
    }
}
