package ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/*
    Get:
        S'ha de fer servir el mètode "get" per a comprovar que la taula retorna els elements correctes.
        S'ha de fer servir el mètode "put" generar les següents situacions:
 */
public class HashTableGetTest {

    /**
     * Obtenir un element que no col·lisiona dins una taula vuida.
     */
    @Test
    public void testObtenirElementNoColisioTaulaBuida() {
        HashTable hashTable = new HashTable();

        String key;
        String value;
        String msg;

        key = "abc";
        value = "prova";
        hashTable.put(key, value);
        msg = String.format(
                "La taula no ha retornat el valor esperat per la clau: %s\nValor esperat: %s\nValor retornat: %s",
                key, value, hashTable.get(key)
        );
        assertEquals(value, hashTable.get(key), msg);
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void testObtenirElementColisioTaula() {
        HashTable hashTable = new HashTable();

        String key;
        String value;
        String msg;

        key = "abc";
        value = "prova";
        hashTable.put(key, value);

        String segon;
        String segonValue;
        segon = hashTable.getCollisionsForKey(key);
        segonValue = "m05";
        hashTable.put(segon, segonValue);
        // System.out.printf(hashTable.toString() + "\n" + hashTable.get(key) + "\n");
        msg = String.format(
                "La taula no ha retornat el valor esperat per la clau: %s\nValor esperat: %s\nValor retornat: %s",
                key, value, hashTable.get(key)
        );
        assertEquals(value, hashTable.get(key), msg);
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
     */

    // TODO: ASK IF I need to put 3 (firstNode) -> (secondNode) -> (lastNode)
    @Test
    public void testObtenirElementColisioTaulaSegonaPos() {
        HashTable hashTable = new HashTable();

        String primer;
        String primerValue;

        primer = "fer";
        primerValue = "m03";
        hashTable.put(primer, primerValue);

        String key;
        String value;
        String msg;

        key = hashTable.getCollisionsForKey(primer);
        value = "prova que recuperare";
        hashTable.put(key, value);
        // System.out.printf(hashTable.toString() + "\n" + hashTable.get(key) + "\n"); // bucket[3] = [fer, m03] -> [3, prova que recuperare]
        msg = String.format(
                "La taula no ha retornat el valor esperat per la clau: %s\nValor esperat: %s\nValor retornat: %s",
                key, value, hashTable.get(key)
        );
        assertEquals(value, hashTable.get(key), msg);
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    // TODO: he afegit extra un 4t, preguntar si afectara en alguna forma...
    @Test
    public void testObtenirElementColisioTaulaTerceraPos() {
        HashTable hashTable = new HashTable();

        String primer;
        String primerValue;
        primer = "fer";
        primerValue = "m03";
        hashTable.put(primer, primerValue);

        ArrayList<String> colls = hashTable.getCollisionsForKey(primer, 3);
        String segona;
        String segonaValue;
        segona = colls.get(0);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);

        String key;
        String value;
        String msg;
        key = colls.get(1);
        value = "m12";
        hashTable.put(key, value);

        msg = String.format(
                "La taula no ha retornat el valor esperat per la clau: %s\nValor esperat: %s\nValor retornat: %s",
                key, value, hashTable.get(key)
        );

        String quarta;
        String quartaValue;
        quarta = colls.get(2);
        quartaValue = "m00";
        hashTable.put(quarta, quartaValue);

        assertEquals(value, hashTable.get(key), msg);
    }

    /**
     * Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void testObtenirElementNoExisteixPosBuida() {
        HashTable hashTable = new HashTable();

        String key;
        String msg;
        String result;

        key = "abc";
        result = hashTable.get(key);
        msg = String.format("S'esperava NULL perquè la clau %s no existeix, pero s'ha retornat %s",
                key, result);
        assertNull(result, msg);
    }

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
}
