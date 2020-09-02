package Esercitazioni_mie.appellonovembre2019;

import java.io.Serializable;

public class Risposta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String idNegozio;
    private int prezzo;

    public Risposta(String id, int prezzo) {
        this.idNegozio = id;
        this.prezzo = prezzo; 
    }

    public String getIdNegozio() { return idNegozio; }

    public int getPrezzo() { return prezzo; }
}
