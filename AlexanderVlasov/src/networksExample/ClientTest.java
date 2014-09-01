package networksExample;


import networksExample.client.ChatClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTest {


    public static void main(String[] args) throws UnknownHostException, UnknownHostException {
        ChatClient client = new ChatClient(InetAddress.getLocalHost(), 1000);
    }

}
