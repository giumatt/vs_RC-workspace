package Esercitazioni_mie.IndexServer;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try {
            
            File file = new File("prova", new String[]{"a", "b", "c"}, "...");
            System.out.println("Salva il file " + file);
            Socket tSocket = new Socket("localhost", 2000);
            ObjectOutputStream oos = new ObjectOutputStream(tSocket.getOutputStream());

            oos.writeObject(file);
            oos.flush(); oos.close(); tSocket.close();

            Thread.sleep(3000);

            file = new File("gatto", new String[]{"d", "e"}, ",,,");
            System.out.println("Salva il file " + file);
            tSocket = new Socket("localhost", 2000);
            oos = new ObjectOutputStream(tSocket.getOutputStream());

            oos.writeObject(file);
            oos.flush(); oos.close(); tSocket.close();

            Thread.sleep(3000);
        } catch (Exception e) { e.printStackTrace(); }

        try {
            String ricerca = "gatto#a,b";
            System.out.println("Ricerca tutti i file identificati da " + ricerca);
            Socket tSocket = new Socket("localhost", 4000);
            PrintWriter pw = new PrintWriter(tSocket.getOutputStream(), true);
            pw.println(ricerca);

            BufferedReader br = new BufferedReader(new InputStreamReader(tSocket.getInputStream()));

            String risposta = br.readLine();
            System.out.println("Risultato ricerca: " + risposta);

            br.close(); pw.close(); tSocket.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}