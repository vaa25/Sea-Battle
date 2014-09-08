package network;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 02.09.14
 */

import model.Cell;
import model.Game;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
    Socket clientSocket;
    ObjectOutputStream output;
    ObjectInputStream input;
    String serverAddress = "127.0.0.1";
    boolean isServerDisconnected;

    public void run() {
        connect2Server();
        getIOStreams();
        keepConnectionAlive();
        closeConnection();
    }

    public void connect2Server() {
        System.out.println("Trying to connect to the server");
        try {
            clientSocket = new Socket(serverAddress, 6000);
        } catch (UnknownHostException e) {
            System.err.println("Server unavailable");
            System.exit(1);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void closeConnection() {
        try {
            output.close();
            input.close();
            clientSocket.close();
        }  catch (IOException e) { e.printStackTrace(); }
    }

    public void getIOStreams() {
        synchronized (Game.seaBattle){
            try {
                output = new ObjectOutputStream(clientSocket.getOutputStream());
                output.flush();
                input = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("Client established I/O Stream");
                Game.seaBattle.notify();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void keepConnectionAlive() {
        while(!isServerDisconnected){

        }
    }

    public Cell receiveCell() {
        try {
            Cell cell = (Cell) input.readObject();
            System.out.println("Client received cell " + cell);
            return cell;
        } catch (ClassNotFoundException e) {
            System.err.println("Object of an unknown type was received");
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public void sendCell(Cell cell){
        try {
            output.writeObject(cell);
            output.flush();
            System.out.println("Client sent cell " + cell);
        } catch (IOException e) {
            System.err.println("Error sending Cell object.");
        }
    }

    public Player receivePlayer(){
        try {
            Player p = (Player) input.readObject();
            System.out.println("Client received player " + p.getName());
            return p;
        } catch (ClassNotFoundException e) {
            System.err.println("Object of an unknown type was received");
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public void sendPlayer(Player p){
        try {
            output.writeObject(p);
            output.flush();
            System.out.println("Client sent player " + p.getName());
        } catch (IOException e) {
            System.out.println(e);
            System.err.println("Error sending Player object.");
        }
    }
}