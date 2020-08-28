package Esercitazioni_mie.VoliAeroportoWSDL;

import java.io.Serializable;

public class Orario implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int ore, minuti;

    public Orario(int ore, int minuti) {
        this.ore = ore;
        this.minuti = minuti;
    }

    public int getOre() { return ore; }

    public int getMinuti() { return minuti; }

    public int hashCode() {
        return (ore + "-" + minuti).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    public String toString() {
        return "Orario [ore: " + ore + ", minuti: " + minuti + "]";
    }
}