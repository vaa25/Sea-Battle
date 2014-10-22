import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeka on 15.10.2014.
 */
public class ArrangeShips extends FieldFrame {

    private List<Ship> MyShips;
    private List<Integer> ArrangeShips;
    private JButton goButton;
    private static final int[] LENGTH_OF_SHIPS={1,1,1,1,2,2,2,3,3,4};

    public ArrangeShips(){

        MyShips=new ArrayList<Ship>();
        ArrangeShips=new ArrayList<Integer>();

        goButton=new JButton("Go");
        goButton.setEnabled(false);
        JPanel goButtonPanel=new JPanel();
        goButtonPanel.add(goButton);
        goButtonPanel.setLayout(new BoxLayout(goButtonPanel, BoxLayout.Y_AXIS));
        goButtonPanel.setPreferredSize(new Dimension(50,275));

        this.setName("Player2");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().add(goButtonPanel);
        this.setSize(375,275);
        pack();

        this.addA1Listener(new A1Listener());
        this.addA2Listener(new A2Listener());
        this.addA3Listener(new A3Listener());
        this.addA4Listener(new A4Listener());
        this.addA5Listener(new A5Listener());
        this.addA6Listener(new A6Listener());
        this.addA7Listener(new A7Listener());
        this.addA8Listener(new A8Listener());
        this.addA9Listener(new A9Listener());
        this.addA10Listener(new A10Listener());

        this.addB1Listener(new B1Listener());
        this.addB2Listener(new B2Listener());
        this.addB3Listener(new B3Listener());
        this.addB4Listener(new B4Listener());
        this.addB5Listener(new B5Listener());
        this.addB6Listener(new B6Listener());
        this.addB7Listener(new B7Listener());
        this.addB8Listener(new B8Listener());
        this.addB9Listener(new B9Listener());
        this.addB10Listener(new B10Listener());

        this.addC1Listener(new C1Listener());
        this.addC2Listener(new C2Listener());
        this.addC3Listener(new C3Listener());
        this.addC4Listener(new C4Listener());
        this.addC5Listener(new C5Listener());
        this.addC6Listener(new C6Listener());
        this.addC7Listener(new C7Listener());
        this.addC8Listener(new C8Listener());
        this.addC9Listener(new C9Listener());
        this.addC10Listener(new C10Listener());

        this.addD1Listener(new D1Listener());
        this.addD2Listener(new D2Listener());
        this.addD3Listener(new D3Listener());
        this.addD4Listener(new D4Listener());
        this.addD5Listener(new D5Listener());
        this.addD6Listener(new D6Listener());
        this.addD7Listener(new D7Listener());
        this.addD8Listener(new D8Listener());
        this.addD9Listener(new D9Listener());
        this.addD10Listener(new D10Listener());

        this.addE1Listener(new E1Listener());
        this.addE2Listener(new E2Listener());
        this.addE3Listener(new E3Listener());
        this.addE4Listener(new E4Listener());
        this.addE5Listener(new E5Listener());
        this.addE6Listener(new E6Listener());
        this.addE7Listener(new E7Listener());
        this.addE8Listener(new E8Listener());
        this.addE9Listener(new E9Listener());
        this.addE10Listener(new E10Listener());

        this.addF1Listener(new F1Listener());
        this.addF2Listener(new F2Listener());
        this.addF3Listener(new F3Listener());
        this.addF4Listener(new F4Listener());
        this.addF5Listener(new F5Listener());
        this.addF6Listener(new F6Listener());
        this.addF7Listener(new F7Listener());
        this.addF8Listener(new F8Listener());
        this.addF9Listener(new F9Listener());
        this.addF10Listener(new F10Listener());

        this.addG1Listener(new G1Listener());
        this.addG2Listener(new G2Listener());
        this.addG3Listener(new G3Listener());
        this.addG4Listener(new G4Listener());
        this.addG5Listener(new G5Listener());
        this.addG6Listener(new G6Listener());
        this.addG7Listener(new G7Listener());
        this.addG8Listener(new G8Listener());
        this.addG9Listener(new G9Listener());
        this.addG10Listener(new G10Listener());

        this.addH1Listener(new H1Listener());
        this.addH2Listener(new H2Listener());
        this.addH3Listener(new H3Listener());
        this.addH4Listener(new H4Listener());
        this.addH5Listener(new H5Listener());
        this.addH6Listener(new H6Listener());
        this.addH7Listener(new H7Listener());
        this.addH8Listener(new H8Listener());
        this.addH9Listener(new H9Listener());
        this.addH10Listener(new H10Listener());

        this.addI1Listener(new I1Listener());
        this.addI2Listener(new I2Listener());
        this.addI3Listener(new I3Listener());
        this.addI4Listener(new I4Listener());
        this.addI5Listener(new I5Listener());
        this.addI6Listener(new I6Listener());
        this.addI7Listener(new I7Listener());
        this.addI8Listener(new I8Listener());
        this.addI9Listener(new I9Listener());
        this.addI10Listener(new I10Listener());

        this.addJ1Listener(new J1Listener());
        this.addJ2Listener(new J2Listener());
        this.addJ3Listener(new J3Listener());
        this.addJ4Listener(new J4Listener());
        this.addJ5Listener(new J5Listener());
        this.addJ6Listener(new J6Listener());
        this.addJ7Listener(new J7Listener());
        this.addJ8Listener(new J8Listener());
        this.addJ9Listener(new J9Listener());
        this.addJ10Listener(new J10Listener());

    }

