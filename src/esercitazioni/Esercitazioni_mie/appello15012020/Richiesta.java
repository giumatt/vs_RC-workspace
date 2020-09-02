package Esercitazioni_mie.appello15012020;

import java.io.Serializable;

public class Richiesta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String data;
    private int numeroPersone;

    public Richiesta(String data, int persone) {
        this.data = data;
        this.numeroPersone = persone;
    }

    public String getData() { return data; }

    public int getNumPersone() { return numeroPersone; }
}
