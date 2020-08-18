package Esercitazione1;

//import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ThreadedEchoServer {
    public static void main(String[] args) {
        try {
            @SuppressWarnings("all")
            ServerSocket s = new ServerSocket(8189);
            while (true) {
                Socket incoming = s.accept();
                new ThreadedEchoHandler(incoming).start();
            }
        } catch (Exception e) { System.err.println(e); }
    }
}

class ThreadedEchoHandler extends Thread {
    private Socket incoming;

    public ThreadedEchoHandler (Socket i) {
        incoming = i;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
            out.println("Hello! Enter BYE to exit.");
            boolean done = false;
            while (!done) {
                String line = in.readLine();
                if (line == null)
                    done = true;
                else {
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE"))
                        done = true;
                }
            }
            incoming.close();
        } catch (Exception e) { System.err.println(e); }
    }
}