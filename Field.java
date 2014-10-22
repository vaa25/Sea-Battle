import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeka on 14.10.2014.
 */
public class Field extends FieldFrame{

    private List<Ship> MyShips;
    private List<JLabel> Labels;
    private List<JButton> MyShots;

    public Field() {
        MyShots=new ArrayList<JButton>();
        MyShips=new ArrayList<Ship>();

        //Labels for right panel
        Labels=new ArrayList<JLabel>();

        Dimension dimension=new Dimension(SIZE_OF_BUTTONS_AND_LABELS,SIZE_OF_BUTTONS_AND_LABELS);
        for (int i=0; i<100; i++){
            Labels.add(new JLabel("",SwingConstants.CENTER));
        }
        for (int i=0; i<100; i++){
            Labels.get(i).setPreferredSize(dimension);
            Labels.get(i).setOpaque(true);
            //Labels.get(i).setBackground(Color.blue);
        }

        JLabel aLabelRightPanel=new JLabel("a",SwingConstants.CENTER);
        JLabel bLabelRightPanel=new JLabel("b",SwingConstants.CENTER);
        JLabel cLabelRightPanel=new JLabel("c",SwingConstants.CENTER);
        JLabel dLabelRightPanel=new JLabel("d",SwingConstants.CENTER);
        JLabel eLabelRightPanel=new JLabel("e",SwingConstants.CENTER);
        JLabel fLabelRightPanel=new JLabel("f",SwingConstants.CENTER);
        JLabel gLabelRightPanel=new JLabel("g",SwingConstants.CENTER);
        JLabel hLabelRightPanel=new JLabel("h",SwingConstants.CENTER);
        JLabel iLabelRightPanel=new JLabel("i",SwingConstants.CENTER);
        JLabel jLabelRightPanel=new JLabel("j",SwingConstants.CENTER);

        JLabel emptyLabelRightPanel=new JLabel();
        JLabel oneLabelRightPanel=new JLabel("1",SwingConstants.CENTER);
        JLabel twoLabelRightPanel=new JLabel("2",SwingConstants.CENTER);
        JLabel threeLabelRightPanel=new JLabel("3",SwingConstants.CENTER);
        JLabel fourLabelRightPanel=new JLabel("4",SwingConstants.CENTER);
        JLabel fiveLabelRightPanel=new JLabel("5",SwingConstants.CENTER);
        JLabel sixLabelRightPanel=new JLabel("6",SwingConstants.CENTER);
        JLabel sevenLabelRightPanel=new JLabel("7",SwingConstants.CENTER);
        JLabel eightLabelRightPanel=new JLabel("8",SwingConstants.CENTER);
        JLabel nineLabelRightPanel=new JLabel("9",SwingConstants.CENTER);
        JLabel tenLabelRightPanel=new JLabel("10",SwingConstants.CENTER);

        //Panels
        JPanel rightPanel=new JPanel();
        rightPanel.setPreferredSize(new Dimension(275,275));
        rightPanel.setLayout(new GridLayout(11,11));

        //Add labels at the rightPanel
        //1th row
        rightPanel.add(emptyLabelRightPanel);
        rightPanel.add(oneLabelRightPanel);
        rightPanel.add(twoLabelRightPanel);
        rightPanel.add(threeLabelRightPanel);
        rightPanel.add(fourLabelRightPanel);
        rightPanel.add(fiveLabelRightPanel);
        rightPanel.add(sixLabelRightPanel);
        rightPanel.add(sevenLabelRightPanel);
        rightPanel.add(eightLabelRightPanel);
        rightPanel.add(nineLabelRightPanel);
        rightPanel.add(tenLabelRightPanel);

        //2th row
        rightPanel.add(aLabelRightPanel);
        for (int i=0; i<10; i++){
            rightPanel.add(Labels.get(i));
        }

        //3th row
        rightPanel.add(bLabelRightPanel);
        for (int i=10; i<20; i++){
            rightPanel.add(Labels.get(i));
        }

        //4th row
        rightPanel.add(cLabelRightPanel);
        for (int i=20; i<30; i++){
            rightPanel.add(Labels.get(i));
        }

        //5th row
        rightPanel.add(dLabelRightPanel);
        for (int i=30; i<40; i++){
            rightPanel.add(Labels.get(i));
        }

        //6th row
        rightPanel.add(eLabelRightPanel);
        for (int i=40; i<50; i++){
            rightPanel.add(Labels.get(i));
        }

        //7th row
        rightPanel.add(fLabelRightPanel);
        for (int i=50; i<60; i++){
            rightPanel.add(Labels.get(i));
        }

        //8th row
        rightPanel.add(gLabelRightPanel);
        for (int i=60; i<70; i++){
            rightPanel.add(Labels.get(i));
        }

        //9th row
        rightPanel.add(hLabelRightPanel);
        for (int i=70; i<80; i++){
            rightPanel.add(Labels.get(i));
        }

        //10th row
        rightPanel.add(iLabelRightPanel);
        for (int i=80; i<90; i++){
            rightPanel.add(Labels.get(i));
        }

        //11th row
        rightPanel.add(jLabelRightPanel);
        for (int i=90; i<100; i++){
            rightPanel.add(Labels.get(i));
        }


        //Add panels at the frame
        this.setTitle("Sea Battle");
        this.getContentPane().add(rightPanel);
        this.setSize(550,225);
        this.setResizable(false);
        pack();

    }

