package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;

public class Gestore extends Thread {
    List<Offerta> offerte;
    List<Richiesta> richieste;

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = new Socket("localhost", 1111);
                ServerSocket server = new ServerSocket(1111);
                socket = server.accept();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Richiesta richiesta = (Richiesta) input.readObject();
                inviaRichiesta(richiesta);
                offerte.add(riceviOfferte());
                inviaOfferte(offerte);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void inviaRichiesta(Richiesta richiesta) {
        try {
            @SuppressWarnings("all")
            Socket socket = new Socket("224.3.2.1", 2222);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(richiesta);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Offerta riceviOfferte() {
        try {
            Socket socket = new Socket("224.3.2.1", 3333);
            ServerSocket server = new ServerSocket(3333);
            socket = server.accept();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Offerta offerta = (Offerta) input.readObject();
            socket.setSoTimeout(10000);
            return offerta;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private void inviaOfferte(List<Offerta> offerte) {
        try {
            Socket socket = new Socket("localhost", 1111);
            ServerSocket server = new ServerSocket(1111);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            for (Offerta o: offerte) {
                oos.writeObject(o);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
       Gestore g = new Gestore();
       g.start(); 
    }
}