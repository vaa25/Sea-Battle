package model.admin;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:38
 *
 * @author Alexander Vlasov
 */
public class Ship {
//    int[][]profile={
//            {1,1,1,1},
//            {0,0,0,0},
//            {0,0,0,0},
//            {0,0,0,0}
//    };
    private final int size;
    private Direction direction;
    private Coord[]coords;

    public Ship(int size) {
        this.size = size;
        direction=Direction.Horizontal;
        coords=new Coord[size];
    }
    public static enum Direction{
        Vertical, Horizontal
    }

    /**
     * Координаты всего корабля, чтобы проверить не вышел ли за края карты
     * @param first
     * @return
     */
    public Coord[] place(Coord first){
        int x0=first.getX();
        int y0=first.getY();
        for (int i = 0; i < coords.length; i++) {
            if (direction==Direction.Horizontal)coords[i]=new Coord(x0+i,y0);
            else if (direction==Direction.Vertical)coords[i]=new Coord(x0,y0+i);
        }
        return coords;
    }

    public void roll(){
        if (direction==Direction.Horizontal)direction=Direction.Vertical;
        else direction=Direction.Horizontal;
    }

    /**
     * Место вокруг корабля и сам корабль, чтобы не столкнуться с другим кораблем
     * @return
     */
//    public Coord[]getAroundPlace(){
//
//    }
}