    public void addA1Listener(ActionListener a) {
        a1.addActionListener(a);
    }

    public void addA2Listener(ActionListener a) {
        a2.addActionListener(a);
    }

    public void addA3Listener(ActionListener a) {
        a3.addActionListener(a);
    }

    public void addA4Listener(ActionListener a) {
        a4.addActionListener(a);
    }

    public void addA5Listener(ActionListener a) {
        a5.addActionListener(a);
    }

    public void addA6Listener(ActionListener a) {
        a6.addActionListener(a);
    }

    public void addA7Listener(ActionListener a) {
        a7.addActionListener(a);
    }

    public void addA8Listener(ActionListener a) {
        a8.addActionListener(a);
    }

    public void addA9Listener(ActionListener a) {
        a9.addActionListener(a);
    }

    public void addA10Listener(ActionListener a) {
        a10.addActionListener(a);
    }

    public void addB1Listener(ActionListener a) {
        b1.addActionListener(a);
    }

    public void addB2Listener(ActionListener a) {
        b2.addActionListener(a);
    }

    public void addB3Listener(ActionListener a) {
        b3.addActionListener(a);
    }

    public void addB4Listener(ActionListener a) {
        b4.addActionListener(a);
    }

    public void addB5Listener(ActionListener a) {
        b5.addActionListener(a);
    }

    public void addB6Listener(ActionListener a) {
        b6.addActionListener(a);
    }

    public void addB7Listener(ActionListener a) {
        b7.addActionListener(a);
    }

    public void addB8Listener(ActionListener a) {
        b8.addActionListener(a);
    }

    public void addB9Listener(ActionListener a) {
        b9.addActionListener(a);
    }

    public void addB10Listener(ActionListener a) {
        b10.addActionListener(a);
    }


    public void addC1Listener(ActionListener a) {
        c1.addActionListener(a);
    }

    public void addC2Listener(ActionListener a) {
        c2.addActionListener(a);
    }

    public void addC3Listener(ActionListener a) {
        c3.addActionListener(a);
    }

    public void addC4Listener(ActionListener a) {
        c4.addActionListener(a);
    }

    public void addC5Listener(ActionListener a) {
        c5.addActionListener(a);
    }

    public void addC6Listener(ActionListener a) {
        c6.addActionListener(a);
    }

    public void addC7Listener(ActionListener a) {
        c7.addActionListener(a);
    }

    public void addC8Listener(ActionListener a) {
        c8.addActionListener(a);
    }

    public void addC9Listener(ActionListener a) {
        c9.addActionListener(a);
    }

    public void addC10Listener(ActionListener a) {
        c10.addActionListener(a);
    }

    public void addD1Listener(ActionListener a) {
        d1.addActionListener(a);
    }

    public void addD2Listener(ActionListener a) {
        d2.addActionListener(a);
    }

