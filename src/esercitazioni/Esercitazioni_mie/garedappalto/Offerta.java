package Esercitazioni_mie.garedappalto;

import java.io.Serializable;

public class Offerta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private int importoRichiesto;

    public Offerta (int id, int importoRichiesto) {
        this.id = id;
        this.importoRichiesto = importoRichiesto;
    }

    public int getId() { return id; }

    public int getImportoRichiesto() { return importoRichiesto; }

    @Override
    public String toString() {
        return "Offerta{" + "id=" + id +", importoRichiesto=" + importoRichiesto + '}';
    }
}