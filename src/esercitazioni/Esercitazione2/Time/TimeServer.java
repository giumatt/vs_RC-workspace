package Esercitazione2.Time;

import java.io.*;
import java.net.*;
import java.util.*;

public class TimeServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(3575);
            int n = 1;
            while (n <= 1) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String dString = new Date().toString();
                buf = dString.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                n++;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            socket.close();
        }
    }
}