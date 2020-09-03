package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;

public class Gestore extends Thread {
    
    @Override
    public void run() {
        try {
            while (true) {
                ServerSocket server = new ServerSocket(1111);
                System.out.println("In attesa di una richiesta...");
                Socket socket = server.accept();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Richiesta richiesta = (Richiesta) input.readObject();
                System.out.println("Richiesta ricevuta: " + richiesta.toString());
                server.close();
                inviaRichiesta(richiesta);
                Offerta offerta = riceviOfferte();
                inviaOfferte(offerta);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void inviaRichiesta(Richiesta richiesta) {
        try {
            @SuppressWarnings("all")
            Socket socket = new Socket("127.0.0.1", 2222);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(richiesta);
            System.out.println("Richiesta: " + richiesta.toString() + " inviata al Centro Benessere");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Offerta riceviOfferte() {
        try {
            //Socket socket = new Socket("127.0.0.1", 3333);
            ServerSocket server = new ServerSocket(3333);
            Socket socket = server.accept();
            System.out.println("In attesa di offerte");
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Offerta offerta = (Offerta) input.readObject();
            System.out.println("Offerta ricevuta dal CB: " + offerta.toString());
            System.out.println("Attesa di un minuto...");
            socket.setSoTimeout(10000);
            System.out.println("Attesa terminata");
            return offerta;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private void inviaOfferte(Offerta offerta) {
        try {
            Socket socket = new Socket("127.0.0.1", 1111);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(offerta);
            System.out.println("Offerte inviata al cliente");
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
       Gestore g = new Gestore();
       g.start(); 
    }
}