    //Listeners
    //A1
    private class A1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(0);
            a1.setBackground(Color.MAGENTA);
            a1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA1Listener(ActionListener a){
        a1.addActionListener(a);
    }
    //A2
    private class A2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(1);
            a2.setBackground(Color.MAGENTA);
            a2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA2Listener(ActionListener a){
        a2.addActionListener(a);
    }
    //A3
    private class A3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(2);
            a3.setBackground(Color.MAGENTA);
            a3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA3Listener(ActionListener a){
        a3.addActionListener(a);
    }
    //A4
    private class A4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(3);
            a4.setBackground(Color.MAGENTA);
            a4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA4Listener(ActionListener a){
        a4.addActionListener(a);
    }
    //A5
    private class A5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(4);
            a5.setBackground(Color.MAGENTA);
            a5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA5Listener(ActionListener a){
        a5.addActionListener(a);
    }
    //A6
    private class A6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(5);
            a6.setBackground(Color.MAGENTA);
            a6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA6Listener(ActionListener a){
        a6.addActionListener(a);
    }
    //A7
    private class A7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(6);
            a7.setBackground(Color.MAGENTA);
            a7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA7Listener(ActionListener a){
        a7.addActionListener(a);
    }
    //A8
    private class A8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(7);
            a8.setBackground(Color.MAGENTA);
            a8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA8Listener(ActionListener a){
        a8.addActionListener(a);
    }

    //A9
    private class A9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(8);
            a9.setBackground(Color.MAGENTA);
            a9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA9Listener(ActionListener a){
        a9.addActionListener(a);
    }

    //A10
    private class A10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(9);
            a10.setBackground(Color.MAGENTA);
            a10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addA10Listener(ActionListener a){
        a10.addActionListener(a);
    }