    public void addD3Listener(ActionListener a) {
        d3.addActionListener(a);
    }

    public void addD4Listener(ActionListener a) {
        d4.addActionListener(a);
    }

    public void addD5Listener(ActionListener a) {
        d5.addActionListener(a);
    }

    public void addD6Listener(ActionListener a) {
        d6.addActionListener(a);
    }

    public void addD7Listener(ActionListener a) {
        d7.addActionListener(a);
    }

    public void addD8Listener(ActionListener a) {
        d8.addActionListener(a);
    }

    public void addD9Listener(ActionListener a) {
        d9.addActionListener(a);
    }

    public void addD10Listener(ActionListener a) {
        d10.addActionListener(a);
    }

    public void addE1Listener(ActionListener a) {
        e1.addActionListener(a);
    }

    public void addE2Listener(ActionListener a) {
        e2.addActionListener(a);
    }

    public void addE3Listener(ActionListener a) {
        e3.addActionListener(a);
    }

    public void addE4Listener(ActionListener a) {
        e4.addActionListener(a);
    }

    public void addE5Listener(ActionListener a) {
        e5.addActionListener(a);
    }

    public void addE6Listener(ActionListener a) {
        e6.addActionListener(a);
    }

    public void addE7Listener(ActionListener a) {
        e7.addActionListener(a);
    }

    public void addE8Listener(ActionListener a) {
        e8.addActionListener(a);
    }

    public void addE9Listener(ActionListener a) {
        e9.addActionListener(a);
    }

    public void addE10Listener(ActionListener a) {
        e10.addActionListener(a);
    }

    public void addF1Listener(ActionListener a) {
        f1.addActionListener(a);
    }

    public void addF2Listener(ActionListener a) {
        f2.addActionListener(a);
    }

    public void addF3Listener(ActionListener a) {
        f3.addActionListener(a);
    }

    public void addF4Listener(ActionListener a) {
        f4.addActionListener(a);
    }

    public void addF5Listener(ActionListener a) {
        f5.addActionListener(a);
    }

    public void addF6Listener(ActionListener a) {
        f6.addActionListener(a);
    }

    public void addF7Listener(ActionListener a) {
        f7.addActionListener(a);
    }

    public void addF8Listener(ActionListener a) {
        f8.addActionListener(a);
    }

    public void addF9Listener(ActionListener a) {
        f9.addActionListener(a);
    }

    public void addF10Listener(ActionListener a) {
        f10.addActionListener(a);
    }

    public void addG1Listener(ActionListener a) {
        g1.addActionListener(a);
    }

    public void addG2Listener(ActionListener a) {
        g2.addActionListener(a);
    }

    public void addG3Listener(ActionListener a) {
            g3.addActionListener(a);
    }

    public void addG4Listener(ActionListener a) {
        g4.addActionListener(a);
    }

    public void addG5Listener(ActionListener a) {
        g5.addActionListener(a);
    }

    public void addG6Listener(ActionListener a) {
        g6.addActionListener(a);
    }

    public void addG7Listener(ActionListener a) {
        g7.addActionListener(a);
    }

    public void addG8Listener(ActionListener a) {
        g8.addActionListener(a);
    }

    public void addG9Listener(ActionListener a) {
        g9.addActionListener(a);
    }

    public void addG10Listener(ActionListener a) {
        g10.addActionListener(a);
    }

    public void addH1Listener(ActionListener a) {
        h1.addActionListener(a);
    }

    public void addH2Listener(ActionListener a) {
        h2.addActionListener(a);
    }

    public void addH3Listener(ActionListener a) {
        h3.addActionListener(a);
    }

    public void addH4Listener(ActionListener a) {
        h4.addActionListener(a);
    }

    public void addH5Listener(ActionListener a) {
        h5.addActionListener(a);
    }

    public void addH6Listener(ActionListener a) {
        h6.addActionListener(a);
    }

    public void addH7Listener(ActionListener a) {
        h7.addActionListener(a);
    }

    public void addH8Listener(ActionListener a) {
        h8.addActionListener(a);
    }

