package Esercitazioni_mie.garedappalto;

import java.io.*;
import java.net.*;

@SuppressWarnings("all")
public class Giudice {
    private static final int rPort = 2000;
    private final static String gAddress = "230.0.0.1";
    private final static int gPort = 3000;
    private final static int oPort = 4000;
    private final static int n = 10;
    
    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(gAddress);

            ServerSocket server = new ServerSocket(rPort);
            Socket accettaRichiesta = server.accept();
            ObjectInputStream ois = new ObjectInputStream(accettaRichiesta.getInputStream());
            Richiesta richiesta = (Richiesta) ois.readObject();
            System.out.println("Ricevuta: " + richiesta);

            inviaRichiestaAiPartecipanti(richiesta, group);

            Offerta oVincente = riceviOfferte();
            System.out.println("Offerta vincente: " + oVincente);

            ObjectOutputStream oos = new ObjectOutputStream(accettaRichiesta.getOutputStream());
            oos.writeObject(oVincente);
            inviaEsitoGara(oVincente, group);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void inviaRichiestaAiPartecipanti (Richiesta richiesta, InetAddress group) {
        try {
            MulticastSocket msocket = new MulticastSocket(gPort);
            byte buf[] = new byte[512];
            String r = richiesta.getDescrizione() + " - " + richiesta.getImportoMassimo();
            buf = r.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, gPort);
            msocket.send(packet);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static Offerta riceviOfferte() {
        Offerta oVincente = null;
        try {
            ServerSocket server = new ServerSocket(oPort);
            for (int i = 0; i < n; i++) {
                Socket partecipante = server.accept();
                ObjectInputStream ois = new ObjectInputStream(partecipante.getInputStream());
                Offerta offerta = (Offerta) ois.readObject();
                if (oVincente == null) oVincente = offerta;
                else if (offerta.getImportoRichiesto() <= oVincente.getImportoRichiesto() || (offerta.getImportoRichiesto() == oVincente.getImportoRichiesto() && offerta.getId() < oVincente.getId()))
                    oVincente = offerta;
                partecipante.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return oVincente;
    }

    private static void inviaEsitoGara(Offerta oVincente, InetAddress group) {
        try {
            MulticastSocket mSocket = new MulticastSocket(gPort);
            String message = oVincente.getId() + " - " + oVincente.getImportoRichiesto();
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, gPort);
            mSocket.send(packet);
        } catch (IOException e) { e.printStackTrace(); }
    }
}