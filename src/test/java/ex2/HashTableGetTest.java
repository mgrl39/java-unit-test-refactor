package ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String msg;
        String value;

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
        String msg;
        String value;

        key 
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
     */

    /**
     * Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
     */

    /**
     * Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
}
