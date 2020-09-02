package Esercitazioni_mie.appellonovembre2019;

import java.io.Serializable;

public class Richiesta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private int quantita, prezzo;

    public Richiesta(String id, int quantita, int prezzo) {
        this.id = id;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public String getId() { return id; }

    public int getQuantita() { return quantita; }

    public int getPrezzo() { return prezzo; }
}
