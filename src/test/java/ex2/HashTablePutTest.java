package ex2;

/*
    S'ha de fer servir el mètode "toString" (aquest mètode no falla)
    per a comprovar que la taula conté els elements correctes
    després de fer servir "put".
 */

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

//   S'ha de fer servir el mètode "put" generar les següents situacions:
public class HashTablePutTest {

    private static int getBucketIndex(String k) {
        return Math.floorMod(k.hashCode(), 16);
    }

    private static String formatExpectedEntry(String key, String value) {
        return ("\n bucket[" + getBucketIndex(key) + "] = [" + key + ", " + value + "]");
    }
    /**
         Inserir un element que no col·lisiona dins
         una taula vuida (sense elements).
     */
    @Test
    public void testInserirElementNoColisioTaulaBuida() {
        String expected;
        String key;
        String value;
        HashTable hashTable;

        key = "abc";
        value = "prova";
        hashTable = new HashTable();
        hashTable.put(key, value);

        expected = formatExpectedEntry(key, value);
        assertTrue(hashTable.toString().contains(expected));
    }

    /**
        Inserir un element que no col·lisiona dins
        una taula no vuida (amb elements).
     */
    @Test
    public void testInserirElementNoColisioTaulaNoBuida() {
        HashTable hashTable = new HashTable();

        // Elements previs (no col·lisionen amb "abc")
        Map<String, String> dades = Map.of(
                "fer", "m03",
                "david", "m05",
                "yago", "m12"
        );
        dades.forEach(hashTable::put);

        String key;
        String value;
        String expected;

        key = "abc";
        value = "prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(key, value);
        assertTrue(hashTable.toString().contains(expected),
                "La taula no conté l'element esperat:\n " + hashTable + "\n");
    }


    /**
       Inserir un element que col·lisiona dins
       una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
     */


    /**
        Inserir un element que col·lisiona dins una taula no vuida,
        que es col·locarà en 3a posició dins el mateix bucket.
     */

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que no col·lisiona dins una taula no vuida.
     */

    /**
        Inserir un elements que ja existeix (update) sobre un element
        que si col·lisiona (1a posició) dins una taula no vuida.
     */

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona
        (2a posició) dins una taula no vuida.
     */

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona (3a posició)
        dins una taula no vuida.
    */

}
