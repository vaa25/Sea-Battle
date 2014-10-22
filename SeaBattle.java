import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Jeka on 15.10.2014.
 */
public class SeaBattle {

    private String name="Player1";
    private ArrangeShips arrangeShips;
    private Field field;
    private final static String IP="192.168.0.100";

    ObjectInputStream in;
    ObjectOutputStream out;


    public SeaBattle(ArrangeShips arrangeShips, Field field){
        this.arrangeShips=arrangeShips;
        this.field=field;

        this.arrangeShips.addGoButtonListener(new GoButtonListener());

        this.field.addA1Listener(new A1());
        this.field.addA2Listener(new A2());
        this.field.addA3Listener(new A3());
        this.field.addA4Listener(new A4());
        this.field.addA5Listener(new A5());
        this.field.addA6Listener(new A6());
        this.field.addA7Listener(new A7());
        this.field.addA8Listener(new A8());
        this.field.addA9Listener(new A9());
        this.field.addA10Listener(new A10());

        this.field.addB1Listener(new B1());
        this.field.addB2Listener(new B2());
        this.field.addB3Listener(new B3());
        this.field.addB4Listener(new B4());
        this.field.addB5Listener(new B5());
        this.field.addB6Listener(new B6());
        this.field.addB7Listener(new B7());
        this.field.addB8Listener(new B8());
        this.field.addB9Listener(new B9());
        this.field.addB10Listener(new B10());

        this.field.addC1Listener(new C1());
        this.field.addC2Listener(new C2());
        this.field.addC3Listener(new C3());
        this.field.addC4Listener(new C4());
        this.field.addC5Listener(new C5());
        this.field.addC6Listener(new C6());
        this.field.addC7Listener(new C7());
        this.field.addC8Listener(new C8());
        this.field.addC9Listener(new C9());
        this.field.addC10Listener(new C10());

        this.field.addD1Listener(new D1());
        this.field.addD2Listener(new D2());
        this.field.addD3Listener(new D3());
        this.field.addD4Listener(new D4());
        this.field.addD5Listener(new D5());
        this.field.addD6Listener(new D6());
        this.field.addD7Listener(new D7());
        this.field.addD8Listener(new D8());
        this.field.addD9Listener(new D9());
        this.field.addD10Listener(new D10());

        this.field.addE1Listener(new E1());
        this.field.addE2Listener(new E2());
        this.field.addE3Listener(new E3());
        this.field.addE4Listener(new E4());
        this.field.addE5Listener(new E5());
        this.field.addE6Listener(new E6());
        this.field.addE7Listener(new E7());
        this.field.addE8Listener(new E8());
        this.field.addE9Listener(new E9());
        this.field.addE10Listener(new E10());

        this.field.addF1Listener(new F1());
        this.field.addF2Listener(new F2());
        this.field.addF3Listener(new F3());
        this.field.addF4Listener(new F4());
        this.field.addF5Listener(new F5());
        this.field.addF6Listener(new F6());
        this.field.addF7Listener(new F7());
        this.field.addF8Listener(new F8());
        this.field.addF9Listener(new F9());
        this.field.addF10Listener(new F10());

        this.field.addG1Listener(new G1());
        this.field.addG2Listener(new G2());
        this.field.addG3Listener(new G3());
        this.field.addG4Listener(new G4());
        this.field.addG5Listener(new G5());
        this.field.addG6Listener(new G6());
        this.field.addG7Listener(new G7());
        this.field.addG8Listener(new G8());
        this.field.addG9Listener(new G9());
        this.field.addG10Listener(new G10());

        this.field.addH1Listener(new H1());
        this.field.addH2Listener(new H2());
        this.field.addH3Listener(new H3());
        this.field.addH4Listener(new H4());
        this.field.addH5Listener(new H5());
        this.field.addH6Listener(new H6());
        this.field.addH7Listener(new H7());
        this.field.addH8Listener(new H8());
        this.field.addH9Listener(new H9());
        this.field.addH10Listener(new H10());

        this.field.addI1Listener(new I1());
        this.field.addI2Listener(new I2());
        this.field.addI3Listener(new I3());
        this.field.addI4Listener(new I4());
        this.field.addI5Listener(new I5());
        this.field.addI6Listener(new I6());
        this.field.addI7Listener(new I7());
        this.field.addI8Listener(new I8());
        this.field.addI9Listener(new I9());
        this.field.addI10Listener(new I10());

        this.field.addJ1Listener(new J1());
        this.field.addJ2Listener(new J2());
        this.field.addJ3Listener(new J3());
        this.field.addJ4Listener(new J4());
        this.field.addJ5Listener(new J5());
        this.field.addJ6Listener(new J6());
        this.field.addJ7Listener(new J7());
        this.field.addJ8Listener(new J8());
        this.field.addJ9Listener(new J9());
        this.field.addJ10Listener(new J10());

    }

