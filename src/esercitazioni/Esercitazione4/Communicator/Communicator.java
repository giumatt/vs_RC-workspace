package Esercitazione4.Communicator;

import java.io.*;
import java.net.*;
import java.util.*;
public class Communicator {
    static int multicastPort;
    static int socketPort;

    static void sendMcastDatagram() {
        try {
            while (true) {
                byte[] buf = new byte[50];
                String strBuf = "";
                strBuf += socketPort;
                buf = strBuf.getBytes();
                InetAddress mcastAddress = InetAddress.getByName("230.0.0.1");
                @SuppressWarnings("all")
                MulticastSocket mSocket = new MulticastSocket();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, mcastAddress, 2000);
                System.out.println("\nCO>Invio datagramma multicast");
                mSocket.send(dp);
                Thread.sleep(20000);
            }
        } catch (Exception e) { System.err.println(e); }
    }

    public static void main(String[] args) {
        try {
            multicastPort = 2000;
            @SuppressWarnings("all")
            Scanner sc = new Scanner(System.in);
            System.out.println("Porta TCP locale: ");
            socketPort = Integer.parseInt(sc.next());
            MulticastListener ml = new MulticastListener(multicastPort, socketPort);
            SocketListener sl = new SocketListener(socketPort);
            ml.start();
            sl.start();
            sendMcastDatagram();
        } catch (Exception e) { System.err.println(e); }
    }
}

class MulticastListener extends Thread {
    int mcastPort;
    InetAddress remAddress;
    int tcpPort;

    public MulticastListener (int port1, int port2) {
        this.mcastPort = port1;
        this.tcpPort = port2;
        System.out.println("ML>Porta multicast locale: " + mcastPort);
        System.out.println("ML>Porta TCP locale: " + tcpPort);
    }

    @Override
    public void run() {
        try {
            @SuppressWarnings("all")
            MulticastSocket mSocket = new MulticastSocket(mcastPort);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            mSocket.joinGroup(group);
            while (true) {
                byte[] buf = new byte[50];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                mSocket.receive(packet);
                remAddress = packet.getAddress();
                String received = new String(packet.getData());
                int i = 0;
                while (Character.isDigit(received.charAt(i)))
                    i++;
                int remTCPPort = Integer.parseInt(received.substring(0, i));
                System.out.println("\nML>Ricevuto mcast datagram da " + remAddress.getHostAddress() + ":" + packet.getPort());
                System.out.println("ML>Contenuto del datagram: " + remTCPPort);
                if (!(remAddress.equals(InetAddress.getLocalHost())) || (remTCPPort != tcpPort)) {
                    System.out.println("ML>Invio risposta a " + remAddress.getHostAddress() + ":" + remTCPPort);
                    Socket tcpSocket = new Socket(remAddress.getHostAddress(), remTCPPort);
                    PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
                    out.println(tcpPort);
                }
            }
        } catch (Exception e) { System.err.println(e); }
    }
}

class SocketListener extends Thread {
    int tcpPort;
    InetAddress remAddress;
    int remTCPPort;

    public SocketListener (int port) {
        this.tcpPort = port;
        System.out.println("SL>Porta TCP locale: " + port);
    }

    @Override
    public void run() {
        try {
            @SuppressWarnings("all")
            ServerSocket sListener = new ServerSocket(tcpPort);
            while (true) {
                Socket sock = sListener.accept();
                InetAddress remAddress = sock.getInetAddress();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String line = in.readLine();
                remTCPPort = Integer.parseInt(line);
                System.out.println("\nSL>Ricevuta risposta da " + remAddress.getHostAddress() + ":" + sock.getPort());
                sock.close();
            }
        } catch (Exception e) { System.err.println(e); }
    }
}