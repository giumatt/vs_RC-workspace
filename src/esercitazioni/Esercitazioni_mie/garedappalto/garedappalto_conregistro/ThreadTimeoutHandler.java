package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ThreadTimeoutHandler extends Thread {
    public static final long TIMEOUT = 60000;
    private final static int gPort = 3000;
    private final static String gAddress = "230.0.0.1";
    private final int idGara;
    private final MulticastSocket mSocket;
    private final RegistroGare registro;

    public ThreadTimeoutHandler(int idGara, RegistroGare registro) throws IOException {
        this.registro = registro;
        this.idGara = idGara;
        this.mSocket = new MulticastSocket(gPort);
    }

    @Override
    public void run() {
        try {
            inviaRichiestaAiPartecipanti(registro.getRichiesta(idGara));
            sleep(TIMEOUT);
            this.registro.chiudiGara(idGara);
            System.out.println("Gara " + idGara + " chiusa!");
            inviaEsitoGara();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private void inviaRichiestaAiPartecipanti(Richiesta2 richiesta) {
        try {
            String r = "RICHIESTA-" + richiesta.getIdEnte() + "-" +
                richiesta.getDescrizione() + "-" +
                richiesta.getImportoMassimo();
            byte buf[] = r.getBytes();
            InetAddress group = InetAddress.getByName(gAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, gPort);
            mSocket.send(packet);
            System.out.println("Inviata richiesta in multicast: " + richiesta);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void inviaEsitoGara() {
        try {
            System.out.println("Ricerco nel registro gara " + idGara);
            Offerta2 oVincente = this.registro.getOffertaVincente(idGara);
            if (oVincente == null) oVincente = new Offerta2(-1, idGara, -1);
            System.out.println(oVincente);
            String message = "ESITO-" + this.idGara + "-" + 
                    oVincente.getIdPartecipante() + "-" +
                    oVincente.getImportoRichiesto();
            byte buf[] = message.getBytes();
            InetAddress group = InetAddress.getByName(gAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, gPort);
            mSocket.send(packet);

            ObjectOutputStream oos = new ObjectOutputStream(this.registro.getSocketEnte(idGara).getOutputStream());
            oos.writeObject(oVincente);
            System.out.println("Inviata offerta vincente all'ente");
            this.registro.getSocketEnte(idGara).close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}