package network;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 02.09.14
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;
    Socket connection; // connection-to-client
    ObjectOutputStream output;
    ObjectInputStream input;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

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
                processConnection();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForConnection() {
        System.out.println("Server is ready to accept conenctions");
        try {
            connection = serverSocket.accept(); // code will stop here until a
            // connection occurs
            System.out.println("Conenction established with the client");
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

    public void processConnection() {
        sendData("Connection established with the server");
        String inputMessage = "";
        new Thread() {
            Scanner sc = new Scanner(System.in);

            public void run() {
                while (true) {
                    sendData(sc.nextLine());
                }
            }
        }.start();
        do {

            try {
                inputMessage = (String) input.readObject();
                System.out.println(inputMessage);
            } catch (ClassNotFoundException e) {
                System.err.println("Object of an unknown type was recieved");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (!inputMessage.equals("QUIT"));
    }

    public void sendData(String s) {
        try {
            output.writeObject(s);
            output.flush();
            System.out.println("Server: " + s);
        } catch (IOException e) {
            System.err.println("Error writting the message");
        }
    }
}