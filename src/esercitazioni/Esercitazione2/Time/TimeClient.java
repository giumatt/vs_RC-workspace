package Esercitazione2.Time;

import java.io.*;
import java.net.*;

public class TimeClient {
    public static void main(String[] args) {
        try {
            String hostname = "localhost";
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3575);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData());
            System.out.println("Response: " + received);
            socket.close();
        } catch (IOException e) { System.err.println(e); }
    } 
}