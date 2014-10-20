
import model.Field;

public class EntryPoint {
    public static void main(String[] args) {
        Field field = new Field();
        field.locateShip(field.getCell(1, 1), Field.Direction.HORIZONTAL, 4);
        System.out.println(field);

    }
}