    public void addH9Listener(ActionListener a) {
        h9.addActionListener(a);
    }

    public void addH10Listener(ActionListener a) {
        h10.addActionListener(a);
    }

    public void addI1Listener(ActionListener a) {
        i1.addActionListener(a);
    }

    public void addI2Listener(ActionListener a) {
        i2.addActionListener(a);
    }

    public void addI3Listener(ActionListener a) {
        i3.addActionListener(a);
    }

    public void addI4Listener(ActionListener a) {
        i4.addActionListener(a);
    }

    public void addI5Listener(ActionListener a) {
        i5.addActionListener(a);
    }

    public void addI6Listener(ActionListener a) {
        i6.addActionListener(a);
    }

    public void addI7Listener(ActionListener a) {
        i7.addActionListener(a);
    }

    public void addI8Listener(ActionListener a) {
        i8.addActionListener(a);
    }

    public void addI9Listener(ActionListener a) {
        i9.addActionListener(a);
    }

    public void addI10Listener(ActionListener a) {
        i10.addActionListener(a);
    }

    public void addJ1Listener(ActionListener a) {
        j1.addActionListener(a);
    }

    public void addJ2Listener(ActionListener a) {
        j2.addActionListener(a);
    }

    public void addJ3Listener(ActionListener a) {
        j3.addActionListener(a);
    }

    public void addJ4Listener(ActionListener a) {
        j4.addActionListener(a);
    }

    public void addJ5Listener(ActionListener a) {
        j5.addActionListener(a);
    }

    public void addJ6Listener(ActionListener a) {
        j6.addActionListener(a);
    }

    public void addJ7Listener(ActionListener a) {
        j7.addActionListener(a);
    }

    public void addJ8Listener(ActionListener a) {
        j8.addActionListener(a);
    }

    public void addJ9Listener(ActionListener a) {
        j9.addActionListener(a);
    }

    public void addJ10Listener(ActionListener a) {
        j10.addActionListener(a);
    }

    public void setShips(List<Ship> Ships) {
        this.MyShips = Ships;
    }

    public void arrangeShips(){

        for (int n=0;n<MyShips.size();n++){
            Ship ship=MyShips.get(n);
            List<Integer> cells=ship.getCoordinates();
            for (Integer cell : cells) {
                Labels.get(cell).setText("x");
                Labels.get(cell).setBackground(Color.green);
            }
        }
    }

    public boolean opponentsShot(String shotInString){
        Integer shot=Integer.valueOf(shotInString);
        for(Ship ship:MyShips){
            List<Integer> cells=ship.getCoordinates();
            for (Integer cell:cells){
                if(shot==cell){
                    //messageDisplay("You hit!");
                    Labels.get(shot).setBackground(Color.RED);
                    return true;
                }else {
                    Labels.get(shot).setBackground(Color.BLUE);
                }
            }
        }
        return false;
    }

    public void messageDisplay(String message){
        JOptionPane.showMessageDialog(this, message);
    }


    public void addMyShot(JButton shot){
        MyShots.add(shot);
    }


    public void processingResult(String result){

        int lengthOfWord=result.length();

        char lastChar=result.charAt((lengthOfWord-1));
        char lastCharButOne=result.charAt((lengthOfWord-2));

        String trueOrFalse=result.substring(0,(lengthOfWord-2));

        String lastCharString=String.valueOf(lastChar);
        String lastCharButOneString=String.valueOf(lastCharButOne);
        String numberOfCellString=lastCharButOneString+lastCharString;

        int numberOfCell=Integer.valueOf(numberOfCellString);
        if(trueOrFalse.equals("true")){
            buttons.get(numberOfCell).setBackground(Color.red);
        }
        else {
            buttons.get(numberOfCell).setBackground(Color.blue);
        }

    }

    @Override
    public void setEnabledAllButtons(boolean condition) {
        for (int i=0; i<buttons.size();i++){
            buttons.get(i).setEnabled(condition);
        }
    }

    public void closePushedButtons(){
        if(!MyShots.isEmpty()){
            for(JButton b:MyShots){
                b.setEnabled(false);
            }
        }
    }
}
