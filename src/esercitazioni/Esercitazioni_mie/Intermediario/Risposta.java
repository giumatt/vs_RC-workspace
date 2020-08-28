package Esercitazioni_mie.Intermediario;

import java.io.Serializable;

public class Risposta implements Serializable {
    private int prezzo, quantita, idVenditore, idProdotto;

    public Risposta(int prezzo, int quantita, int idVenditore, int idProdotto) {
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.idProdotto = idProdotto;
        this.idVenditore = idVenditore;
    }

    public int getPrezzo() { return prezzo; }

    public void setPrezzo(int quantita) { this.prezzo = prezzo; }

    public int getQuantita() { return quantita; }

    public void setQuantita()  thie
}