package Esercitazione3.MulticastTime;

import java.net.*;
import java.util.*;

public class MulticastTimeServer {
    public static void main(String[] args) {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(3575);
            while(true){
                byte[] buf = new byte[256];
                String dString = new Date().toString();
                buf = dString.getBytes();
                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 3575);
                socket.send(packet);
                System.out.println("Broadcasting: " + dString);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            socket.close();
        }
    }
}