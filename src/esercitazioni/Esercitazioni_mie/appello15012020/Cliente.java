package Esercitazioni_mie.appello15012020;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.List;

public class Cliente {
    private static List<Offerta> offerte;
    public static void main(String[] args) {
        try {
            //invio richiesta 1
            Socket socket = new Socket("127.0.0.1", 1111);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Richiesta richiesta = new Richiesta("12/05/2020", 5);
            System.out.println("Invio richiesta: " + richiesta.toString());
            oos.writeObject(richiesta);
            System.out.println("Richiesta 1 inviata");
            oos.flush(); oos.close(); socket.close();
            
            Thread.sleep(3000);

            //ricezione offerta
            ServerSocket server = new ServerSocket(1111);
            Socket Osocket = server.accept();
            ObjectInputStream input = new ObjectInputStream(Osocket.getInputStream());
            Offerta offerta = (Offerta) input.readObject();
            System.out.println("Offerte ricevute: " + offerte.toString());
            offerte.add(offerta);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
