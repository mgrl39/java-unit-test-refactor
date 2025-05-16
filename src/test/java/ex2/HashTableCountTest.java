package ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *  Count:
 *      S'ha de repetir tot el que s'ha fet amb "put" i comprovar amb "count" que el número de nodes és correcte.
 *      S'ha de repetir tot el que s'ha fet amb "drop" i comprovar amb "count" que el número de nodes és correcte.
 */
public class HashTableCountTest {

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula vuida (sense elements).
     * DROP: Esborrar un element que no col·lisiona dins una taula.
     */
    @Test
    public void testComptarInserirElementNoColisionaDinsTaulaBuidaDespresEsborrar() {
        HashTable hashTable = new HashTable();

        String key;
        String value;

        key = "fer";
        value = "m03";
        // Inicialment hauria d'estar buida
        assertEquals(0, hashTable.count(),
                "El valor del compte no es correcte. \nExpected: 0\nActual: " + hashTable.count());
        // Després d'afegir un element
        hashTable.put(key, value);
        assertEquals(1, hashTable.count(),
                "El valor del compte no es correcte. \nExpected: 1\nActual: " + hashTable.count());
        // Després d'esborrar-lo
        hashTable.drop(key);
        assertEquals(0, hashTable.count(),"El valor del compte no es correcte. \nExpected: 0\nActual: " + hashTable.count());
    }

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
     * DROP: Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */

    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */

    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */

    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */

    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */

    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */

    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
     */
}
