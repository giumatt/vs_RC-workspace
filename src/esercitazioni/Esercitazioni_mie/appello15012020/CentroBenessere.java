package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Random;

public class CentroBenessere {
    public static void main(String[] args) {
        try {
            //Socket socket = new Socket("127.0.0.1", 2222);
            ServerSocket server = new ServerSocket(2222);
            System.out.println("In attesa di una richiesta");
            Socket socket = server.accept();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Richiesta richiesta = (Richiesta) input.readObject();
            System.out.println("Richiesta: " + richiesta.toString() + " ricevuta da Gestore");
            Random rd = new Random();
            double numero = rd.nextDouble();
            if (numero > 0.3) {
                inviaOfferta(richiesta);
            } else {
                Socket Osocket = new Socket("127.0.0.1", 3333);
                ObjectOutputStream oos = new ObjectOutputStream(Osocket.getOutputStream());
                Offerta offerta = new Offerta("Uliveto Principessa rifiuta la richiesta", -1);
                System.out.println("Offerta: " + offerta.toString() + " inviata al Gestore");
                oos.writeObject(offerta);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void inviaOfferta(Richiesta richiesta) {
        try {
            int persone = richiesta.getNumPersone();
            Random rd = new Random();
            int prezzo = rd.nextInt((150 - 50 + 1) + 50);
            Socket socket = new Socket("127.0.0.1", 3333);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Offerta offerta = new Offerta("Uliveto Principessa", (prezzo * persone));
            System.out.println("Offerta: " + offerta.toString() + " inviata al Gestore");
            oos.writeObject(offerta);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
