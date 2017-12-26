package edu.spbu.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;

public class Server2 {

    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(2365);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();
                writeResponse("2357");
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            /*File file = new File (MyFile.html HTTP/1.1);
            String p = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));*/
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";

            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
   /* public void readInputStream() throws IOException {
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
    }*/
}