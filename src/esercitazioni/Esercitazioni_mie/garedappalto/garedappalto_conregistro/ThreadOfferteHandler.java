package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.net.*;
import java.io.*;

public class ThreadOfferteHandler extends Thread {
    private final RegistroGare registro;
    private ServerSocket socket;
    private final static int oPort = 4000;

    public ThreadOfferteHandler(RegistroGare registro) {
        this.registro = registro;
    }

    public void run() {
        try {
            this.socket = new ServerSocket(oPort);
            while (true) {
                Socket partecipante = socket.accept();
                ObjectInputStream ois = new ObjectInputStream(partecipante.getInputStream());
                Offerta2 offerta = (Offerta2) ois.readObject();
                this.registro.aggiungiOfferta(offerta);
                partecipante.close();
            }
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }
}