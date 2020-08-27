package Esercitazioni_mie.IndexServer;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class IndexServer {
    private Map<File, InetAddress> data;
    private final int uPort = 3000;
    private final int tPort = 4000;
    private ServerSocket server;
    private DatagramSocket uSocket;

    public IndexServer() {
        data = Collections.synchronizedMap(new HashMap<File, InetAddress>());
        System.out.println("IndexServer in fase di avvio.");
        inizia();
    }

    private void inizia() {
        try {
            server = new ServerSocket(tPort);
            System.out.println(server);
            uSocket = new DatagramSocket(uPort);
        } catch (IOException e) { e.printStackTrace(); }
        new GestisciFile().start();
        new GestisciClient().start();
    }

    class GestisciFile extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    System.out.println("Attesa di un nuovo pacchetto...");
                    uSocket.receive(packet);

                    String msg = new String(packet.getData());
                    System.out.println(msg);
                    String[] parti = msg.split("#");
                    String[] keywords = parti[1].split(",");

                    File file = new File(parti[0], keywords, "");
                    System.out.format("Aggiungo il file  %s inviato da %s%n", parti[0], packet.getAddress());
                    data.put(file, packet.getAddress());
                } catch (IOException e)  { e.printStackTrace(); }
            }
        }
    }

    class GestisciClient extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Attesa nuova ricerca...");
                    Socket tSocket = server.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(tSocket.getInputStream()));

                    String msg = br.readLine();
                    System.out.println("Ricevi la ricerca: " + msg);

                    String[] parti = msg.split("#");
                    String[] keywords = parti[1].split(",");

                    System.out.format("Ricevo la ricerca con filename %s e chiavi %s%n", parti[0], parti[1]);

                    String ret = "";
                    synchronized (data) {
                        for (Entry<File, InetAddress> entry: data.entrySet()) {
                            File file = entry.getKey();

                            if (file.getFilename().equals(parti[0])) {
                                boolean trovato = true;
                                for (int i = 0; i < keywords.length; i++) {
                                    trovato = false;
                                    for (int j = 0; j < file.getKeywords().length; j++) {
                                        if (keywords[i].equals(file.getKeywords()[j])) {
                                            trovato = true;
                                            break;
                                        }
                                    }
                                }
                                if (trovato) ret += entry.getValue().toString();
                            }
                        }
                    }
                    System.out.println("Invio la risposta " + ret);
                    PrintWriter pw = new PrintWriter(tSocket.getOutputStream(), true);
                    pw.println(ret);
                    br.close(); pw.close(); tSocket.close();
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        IndexServer idexS = new IndexServer();
    }
}

