package model.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server
{
	private ServerSocket server;
	private Socket client;
	private int port;

	private BufferedReader inputStream;
	private PrintWriter outputStream;
	private String inputMsgFromClient, outputMsgToClient;

	Server(int port)
	{
		this.port = port;
	}

	public void start()
	{
		try {
			server = new ServerSocket(port);
			client = server.accept();
			System.out.println("Got client");

			inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outputStream = new PrintWriter(client.getOutputStream(), true);

			while ((inputMsgFromClient = inputStream.readLine()) != null) {
				System.out.println(inputMsgFromClient);
				outputMsgToClient = "Server: " + inputMsgFromClient;
				outputStream.println(outputMsgToClient);
			}

			outputStream.close();
			inputStream.close();
			client.close();
			server.close();

			System.out.println("Server closed");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException
	{
		new Server(2222).start();
	}
}