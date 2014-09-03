package src.main.java.network;

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
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    ServerSocket serverSocket;
    Socket clientSocket;
    ObjectOutputStream output;
    ObjectInputStream input;
    String serverAddress = "127.0.0.1";

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public void run() {
        connect2Server();
        getIOStreams();
        processConnection();
        closeConnection();
    }

    public void connect2Server() {
        System.out.println("Trying to connect to the server");
        try {
            clientSocket = new Socket(serverAddress, 6000);
        } catch (UnknownHostException e) {
            System.err.println("Server unavailable");
            System.exit(1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getIOStreams() {
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Client established I/O Stream");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processConnection() {
        sendData("Connection established with the client");
        String inputMessage = "";
        new Thread() {
            Scanner sc = new Scanner(System.in);

            public void run() {
                String outputMessage = "";
                do {
                    outputMessage = sc.nextLine();
                    sendData(outputMessage);
                } while (!outputMessage.equals("QUIT"));
            }
        }.start();
        while (true) {

            try {
                inputMessage = (String) input.readObject();
                System.out.println(inputMessage);
            } catch (ClassNotFoundException e) {
                System.err.println("Object of an unknown type was recieved");
            } catch (EOFException e){
                System.out.println("Disconnected");
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void sendData(String s) {
        try {
            output.writeObject(s);
            output.flush();
            System.out.println("Client: " + s);
        }  catch (IOException e) {
            System.err.println("Error writting the message");
        }
    }

    public void closeConnection() {
        try {
            output.close();
            input.close();
            clientSocket.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}