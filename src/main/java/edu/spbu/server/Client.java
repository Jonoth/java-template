package edu.spbu.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket c = new Socket(InetAddress.getByName("localhost"), 2357); /*wwww.yahoo.com 80*/
        Client m = new Client(c);
        m.start();
    }
    private Socket c;
    private InputStream is;
    private OutputStream os;

    public Client(Socket c) throws IOException {
        this.c = c;
        this.is = c.getInputStream();
        this.os = c.getOutputStream();
    }

    public void start() throws IOException {
        writeOutputStream();
        readInputStream();
    }

    public void writeOutputStream() throws IOException {
        String s = "GET /2357.html HTTP/1.1\n" +
                "Host: www.yahoo.com\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\n" +
                "Upgrade-Insecure-Requests: 1\n\n";
        os.write(s.getBytes());
        os.flush();
    }

    public void readInputStream() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        while (s!=null){
            System.err.println(s);
            s = br.readLine();
        }
    }
}