package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.util.Random;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Ente extends Thread {
    private static String GIUDICE_ADDRESS = "127.0.0.1";
    private static int GIUDICE_PORT = 2000, countOpere = 0;
    private int idEnte;

    public Ente(int idEnte) { this.idEnte = idEnte; }

    @Override
    public void run() {
        try {
            sleep(new Random().nextInt(60000));
            @SuppressWarnings("all")
            Socket socket = new Socket(GIUDICE_ADDRESS, GIUDICE_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Richiesta2 richiesta = new Richiesta2(idEnte, "Opera: " + (++countOpere), 100000);
            out.writeObject(richiesta);
            System.out.println("Inviata: " + richiesta);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Offerta2 offertaMigliore = (Offerta2) in.readObject();
            System.out.println("Ricevuta offerta migliore: " + offertaMigliore);
        } catch (IOException | ClassNotFoundException | InterruptedException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Ente(i).start();
        }
    }
}