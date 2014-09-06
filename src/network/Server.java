package network;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 02.09.14
 */
import model.Cell;
import model.Game;
import model.Player;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    ServerSocket serverSocket;
    Socket connection; // connection-to-client
    ObjectOutputStream output;
    ObjectInputStream input;
    boolean isClientDisconnected;

    public void run() {
        try {
            serverSocket = new ServerSocket(6000, 100);
        } catch (IOException e) {
            System.err.println("Invalid port number");
        }
        while (true) {
            try {
                waitForConnection();
                getIOStreams();
                Game.seaBattle.setPlayerRemote(receivePlayer());
                keepConnectionAlive();
            } finally {
                closeConnection();
            }
        }
    }

    public void closeConnection() {
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void waitForConnection() {
        System.out.println("Server is ready to accept connections");
        try {
            connection = serverSocket.accept(); // code will stop here until a connection occurs
            connection.setKeepAlive(true);
            System.out.println("Connection established with the client");
        } catch (EOFException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getIOStreams() {
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush(); // send header information to the client, which
            // contains info required to create the input stream
            // object
            input = new ObjectInputStream(connection.getInputStream());
            System.out.println("Server established I/O streams");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keepConnectionAlive() {
        while(!isClientDisconnected){

        }
    }

    public Cell receiveCell() {
        try {
            Cell cell = (Cell) input.readObject();
            System.out.println("Server received cell " + cell);
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
            System.out.println("Server sent cell " + cell);
        } catch (IOException e) {
            System.err.println("Error sending Cell object.");
        }
    }

    public Player receivePlayer(){
        try {
            Player p = (Player) input.readObject();
            System.out.println("Server received player " + p.getName());
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
            System.out.println("Server sent player " + p.getName());
        } catch (IOException e) {
            System.out.println(e);
            System.err.println("Error sending Player object");
        }
    }
}