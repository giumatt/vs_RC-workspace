package Esercitazioni_mie.appello15012020;

import java.io.Serializable;

public class Offerta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String hostnameCentroBenessere;
    private int prezzo;

    public Offerta(String hostname, int prezzo) {
        this.hostnameCentroBenessere = hostname;
        this.prezzo = prezzo;
    }

    public String getHostnameCB() { return hostnameCentroBenessere; }

    public int getPrezzo() { return prezzo; }

    @Override
    public String toString() {
        return "Offerta [hostname: " + hostnameCentroBenessere + ", prezzo: " + prezzo + "]";
    }
}
