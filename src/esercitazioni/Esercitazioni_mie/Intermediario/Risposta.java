package Esercitazioni_mie.Intermediario;

import java.io.Serializable;

public class Risposta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int prezzo, quantita, idVenditore, idProdotto;

    public Risposta(int prezzo, int quantita, int idVenditore, int idProdotto) {
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.idProdotto = idProdotto;
        this.idVenditore = idVenditore;
    }

    public int getPrezzo() { return prezzo; }

    public void setPrezzo(int prezzo) { this.prezzo = prezzo; }

    public int getQuantita() { return quantita; }

    public void setQuantita(int quantita) { this.quantita = quantita; }
    
    public int getIdVenditore() { return idVenditore; }

    public void setIdVenditore (int idVenditore) { this.idVenditore =idVenditore; }

    public int getIdProdotto() { return idProdotto; }

    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }
}