package ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Drop:
 *         S'ha de fer servir el mètode "toString" per a comprovar que la taula conté els elements correctes després de
 *         fer servir "drop".
 *
 *         S'ha de crear una taula que conté un element que no col·lisiona i altres 3 elements que si col·lisionen entre ells,
 *         tal i com s'ha fet a les proves anteriors.
 */
public class HashTableDropTest {

    /**
     * Esborrar un element que no col·lisiona dins una taula.
     */
    @Test
    public void testElementNoColDinsUnaTaula() {
        HashTable hashTable = new HashTable();

        String key;
        String value;
        String result;
        String msg;

        key = "fer";
        value = "m03";
        hashTable.put(key, value);
        hashTable.drop(key);
        result = hashTable.get(key);
        msg = "S'esperava que la clau " + key + " ja no hi fos.";
        // assertTrue(hashTable.toString().isBlank());
        assertNull(result, msg);
    }

    /**
     * Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void testElementColisionaDinsUnaTaulaPrimeraPos() {
        HashTable hashTable = new HashTable();

        String key;
        String value;

        key = "fer";
        value = "m03";
        hashTable.put(key, value);

        String segona;
        String segonaValue;

        segona = hashTable.getCollisionsForKey(key);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);
        hashTable.drop(key);

        String expected;
        String msg;
        expected = HashTableTestHelper.formatExpectedEntry(segona, segonaValue);
        msg = "S'esperava que la clau " + key + " ja no hi fos.";
        assertNull(hashTable.get(key), "S'esperava que la clau " + key + " ja no hi fos.");
        // System.out.printf("ESPERAT: " + expected + "\nRESULTAT: " + hashTable.toString());
        // if (hashTable.toString().isBlank()) System.out.println("que??");
        assertEquals(expected, hashTable.toString(), msg);
    }

    /**
     * Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */

    /**
     * Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */

    /**
     * Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
}
