package Esercitazioni_mie.Intermediario;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Intermediario {
    public final static int rPort = 2345;
    private List<Venditore> venditori;

    public Intermediario(List<Venditore> venditori) { this.venditori = venditori; }

    public List<Venditore> getVenditori() { return venditori; }
    public static void main(String[] args) {
        
        Intermediario i = new Intermediario(new LinkedList<Venditore>());
        try {
            @SuppressWarnings("all")
            ServerSocket server = new ServerSocket(rPort);
            while (true) {
                Socket accettaRichiesta = server.accept();
                RichiestaHandler t = new RichiestaHandler(accettaRichiesta, i.getVenditori());
                t.start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    static class RispostaHandler extends Thread {
        private DatagramSocket socket;
        private RichiestaHandler handler;

        public RispostaHandler(DatagramSocket socketV, RichiestaHandler handler) {
            this.socket = socketV; this.handler = handler;
        }
        
        @Override
        public void run() {
            try {
                DatagramPacket packet;
                while (true) {
                    byte[] buf = new byte[512];
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String received = new String(packet.getData());
                    String[] parts = received.split(", ");
                    int idProdotto = Integer.parseInt(parts[0]);
                    int quantita = Integer.parseInt(parts[1]);
                    int prezzo = Integer.parseInt(parts[2]);
                    int idVenditore = Integer.parseInt(parts[3]);
                    this.handler.storeRistosta(new Risposta(prezzo, quantita, idVenditore, idProdotto));
                }
            } catch (Exception e) { this.socket.close(); System.out.println("Fine risposte dal venditore."); }
        }
    }

    static class RichiestaHandler extends Thread {
        private Socket socket;
        private List<Venditore> venditori;
        private List<Risposta> risposte;

        public RichiestaHandler(Socket s, List<Venditore> venditori) {
            this.socket = s;
            this.venditori = venditori;
            this.risposte = Collections.synchronizedList(new LinkedList<Risposta>());
        }

        public void storeRistosta(Risposta risposta) { this.risposte.add(risposta); }

        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Richiesta richiesta = (Richiesta) ois.readObject();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(richiesta); oos.flush();
                byte[] buf = bos.toByteArray();

                for (Venditore v: venditori) {
                    DatagramSocket socketV = new DatagramSocket();
                    socketV.setSoTimeout(60000);
                    InetAddress vAddress = InetAddress.getByName(v.getAddress());
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, vAddress, v.getPort());
                    socketV.send(packet);
                    new RispostaHandler(socketV, this).start();
                }
                Risposta minRisp = new Risposta(richiesta.getIdProdotto(), richiesta.getQuantita(), -1, -1);
                for (Risposta r: risposte) {
                    if (r.getPrezzo() < minRisp.getPrezzo())
                        minRisp = r;
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(minRisp);
                oos.flush(); socket.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}