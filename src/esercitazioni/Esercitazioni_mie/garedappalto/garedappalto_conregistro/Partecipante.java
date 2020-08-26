package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;

public class Partecipante extends Thread {
    private int id;
    private final String GIUDICE_ADDRESS = "127.0.0.1";

    public Partecipante(int id) {
        this.id = id;
    }

    private void gestisciRichiesta(String[] richiestaParts) throws IOException {
        try {
            int idGara = Integer.parseInt(richiestaParts[1]);
            int importoMax = Integer.parseInt(richiestaParts[3].trim());
            sleep(new Random().nextInt(60000));

            Socket socket = new Socket(GIUDICE_ADDRESS, 4000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Offerta2 offerta = new Offerta2(this.id, idGara, new Random().nextInt(importoMax));
            out.writeObject(offerta);
            System.out.println("Inviata offerta: " + offerta);
            socket.close();
        } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        System.out.println("Avvio partecipante con ID: " + this.id);
        MulticastSocket mSocket = null;
        try {
            mSocket = new MulticastSocket(3000);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            mSocket.joinGroup(group);
            while(true) {
                byte[] buf = new byte[512];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                mSocket.receive(packet);
                String richiesta = new String(packet.getData());
                String[] richiestaParts = richiesta.split("-");
                System.out.println("Ricevuto pacchetto multicast: " + richiesta);
                if (richiestaParts[0].equals("RICHIESTA")) {
                    gestisciRichiesta(richiestaParts);
                } else System.out.println(richiesta);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
       int n = 10;
       for (int i = 0; i < n; i++) {
           new Partecipante(i).start();
       } 
    }
}