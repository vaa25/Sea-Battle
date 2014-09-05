package model.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


class Client
{
	private Socket clientSocket;
	private String host;
	private int port;

	private BufferedReader userInput;
	private BufferedReader inputStream;
	private PrintWriter outputStream;
	String outputMsgToServer, inputMsgFromServer;

	Client(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void start()
	{
		try {
			clientSocket = new Socket(host, port);

			userInput = new BufferedReader(new InputStreamReader(System.in));
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

			while ((outputMsgToServer = userInput.readLine()) != null) {
				outputStream.println(outputMsgToServer);

				inputMsgFromServer = inputStream.readLine();
				System.out.println(inputMsgFromServer);
			}

			outputStream.close();
			inputStream.close();
			userInput.close();
			clientSocket.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		new Client("localhost", 2222).start();
	}
}