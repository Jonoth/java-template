package edu.spbu.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter IP & port: ");
        Scanner scan = new Scanner(System.in);
        String ip = scan.next();
        int port = scan.nextInt();
        Socket clientSocket = new Socket(InetAddress.getByName(ip), port);
        Client client = new Client(clientSocket);
        client.writeOutputStream();
        client.readInputStream();
    }
}
