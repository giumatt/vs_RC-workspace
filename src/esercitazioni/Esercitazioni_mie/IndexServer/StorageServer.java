package Esercitazioni_mie.IndexServer;

import java.util.HashMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class StorageServer {
    private HashMap<String, File> data;
    private final int myPort = 2000;
    private final String indexServerAddress;
    private final int indexServerPort = 3000;
    private ServerSocket server;

    public StorageServer(String indexServerAddress) {
        this.indexServerAddress = indexServerAddress;
        data = new HashMap<String, File>();
        System.out.println("StorageServer in fase di avvio");
        inizia();
    }

    public void inizia() {
        try {
            server = new ServerSocket(myPort);
        } catch (IOException e) { e.printStackTrace(); }
        while (true) {
            memorizzaFile();
        }
    }

    private void memorizzaFile() {
        try {
            System.out.println("In attesa di memorizzare un file...");
            Socket tSocket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(tSocket.getInputStream());
            File file = (File) ois.readObject();
            ois.close(); tSocket.close();

            data.put(file.getFilename(), file);
            System.out.println("Ho memorizzato il file " + file);

            System.out.println("Invio datagramma all'IndexServer");
            DatagramSocket uSocket = new DatagramSocket();

            String msg = file.getFilename() + "#";
            for (String key: file.getKeywords()) {
                msg += key + ", ";
            }

            byte[] buf = msg.getBytes();
            InetAddress address = InetAddress.getByName(indexServerAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, indexServerPort);
            uSocket.send(packet);
            uSocket.close();
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        StorageServer storageS = new StorageServer("localhost");
    }
}