    //B1
    private class B1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(10);
            b1.setBackground(Color.MAGENTA);
            b1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB1Listener(ActionListener a){
        b1.addActionListener(a);
    }
    //B2
    private class B2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(11);
            b2.setBackground(Color.MAGENTA);
            b2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB2Listener(ActionListener a){
        b2.addActionListener(a);
    }
    //B3
    private class B3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(12);
            b3.setBackground(Color.MAGENTA);
            b3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB3Listener(ActionListener a){
        b3.addActionListener(a);
    }
    //B4
    private class B4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(13);
            b4.setBackground(Color.MAGENTA);
            b4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB4Listener(ActionListener a){
        b4.addActionListener(a);
    }
    //B5
    private class B5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(14);
            b5.setBackground(Color.MAGENTA);
            b5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB5Listener(ActionListener a){
        b5.addActionListener(a);
    }
    //B6
    private class B6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(15);
            b6.setBackground(Color.MAGENTA);
            b6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB6Listener(ActionListener a){
        b6.addActionListener(a);
    }
    //B7
    private class B7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(16);
            b7.setBackground(Color.MAGENTA);
            b7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB7Listener(ActionListener a){
        b7.addActionListener(a);
    }
    //B8
    private class B8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(17);
            b8.setBackground(Color.MAGENTA);
            b8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB8Listener(ActionListener a){
        b8.addActionListener(a);
    }
    //B9
    private class B9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(18);
            b9.setBackground(Color.MAGENTA);
            b9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB9Listener(ActionListener a){
        b9.addActionListener(a);
    }
    //B10
    private class B10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(19);
            b10.setBackground(Color.MAGENTA);
            b10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addB10Listener(ActionListener a){
        b10.addActionListener(a);
    }
    //C1
    private class C1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(20);
            c1.setBackground(Color.MAGENTA);
            c1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC1Listener(ActionListener a){
        c1.addActionListener(a);
    }
    //C2
    private class C2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(21);
            c2.setBackground(Color.MAGENTA);
            c2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC2Listener(ActionListener a){
        c2.addActionListener(a);
    }
    //C3
    private class C3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(22);
            c3.setBackground(Color.MAGENTA);
            c3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC3Listener(ActionListener a){
        c3.addActionListener(a);
    }
    //C4
    private class C4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(23);
            c4.setBackground(Color.MAGENTA);
            c4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC4Listener(ActionListener a){
        c4.addActionListener(a);
    }
    //C5
    private class C5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(24);
            c5.setBackground(Color.MAGENTA);
            c5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC5Listener(ActionListener a){
        c5.addActionListener(a);
    }
    //C6
    private class C6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(25);
            c6.setBackground(Color.MAGENTA);
            c6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC6Listener(ActionListener a){
        c6.addActionListener(a);
    }
    //C7
    private class C7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(26);
            c7.setBackground(Color.MAGENTA);
            c7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC7Listener(ActionListener a){
        c7.addActionListener(a);
    }
    //C8
    private class C8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(27);
            c8.setBackground(Color.MAGENTA);
            c8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC8Listener(ActionListener a){
       c8.addActionListener(a);
    }
    //C9
    private class C9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(28);
            c9.setBackground(Color.MAGENTA);
            c9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC9Listener(ActionListener a){
        c9.addActionListener(a);
    }
    //C10
    private class C10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(29);
            c10.setBackground(Color.MAGENTA);
            c10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addC10Listener(ActionListener a){
        c10.addActionListener(a);
    }
    //D1
    private class D1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(30);
            d1.setBackground(Color.MAGENTA);
            d1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD1Listener(ActionListener a){
        d1.addActionListener(a);
    }
    //D2
    private class D2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(31);
            d2.setBackground(Color.MAGENTA);
            d2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD2Listener(ActionListener a){
        d2.addActionListener(a);
    }
    //D3
    private class D3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(32);
            d3.setBackground(Color.MAGENTA);
            d3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD3Listener(ActionListener a){
        d3.addActionListener(a);
    }
    //D4
    private class D4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(33);
            d4.setBackground(Color.MAGENTA);
            d4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD4Listener(ActionListener a){
        d4.addActionListener(a);
    }
    //D5
    private class D5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(34);
            d5.setBackground(Color.MAGENTA);
            d5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD5Listener(ActionListener a){
        d5.addActionListener(a);
    }
    //D6
    private class D6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(35);
            d6.setBackground(Color.MAGENTA);
            d6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD6Listener(ActionListener a){
        d6.addActionListener(a);
    }
    //D7
    private class D7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(36);
            d7.setBackground(Color.MAGENTA);
            d7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD7Listener(ActionListener a){
        d7.addActionListener(a);
    }
    //D8
    private class D8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(37);
            d8.setBackground(Color.MAGENTA);
            d8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD8Listener(ActionListener a){
        d8.addActionListener(a);
    }
    //D9
    private class D9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(38);
            d9.setBackground(Color.MAGENTA);
            d9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD9Listener(ActionListener a){
        d9.addActionListener(a);
    }
    //D10
    private class D10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(39);
            d10.setBackground(Color.MAGENTA);
            d10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addD10Listener(ActionListener a){
        d10.addActionListener(a);
    }
    //E1
    private class E1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(40);
            e1.setBackground(Color.MAGENTA);
            e1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE1Listener(ActionListener a){
        e1.addActionListener(a);
    }
    //E2
    private class E2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(41);
            e2.setBackground(Color.MAGENTA);
            e2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE2Listener(ActionListener a){
        e2.addActionListener(a);
    }
    //E3
    private class E3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(42);
            e3.setBackground(Color.MAGENTA);
            e3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE3Listener(ActionListener a){
        e3.addActionListener(a);
    }
    //E4
    private class E4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(43);
            e4.setBackground(Color.MAGENTA);
            e4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE4Listener(ActionListener a){
        e4.addActionListener(a);
    }
    //E5
    private class E5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(44);
            e5.setBackground(Color.MAGENTA);
            e5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE5Listener(ActionListener a){
        e5.addActionListener(a);
    }
    //E6
    private class E6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(45);
            e6.setBackground(Color.MAGENTA);
            e6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE6Listener(ActionListener a){
        e6.addActionListener(a);
    }
    //E7
    private class E7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(46);
            e7.setBackground(Color.MAGENTA);
            e7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE7Listener(ActionListener a){
        e7.addActionListener(a);
    }
    //E8
    private class E8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(47);
            e8.setBackground(Color.MAGENTA);
            e8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE8Listener(ActionListener a){
        e8.addActionListener(a);
    }
    //E9
    private class E9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(48);
            e9.setBackground(Color.MAGENTA);
            e9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE9Listener(ActionListener a){
        e9.addActionListener(a);
    }
    //E10
    private class E10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(49);
            e10.setBackground(Color.MAGENTA);
            e10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addE10Listener(ActionListener a){
        e10.addActionListener(a);
    }
    //F1
    private class F1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(50);
            f1.setBackground(Color.MAGENTA);
            f1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF1Listener(ActionListener a){
        f1.addActionListener(a);
    }
    //F2
    private class F2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(51);
            f2.setBackground(Color.MAGENTA);
            f2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF2Listener(ActionListener a){
        f2.addActionListener(a);
    }
    //F3
    private class F3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(52);
            f3.setBackground(Color.MAGENTA);
            f3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF3Listener(ActionListener a){
        f3.addActionListener(a);
    }
    //F4
    private class F4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(53);
            f4.setBackground(Color.MAGENTA);
            f4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF4Listener(ActionListener a){
        f4.addActionListener(a);
    }
    //F5
    private class F5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(54);
            f5.setBackground(Color.MAGENTA);
            f5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF5Listener(ActionListener a){
        f5.addActionListener(a);
    }
    //F6
    private class F6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(55);
            f6.setBackground(Color.MAGENTA);
            f6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF6Listener(ActionListener a){
        f6.addActionListener(a);
    }
    //F7
    private class F7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(56);
            f7.setBackground(Color.MAGENTA);
            f7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF7Listener(ActionListener a){
        f7.addActionListener(a);
    }
    //F8
    private class F8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(57);
            f8.setBackground(Color.MAGENTA);
            f8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF8Listener(ActionListener a){
        f8.addActionListener(a);
    }
    //F9
    private class F9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(58);
            f9.setBackground(Color.MAGENTA);
            f9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF9Listener(ActionListener a){
        f9.addActionListener(a);
    }
    //F10
    private class F10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(59);
            f10.setBackground(Color.MAGENTA);
            f10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addF10Listener(ActionListener a){
        f10.addActionListener(a);
    }
    //G1
    private class G1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(60);
            g1.setBackground(Color.MAGENTA);
            g1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG1Listener(ActionListener a){
        g1.addActionListener(a);
    }
    //G2
    private class G2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(61);
            g2.setBackground(Color.MAGENTA);
            g2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG2Listener(ActionListener a){
        g2.addActionListener(a);
    }
    //G3
    private class G3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(62);
            g3.setBackground(Color.MAGENTA);
            g3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG3Listener(ActionListener a){
        g3.addActionListener(a);
    }
    //E4
    private class G4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(63);
            g4.setBackground(Color.MAGENTA);
            g4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG4Listener(ActionListener a){
        g4.addActionListener(a);
    }
    //G5
    private class G5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(64);
            g5.setBackground(Color.MAGENTA);
            g5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG5Listener(ActionListener a){
        g5.addActionListener(a);
    }
    //G6
    private class G6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(65);
            g6.setBackground(Color.MAGENTA);
            g6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG6Listener(ActionListener a){
        g6.addActionListener(a);
    }
    //G7
    private class G7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(66);
            g7.setBackground(Color.MAGENTA);
            g7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG7Listener(ActionListener a){
        g7.addActionListener(a);
    }
    //G8
    private class G8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(67);
            g8.setBackground(Color.MAGENTA);
            g8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG8Listener(ActionListener a){
        g8.addActionListener(a);
    }
    //G9
    private class G9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(68);
            g9.setBackground(Color.MAGENTA);
            g9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG9Listener(ActionListener a){
        g9.addActionListener(a);
    }
    //G10
    private class G10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(69);
            g10.setBackground(Color.MAGENTA);
            g10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addG10Listener(ActionListener a){
        g10.addActionListener(a);
    }
    //H1
    private class H1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(70);
            h1.setBackground(Color.MAGENTA);
            h1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH1Listener(ActionListener a){
        h1.addActionListener(a);
    }
    //H2
    private class H2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(71);
            h2.setBackground(Color.MAGENTA);
            h2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH2Listener(ActionListener a){
        h2.addActionListener(a);
    }
    //H3
    private class H3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(72);
            h3.setBackground(Color.MAGENTA);
            h3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH3Listener(ActionListener a){
        h3.addActionListener(a);
    }
    //H4
    private class H4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(73);
            h4.setBackground(Color.MAGENTA);
            h4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH4Listener(ActionListener a){
        h4.addActionListener(a);
    }
    //H5
    private class H5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(74);
            h5.setBackground(Color.MAGENTA);
            h5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH5Listener(ActionListener a){
        h5.addActionListener(a);
    }
    //H6
    private class H6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(75);
            h6.setBackground(Color.MAGENTA);
            h6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH6Listener(ActionListener a){
        h6.addActionListener(a);
    }
    //H7
    private class H7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(76);
            h7.setBackground(Color.MAGENTA);
            h7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH7Listener(ActionListener a){
        h7.addActionListener(a);
    }
    //H8
    private class H8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(77);
            h8.setBackground(Color.MAGENTA);
            h8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH8Listener(ActionListener a){
        h8.addActionListener(a);
    }
    //H9
    private class H9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(78);
            h9.setBackground(Color.MAGENTA);
            h9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH9Listener(ActionListener a){
        h9.addActionListener(a);
    }
    //H10
    private class H10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(79);
            h10.setBackground(Color.MAGENTA);
            h10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addH10Listener(ActionListener a){
        h10.addActionListener(a);
    }
    //I1
    private class I1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(80);
            i1.setBackground(Color.MAGENTA);
            i1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI1Listener(ActionListener a){
        i1.addActionListener(a);
    }
    //I2
    private class I2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(81);
            i2.setBackground(Color.MAGENTA);
            i2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI2Listener(ActionListener a){
        i2.addActionListener(a);
    }
    //I3
    private class I3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(82);
            i3.setBackground(Color.MAGENTA);
            i3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI3Listener(ActionListener a){
        i3.addActionListener(a);
    }
    //I4
    private class I4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(83);
            i4.setBackground(Color.MAGENTA);
            i4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI4Listener(ActionListener a){
        i4.addActionListener(a);
    }
    //I5
    private class I5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(84);
            i5.setBackground(Color.MAGENTA);
            i5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI5Listener(ActionListener a){
        i5.addActionListener(a);
    }
    //I6
    private class I6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(85);
            i6.setBackground(Color.MAGENTA);
            i6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI6Listener(ActionListener a){
        i6.addActionListener(a);
    }
    //I7
    private class I7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(86);
            i7.setBackground(Color.MAGENTA);
            i7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI7Listener(ActionListener a){
        i7.addActionListener(a);
    }
    //I8
    private class I8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(87);
            i8.setBackground(Color.MAGENTA);
            i8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI8Listener(ActionListener a){
        i8.addActionListener(a);
    }
    //I9
    private class I9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(88);
            i9.setBackground(Color.MAGENTA);
            i9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI9Listener(ActionListener a){
        i9.addActionListener(a);
    }
    //I10
    private class I10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(89);
            i10.setBackground(Color.MAGENTA);
            i10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addI10Listener(ActionListener a){
        i10.addActionListener(a);
    }
    //J1
    private class J1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(90);
            j1.setBackground(Color.MAGENTA);
            j1.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ1Listener(ActionListener a){
        j1.addActionListener(a);
    }
    //J2
    private class J2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(91);
            j2.setBackground(Color.MAGENTA);
            j2.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ2Listener(ActionListener a){
        j2.addActionListener(a);
    }
    //J3
    private class J3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(92);
            j3.setBackground(Color.MAGENTA);
            j3.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ3Listener(ActionListener a){
        j3.addActionListener(a);
    }
    //J4
    private class J4Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(93);
            j4.setBackground(Color.MAGENTA);
            j4.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ4Listener(ActionListener a){
        j4.addActionListener(a);
    }
    //J5
    private class J5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(94);
            j5.setBackground(Color.MAGENTA);
            j5.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ5Listener(ActionListener a){
        j5.addActionListener(a);
    }
    //J6
    private class J6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(95);
            j6.setBackground(Color.MAGENTA);
            j6.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ6Listener(ActionListener a){
        j6.addActionListener(a);
    }
    //J7
    private class J7Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(96);
            j7.setBackground(Color.MAGENTA);
            j7.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ7Listener(ActionListener a){
        j7.addActionListener(a);
    }
    //J8
    private class J8Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(97);
            j8.setBackground(Color.MAGENTA);
            j8.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ8Listener(ActionListener a){
        j8.addActionListener(a);
    }
    //J9
    private class J9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(98);
            j9.setBackground(Color.MAGENTA);
            j9.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ9Listener(ActionListener a){
        j9.addActionListener(a);
    }
    //J10
    private class J10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addCell(99);
            j10.setBackground(Color.MAGENTA);
            j10.setEnabled(false);
            conditionForMakeShips(areCellsReady());
        }
    }

    public void addJ10Listener(ActionListener a){
        j10.addActionListener(a);
    }

    public void addGoButtonListener(ActionListener a){
        goButton.addActionListener(a);
    }

    public void messageDisplay(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    public void addCell(int coordinate){
        ArrangeShips.add(coordinate);
    }

    public void conditionForMakeShips(boolean condition){
        if(condition) {
            setEnabledAllButtons();
            for (int i=0;i<LENGTH_OF_SHIPS.length;i++){
                addShip(LENGTH_OF_SHIPS[i]);
            }
            messageDisplay("All ships are ready.");
            goButton.setEnabled(true);
        }
    }

    public void addShip(int length){
        Ship newShip=new Ship();
        do{
            int quantityOfCells=ArrangeShips.size()-1;
            Integer cell=(Integer) ArrangeShips.get(quantityOfCells);
            newShip.addCell(cell);
            ArrangeShips.remove(quantityOfCells);
            length--;
        }while (length>0);
        MyShips.add(newShip);
    }

    public boolean areCellsReady(){
        return ArrangeShips.size() == 20;
    }

    public void setEnabledAllButtons(){
        super.setEnabledAllButtons(false);
    }

    public List<Ship> getMyShips() {
        return MyShips;
    }
}