    public class GoButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            List<Ship> ships=arrangeShips.getMyShips();
            field.setShips(ships);
            field.setVisible(true);
            field.arrangeShips();
            arrangeShips.setVisible(false);
            send(name,"ready");
        }
    }

    public class InputMyShips implements Runnable{

        Object object=null;

        @Override
        public void run() {
            try{
                while ((object=in.readObject())!=null){

                    //System.out.println("Обьекти прийшли");
                    String nameToCompare=(String)object;
                    String coordinate=(String)in.readObject();

                    if (!nameToCompare.equals(name)){

                        if(coordinate.length()==2){//Input coordinate of enemy shot

                            boolean result=field.opponentsShot(coordinate);

                            String resultInString=String.valueOf(result)+coordinate;
                            send(name,resultInString);
                            field.setEnabledAllButtons(true);
                            field.closePushedButtons();

                        }else if(coordinate.equals("ready")){

                            field.messageDisplay("Player2 is ready. You move.");
                            field.setEnabledAllButtons(true);

                        }else {//Input result of shot

                            field.processingResult(coordinate);
                            field.setEnabledAllButtons(false);
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void preparationOne(){
        try{
            Socket socket=new Socket(IP,4242);
            in=new ObjectInputStream(socket.getInputStream());
            out=new ObjectOutputStream(socket.getOutputStream());
            Thread threadForInputShips=new Thread(new InputMyShips());
            threadForInputShips.start();
        }catch (IOException e){
            arrangeShips.messageDisplay("Failed to connection to server");
        }
    }

    public static void main(String[] args) {
        ArrangeShips arrangeShips=new ArrangeShips();
        arrangeShips.setVisible(true);
        Field field=new Field();
        SeaBattle seaBattle=new SeaBattle(arrangeShips,field);
        seaBattle.preparationOne();
        field.setVisible(false);
    }


    private class A1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="00";
            JButton button=field.getButtons().get(0);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="01";
            JButton button=field.getButtons().get(1);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="02";
            JButton button=field.getButtons().get(2);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="03";
            JButton button=field.getButtons().get(3);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="04";
            JButton button=field.getButtons().get(4);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="05";
            JButton button=field.getButtons().get(5);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="06";
            JButton button=field.getButtons().get(6);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="07";
            JButton button=field.getButtons().get(7);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class A9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="08";
            JButton button=field.getButtons().get(8);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);//Item 3
        }
    }

    private class A10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="09";
            JButton button=field.getButtons().get(9);
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="10";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="11";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="12";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="13";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="14";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="15";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="16";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="17";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="18";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class B10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="19";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class C1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="20";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="21";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class C3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="22";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class C4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="23";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="24";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="25";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="26";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="27";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="28";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class C10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="29";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="30";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="31";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="32";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="33";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="34";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="35";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="36";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="37";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="38";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class D10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="39";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="40";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="41";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="42";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="43";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="44";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="45";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="46";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="47";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="48";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class E10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="49";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="50";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="51";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="52";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="53";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="54";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="55";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="56";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="57";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="58";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class F10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="59";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="60";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="61";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="62";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="63";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="64";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="65";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="66";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="67";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="68";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class G10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="69";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="70";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="71";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="72";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="73";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="74";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="75";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="76";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="77";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="78";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class H10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="79";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="80";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="81";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="82";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="83";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="84";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="85";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="86";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="87";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="88";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class I10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="89";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class J1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="90";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class J2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="91";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class J3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="92";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);//Item 2
            send(name,number);//Item 3
        }
    }

    private class J4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="93";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J5 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="94";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J6 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="95";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J7 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="96";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J8 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="97";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J9 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="98";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    private class J10 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String number="99";
            JButton button=field.getButtons().get(Integer.valueOf(number));
            field.addMyShot(button);
            field.setEnabledAllButtons(false);
            send(name,number);
        }
    }

    public void send(String objectOne, String objectTwo){
        try{
            out.writeObject(objectOne);
            out.writeObject(objectTwo);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
