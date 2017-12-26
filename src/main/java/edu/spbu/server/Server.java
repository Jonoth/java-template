package edu.spbu.server;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.net.Socket;

public class Server {
    public Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    public String fileName;

    public Server(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.fileName = "";
    }

    public void readInputStream() throws IOException {
        Scanner scan = new Scanner(inputStream);
        String str = scan.next();
        if (str.equals("GET")) {
            str = scan.next();
            fileName = str.substring(1, str.length());
            System.out.println(fileName);
        } else {
            System.out.println("Strange string input");
        }

        //System.out.println("Client has sent: " + str);
    }

    public void writeOutputStream() throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            String s = new String(Files.readAllBytes(Paths.get(fileName)));
            String response = "HTTP/1.1 200 OK\r\n" + "Content-Type:text/html\r\n\r\n" + s;
            outputStream.write(response.getBytes());
        } else {
            outputStream.write("<html><h2>404</h2></html>".getBytes());
            outputStream.flush();
        }


        /*String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type:text/html\r\n\r\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<p>Hi</p> <p style=\"color:red;\">Hello</p>\n" +
                "<p style=\"color:blue;\">World</p>\n" +
                "<p style=\"font-size:36px;\">Byby</p>\n" +
                "</body>\n" +
                "</html>\n\n";
        outputStream.write(response.getBytes());*/
    }

    /*public void writeOutputStream2() throws IOException {
        File file = new File("C:\\Users\\Темирлан\\IdeaProjects\\java-template", "MyFile.html");
        if (file.exists()) {
            String s = new String(Files.readAllBytes(Paths.get(fileName)));
            String response = "HTTP/1.1 200 OK\r\n" + "Content-Type:text/html\r\n\r\n" + s;
            outputStream.write(response.getBytes());
        } else {
            outputStream.write("<html><h2>404</h2></html>".getBytes());
            outputStream.flush();
        }
    }*/
}

