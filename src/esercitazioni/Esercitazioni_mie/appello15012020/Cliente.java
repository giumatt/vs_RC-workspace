package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;

public class Cliente {
    private static List<Offerta> offerte;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("gestore.dimes.unical.it", 1111);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Richiesta richiesta = new Richiesta("12/05/2020", 5);
            oos.writeObject(richiesta);

            ServerSocket server = new ServerSocket(1111);
            socket = server.accept();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Offerta offerta = (Offerta) input.readObject();
            offerte.add(offerta);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
