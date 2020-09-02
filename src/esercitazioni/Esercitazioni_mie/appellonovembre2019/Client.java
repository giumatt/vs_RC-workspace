package Esercitazioni_mie.appellonovembre2019;

import java.io.ObjectOutputStream;
import java.net.*;

public class Client extends Thread {
    private final static int tPort = 1111;

    public static void main(String[] args) {
        try {
            Richiesta richiesta = new Richiesta("Smartphone XYZ", 5, 150);
            Socket tSocket = new Socket("server.dimes.it", tPort);
            ObjectOutputStream oos = new ObjectOutputStream(tSocket.getOutputStream());

            oos.writeObject(richiesta);
            oos.flush(); tSocket.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
