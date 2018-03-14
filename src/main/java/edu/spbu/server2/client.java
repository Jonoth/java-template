package edu.spbu.server2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class client {
    public static void main(String[] args) throws Exception{
        System.out.println("Hello!");
        Socket socket = new Socket("localhost",2357); //www.yahoo.com 80

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();

        String os = "GET /index.html\n";
        String info = "HTTP/1.1 200 OK\r\n" +
                "Server: MyServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + os.length() + "\r\n" +
                "Connection: keepalive\r\n\r\n";
        os = os + info;
        out.write(os.getBytes());
        String res;
        while ((res = in.readLine()) != null) {
            System.out.println(res);
        }
        out.close();
        in.close();
        socket.close();
    }
}
