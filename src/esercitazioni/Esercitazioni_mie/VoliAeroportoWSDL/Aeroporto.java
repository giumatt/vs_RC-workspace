package Esercitazioni_mie.VoliAeroportoWSDL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Aeroporto {
    private HashMap<Data, HashMap<String, LinkedList<Volo>>> db;

    public Aeroporto() {
        db = new HashMap<Data, HashMap<String, LinkedList<Volo>>>();
    }

    public String primoVolo(String citta, Data data) {
        if (!db.containsKey(data) || db.get(data).containsKey(citta))
            return "Nessun volo";
        return db.get(data).get(citta).getFirst().getVoloId();
    }

    public Orario orarioVolo(String voloId, Data data) {
        LinkedList<Volo> tmp;
        if (db.containsKey(data)) {
            for (Entry<String, LinkedList<Volo>> entry: db.get(data).entrySet()) {
                tmp = entry.getValue();
                for (Volo volo2: tmp) {
                    if (volo2.getVoloId().equals(voloId))
                        return volo2.getOrario();
                }
            }
        }
        return new Orario(-1, -1);
    }
}