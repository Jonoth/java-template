package edu.spbu.server2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class server {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(2357);

        while (true) {
            System.out.println("Listening for connection on port 2357 ...");
            Socket socket = server.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();

            String is;
            String name = "2357.html";

            while ((is = in.readLine()) != null) {
                System.out.println(is);
                if (is.length() == 0) break;
                String[] mis = is.split(" ");
                if (mis[0].equals("GET")) {
                    name = mis[1].substring(1, mis[1].length());
                    System.out.println(name);
                    if (!Objects.equals(name, "index.html")) {
                        name = "2357.html";
                    }
                }
            }

            File html = new File("C:\\Users\\Темирлан\\IdeaProjects\\java-template\\src", name);
            boolean a = html.exists();
            if (a) {
                String info = "HTTP/1.1 200 OK\r\n" +
                        "Server: MyServer\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + html.length() + "\r\n" +
                        "Connection: keepalive\r\n\r\n";
                out.write(info.getBytes());
                out.flush();

                FileInputStream file = new FileInputStream(html);
                byte[] buffer = new byte[file.available()];
                file.read(buffer, 0, buffer.length);
                out.write(buffer, 0, buffer.length);
                file.close();
            }
            else {
                String mistake = "<html><body><p> Something seems to be wrong </p></body></html>";
                String info = "HTTP/1.1 200 OK\r\n" +
                        "Server: MyServer\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + mistake.length() + "\r\n" +
                        "Connection: keepalive\r\n\r\n";
                mistake = info + mistake;
                out.write(mistake.getBytes());
                out.flush();
            }
            in.close();
            out.close();
        }
    }
}
