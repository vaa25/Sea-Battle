package networksExample;

import networksExample.client.ChatClient;
import networksExample.server.ChatServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Alexander Vlasov
 */
public class TestClientServer {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        new Thread(new ChatServer(1000)).start();
        Thread.sleep(1000);
        ChatClient client = new ChatClient(InetAddress.getLocalHost(), 1000);
    }
}
