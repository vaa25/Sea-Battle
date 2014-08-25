package model.admin;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:38
 *
 * @author Alexander Vlasov
 */
public class Ship implements Comparable<Ship>{
//    int[][]profile={
//            {1,1,1,1},
//            {0,0,0,0},
//            {0,0,0,0},
//            {0,0,0,0}
//    };
    private final int size;
    private Direction direction;
    private Coord[]coords;
    private Coord coordLeftUp;


    public Ship(int size) {
        this.size = size;
        direction=Direction.Horizontal;
        coords=new Coord[size];
    }


    @Override
    public int compareTo(Ship o) {
        return size-o.size;
    }

    public static enum Direction{
        Vertical, Horizontal
    }


    public void place(Coord coordLeftUp){
        this.coordLeftUp=coordLeftUp;
        int x0=coordLeftUp.getX();
        int y0=coordLeftUp.getY();
        for (int i = 1; i < coords.length; i++) {
            if (direction==Direction.Horizontal)coords[i]=new Coord(x0+i,y0);
            else if (direction==Direction.Vertical)coords[i]=new Coord(x0,y0+i);
        }
    }
    /**
     * Координаты всего корабля, чтобы проверить не вышел ли за края карты
     * @return
     */
    public Coord[] getShipCoords() {
        return coords;
    }

    public void changeDirection(){
        if (direction==Direction.Horizontal)direction=Direction.Vertical;
        else direction=Direction.Horizontal;
        place(coordLeftUp);
    }

    /**
     * Место вокруг корабля и сам корабль, чтобы не столкнуться с другим кораблем
     * @return
     */
    public Coord[] getAroundCoords(){
        Coord[] res= new Coord[size*3+6];
        if (direction==Direction.Horizontal){
            res[0]=new Coord(coords[0].getX()-1,coords[0].getY()-1);
            res[1]=new Coord(coords[0].getX()-1,coords[0].getY());
            res[2]=new Coord(coords[0].getX()-1,coords[0].getY()+1);
            res[3]=new Coord(coords[size-1].getX()+1,coords[size-1].getY()-1);
            res[4]=new Coord(coords[size-1].getX()+1,coords[size-1].getY());
            res[5]=new Coord(coords[size-1].getX()+1,coords[size-1].getY()+1);

        }
        else{
            res[0]=new Coord(coords[0].getX()-1,coords[0].getY()-1);
            res[1]=new Coord(coords[0].getX(),coords[0].getY()-1);
            res[2]=new Coord(coords[0].getX()+1,coords[0].getY()-1);
            res[3]=new Coord(coords[size-1].getX()-1,coords[size-1].getY()+1);
            res[4]=new Coord(coords[size-1].getX(),coords[size-1].getY()+1);
            res[5]=new Coord(coords[size-1].getX()+1,coords[size-1].getY()+1);
        }
        for (int i = 0; i < size; i++) {
            res[6+i]=new Coord(coords[i].getX(),coords[i].getY());
        }
        return res;
    }
    public boolean isCrossing(Ship ship){
        for (Coord coord : coords) {
            for (Coord anotherCoords : ship.getAroundCoords()) {
                if (coord.equals(anotherCoords))return true;
            }
        }
        return false;
    }
}
