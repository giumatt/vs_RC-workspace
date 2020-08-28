package Esercitazioni_mie.Intermediario;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private int idProdotto, quantita;

    public Richiesta(int idProdotto, int quantita) {
        super();
        this.idProdotto = idProdotto;
        this.quantita = quantita;
    }

    public int getIdProdotto() { return idProdotto; }

    public int getQuantita() { return quantita; }

    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public void setQuantita(int quantita) { this.quantita = quantita; }
}