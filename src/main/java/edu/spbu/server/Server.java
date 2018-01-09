package edu.spbu.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;



public class Server {
    public static void main(String args[]) throws IOException
    {
        String line, output = null;
        String fileName = null;
        ServerSocket server = new ServerSocket(2357);



        System.out.println("Listening for connection on port 2357 ....");
        while (true) {
            try (Socket socket = server.accept()) {

                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);



                BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                line = request.readLine();

                System.out.println(line);


                Scanner scan = new Scanner(line);
                String str = scan.next();
                if (str.equals("GET")) {
                    str = scan.next();
                    fileName = str.substring(1, str.length());
                    System.out.println(fileName);
                }

                try {
                    if (fileName.equals("2357.html")) {

                        String httpResponse = "HTTP/1.1 200 OK\n" + "Content-Type: text/html\n\n" + new String(Files.readAllBytes(Paths.get("src/2357.html")));
                        socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));

                    } else {
                        String httpResponse = "HTTP/1.1 200 OK\n" + "Content-Type: text/html\n\n" + new String(Files.readAllBytes(Paths.get("src/Error.html")));
                        socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                    }
                } catch (NullPointerException e){}
            }
        }
    }
}