package Esercitazione4.Peaky_Blinders;

import java.io.*;
import java.net.*;
import java.util.*;

public class BetServer {
    private HashMap<Integer, Scommessa> scommesse;
    private Calendar limite;
    private BetAccepter accepter;
    private BetDenyer denyer;
    private int port;

    public BetServer (int port, Calendar deadline) {
        scommesse = new HashMap<Integer, Scommessa>();
        limite = deadline;
        this.port = port;
        accepter = new BetAccepter(port);
        accepter.start();
    }

    class BetAccepter extends Thread {
        private int port;
        private ServerSocket serv;
        private boolean accept;

        public BetAccepter (int p) {
            try {
                port = p;
                serv = new ServerSocket(port);
                accept = true;
            } catch (IOException e) { System.err.println(e); }
        }

        public void run() {
            while (accept) {
                try {
                    Calendar now = Calendar.getInstance();
                    serv.setSoTimeout((int)(limite.getTimeInMillis() - now.getTimeInMillis()));
                    Socket k = serv.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(k.getInputStream()));
                    PrintWriter out = new PrintWriter(k.getOutputStream(), true);
                    String line = in.readLine();
                    int pos = line.indexOf(" ");
                    int numCavallo = Integer.parseInt(line.substring(0, pos));
                    long puntata = Long.parseLong(line.substring(pos + 1));
                    InetAddress ip = k.getInetAddress();
                    Scommessa s = new Scommessa(numCavallo, puntata, ip);
                    int key = s.getID();
                    scommesse.put(key, s);
                    out.println("Scommessa accettata");
                    out.close();
                    k.close();
                    System.out.println("Eccoti la ricevuta: " + ip + " " + numCavallo + " " + puntata );
                } catch (SocketTimeoutException st) {
                    accept = false;
                    System.out.println("È chiuso.");
                }
                catch (IOException e) { System.err.println(e); }
            }
            try {
                serv.close();
            } catch (IOException ioe) { System.err.println(ioe); }
        }
    }

    class BetDenyer extends Thread {
        private int port;
        private ServerSocket serv;
        private boolean closed;

        public BetDenyer (int p) {
            try {
                port = p;
                serv = new ServerSocket(port);
                closed = true;
            } catch (IOException e) {System.err.println(e); }
        }

        public void reset() {
            closed = false;
        }

        public void run() {
            try {
                while (closed) {
                    Socket k = serv.accept();
                    PrintWriter out = new PrintWriter(k.getOutputStream(), true);
                    out.println("È chiuso.");
                    out.close();
                    k.close();
                    System.out.println("Scommessa rifiutata");
                }
                serv.close();
            } catch (IOException e) { System.err.println(e); }
        }
    }

    public void accettaScommesse() {
        try {
            accepter.join();
        } catch (InterruptedException e) { System.err.println(e); }
    }

    public void rifiutaScommesse() {
        denyer = new BetDenyer(port);
        denyer.start();
    }

    public void resetServer() {
        denyer.reset();
    }

    public LinkedList<Scommessa> controllaScommesse (int cavalloVincente) {
        LinkedList<Scommessa> elenco = new LinkedList<Scommessa>();
        for (int i = 0; i < scommesse.size(); i++) {
            Scommessa s = scommesse.get(i);
            if (s.equals(new Scommessa(cavalloVincente, 0, null)))
                elenco.addLast(s);
        }
        return elenco;
    }

    public void comunicaVincitori (LinkedList<Scommessa> vincitori, InetAddress ind, int port) {
        ListIterator<Scommessa> it = vincitori.listIterator();
        try {
            @SuppressWarnings("all")
            MulticastSocket socket = new MulticastSocket();
            byte[] buf = new byte[256];
            String m = "";
            int quota = 12;
            while (it.hasNext()) {
                Scommessa s = it.next();
                m += s.getScommettitore() + " " + (s.getPuntata() * quota) + "\n";
            }
            buf = m.getBytes();
            DatagramPacket pk = new DatagramPacket(buf, buf.length, ind, port);
            socket.send(pk);
        } catch (IOException e) { System.err.println(e); }
    }

    public static void main(String[] args) {
        int serverPort = 8001;
        int clientPort = 8002;
        try {
            Calendar deadline = Calendar.getInstance();
            deadline.add(Calendar.MINUTE, 1);
            BetServer server = new BetServer(serverPort, deadline);
            System.out.println("Scommesse aperte");
            server.accettaScommesse();
            server.rifiutaScommesse();
            int vincente = ((int)(Math.random() * 12)) + 1;
            System.out.println("Oggi ha vinto: " + vincente);
            LinkedList<Scommessa> elencoVincitori = server.controllaScommesse(vincente);
            InetAddress multiAddress = InetAddress.getByName("230.0.0.1");
            server.comunicaVincitori(elencoVincitori, multiAddress, clientPort);
            Thread.sleep(120000);
            server.resetServer();
        } catch (InterruptedException e) { System.err.println(e); }
        catch (UnknownHostException ue) { System.err.println(ue); }
    }
}