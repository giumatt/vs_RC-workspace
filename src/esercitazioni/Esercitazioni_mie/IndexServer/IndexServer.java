package Esercitazioni_mie.IndexServer;

import java.io.IOException;
import java.net.*;
import java.util.*;

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
}

