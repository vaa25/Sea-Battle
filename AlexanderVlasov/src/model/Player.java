package model;

import common.Coord;
import common.CurrentStatisticInterface;
import common.ModelInterface;
import common.ShootResult;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 25.08.2014
 * Time: 17:55
 *
 * @author Alexander Vlasov
 */
public class Player implements ModelInterface{
    final static boolean WON=true;
    final static boolean DONTWON =false;
    private String name;
    private Field myField;
    private Field enemyField;
    private int width,height;
    private CurrentStatistic currentStatistic;
    private Coord shootCoord;
    public Player(int width, int height, String name) {
        this.width = width;
        this.height = height;
        enemyField=new Field(width,height);
        currentStatistic=new CurrentStatistic();
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setMyField(Field myField) {
        this.myField = myField;
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
    public ShootResult getShootResult(Coord coord){
        Cell cell = myField.getCell(coord);
        if (cell.getShip()!=null){
            if (cell.isShoot()){
                if (cell.getShip().isAlive())return ShootResult.HURT;
                else return ShootResult.KILLED;
            }
            else return ShootResult.SHIP;
        }else{
            if (cell.isShoot())return ShootResult.MISSED;
            else return ShootResult.CLEAN;
        }
    }

    @Override
    public int[][] getEnemyField() {
        return getField(enemyField.getField());
    }
    public boolean isEnemyLoose(){
        return enemyField.getKilled()==myField.getShipSize();
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
            case HURT:
                setHurt();
                break;
            case KILLED:
                setHurt();
                enemyField.addKilled();
                enemyField.place(constructKilledShip());
                break;
            case MISSED:
                enemyField.setShoot(shootCoord);
        }

    }
    private void setHurt(){
        Ship ship=new Ship(1);
        ship.setCoords(shootCoord);
        enemyField.place(ship);  // устанавливает временный корабль-маркер
        enemyField.setShoot(shootCoord);
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
            if (y<height-1&&field[x][y+1].getShip()!=null)searchWrecks(coords, new Coord(x,y+1));
        }
    }

    public ShootResult shoot(Coord coord){
        myField.shoot(coord);
        return getShootResult(coord);
    }
    public void printEnemy(){
        enemyField.printField();
    }
    public void printMy(){
        myField.printField();
    }
    public boolean isAlreadyShooted(Coord coord){
        return enemyField.getCell(coord).isShoot();
    }
    public static void main(String[] args) throws InterruptedException {
        Player player=new Player(10,10, "Player");
        Field my=new Field(10,10);
        player.setMyField(my);
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
        System.out.println("player map:");
        player.printMy();
        my.printField();

        Player player2=new Player(10,10,"Player2");
        Field enemy=new Field(10,10);
        player2.setMyField(enemy);
        List<Ship>ships2=new ArrayList<>();
        ships2.add(new Ship(4));
        ships2.add(new Ship(3));
        ships2.add(new Ship(3));
        ships2.add(new Ship(2));
        ships2.add(new Ship(2));
        ships2.add(new Ship(2));
        ships2.add(new Ship(1));
        ships2.add(new Ship(1));
        ships2.add(new Ship(1));
        ships2.add(new Ship(1));
        enemy.setRandom(ships2);
        System.out.println("player2 map:");
        player2.printMy();

//        Field myUnknown=new Field(10,10);
//        myUnknown.printField();
//        System.out.println("myUnknown");

//        Field enemyUnknown=new Field(10,10);
//        enemyUnknown.printField();
//        System.out.println("enemyUnknown");

//        ShootResult shootResult;
        Player shooting=player;
        Player shooted=player2;

        while(turn(shooting,shooted)==DONTWON){
            Player temp=shooted;
            shooted=shooting;
            shooting=temp;
        };


    }
    private static boolean turn(Player shooting, Player shooted){
        ShootResult shootResult;

        do{
            do{
                Random random=new Random();
                Coord coord;
                do {
                    coord=new Coord(random.nextInt(10),random.nextInt(10));
                }while (shooting.isAlreadyShooted(coord));

                shootResult=shooted.shoot(coord);
                shooting.shootCoord=coord;
                shooting.setShootResult(shootResult);
                System.out.println(shooting.getName() + " now shoots at " + coord + " with result " + shootResult + ", overall killed " + shooting.enemyField.getKilled() + " enemy ships, enemy map:");
                shooting.printEnemy();
                System.out.println("in reality:");
                shooted.printMy();
            }
            while (shootResult==ShootResult.HURT);
            if (shootResult== ShootResult.KILLED){
                if (shooting.isEnemyLoose()){
                    System.out.println(shooting.getName()+" won");
                    return WON;
                }
            }else break;
        } while (true);
        return DONTWON;
    }

}
