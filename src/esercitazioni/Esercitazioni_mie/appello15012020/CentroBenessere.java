package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Random;

public class CentroBenessere {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("224.3.2.1", 2222);
            ServerSocket server = new ServerSocket(2222);
            socket = server.accept();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Richiesta richiesta = (Richiesta) input.readObject();
            Random rd = new Random();
            double numero = rd.nextDouble();
            if (numero > 0.3) {
                inviaOfferta(richiesta);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void inviaOfferta(Richiesta richiesta) {
        try {
            int persone = richiesta.getNumPersone();
            Random rd = new Random();
            int prezzo = rd.nextInt((150 - 50 + 1) + 50);
            Socket socket = new Socket("224.3.2.1", 3333);
            ServerSocket server = new ServerSocket(3333);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Offerta offerta = new Offerta("Uliveto Principessa", (prezzo * persone));
            oos.writeObject(offerta);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
