package edu.spbu.server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void writeOutputStream() throws IOException {
        String str = "GET /MyFile.html HTTP/1.1\r\n\r\n";
        //String str = "GET / HTTP/1.1\r\n\r\n";
        outputStream.write(str.getBytes());
        outputStream.write("".getBytes());
        outputStream.flush();

        /*System.out.println("Enter your text: ");
        String str;
        Scanner scanner = new Scanner(System.in);
        str = scanner.next();
        os.write(str.getBytes());
        os.flush();*/
    }

    public void readInputStream() throws IOException {
        Scanner scan = new Scanner(inputStream);
        String str = new String();
        while (scan.hasNextLine()){
            str = scan.nextLine();
            System.out.println(str);
        }

    }
}

