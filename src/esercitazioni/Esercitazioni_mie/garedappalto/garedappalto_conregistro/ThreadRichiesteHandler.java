package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.IOException;
import java.io.*;
import java.net.*;

public class ThreadRichiesteHandler extends Thread {
    private ServerSocket socket;
    private RegistroGare registro;
    private final static int rPort = 2000;

    public ThreadRichiesteHandler(RegistroGare registro) throws IOException {
        this.registro = registro;
        this.socket = new ServerSocket(rPort);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socketRichiesta = socket.accept();
                ObjectInputStream ois = new ObjectInputStream(socketRichiesta.getInputStream());
                Richiesta2 richiesta = (Richiesta2) ois.readObject();
                System.out.println("Ricevuta nuova richiesta: " + richiesta);

                //aggiungo richiesta di gara al registro
                int idGara = registro.addGara(socketRichiesta, richiesta);
                
                //avvio thread per gestire il timer sulle offerte per questa gara
                new ThreadTimeoutHandler(idGara, registro).start();
            } 
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }
}