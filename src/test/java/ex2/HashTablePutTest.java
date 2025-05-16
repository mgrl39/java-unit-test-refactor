package ex2;

/*
    S'ha de fer servir el mètode "toString" (aquest mètode no falla)
    per a comprovar que la taula conté els elements correctes
    després de fer servir "put".
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static ex2.HashTableTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//   S'ha de fer servir el mètode "put" generar les següents situacions:
public class HashTablePutTest {
    /**
         Inserir un element que no col·lisiona dins
         una taula vuida (sense elements).
     */
    @Test
    public void putElementNoColTaulaBuida () {
        final String key = "clau";
        final String value = "valor";

        HashTable hashTable = createTableWithOneElement(key, value);
        String expected = formatExpectedEntry(key, value);
        assertEquals(hashTable.toString(), expected, errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un element que no col·lisiona dins
        una taula no vuida (amb elements).
     */
    @Test
    public void putElementNoColTaulaNoBuida() {
        Map<String, String> dades = Map.of(
                "fer", "m03",
                "david", "m05",
                "yago", "m12"
        ); // Elements previs (no col·lisionen amb "abc")
        HashTable hashTable = createTableWithMultipleElements(dades);

        final String key = "clau";
        final String value = "valor";

        hashTable.put(key, value);
        final String expected = formatExpectedEntry(key, value);
        // System.out.printf(hashTable.toString()); // Al printf es veu que no col·lisiona.
        assertTrue(
                hashTable.toString().contains(expected),
                errorMessage(expected, hashTable.toString())
        );
    }

    /**
       Inserir un element que col·lisiona dins una taula no vuida,
       que es col·locarà en 2a posició dins el mateix bucket.
     */
    @Test
    public void putElementColSegonaPosTaulaNoBuida() {
        final String key = "clau";
        final String value = "valor";
        ArrayList<String> colKeys = new ArrayList<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colKeys);

        final String expected = formatBucketChainFromList(key, value, colKeys, value);
        // System.out.printf(expected);
        // System.out.println(hashTable.toString());
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un element que col·lisiona dins una taula no vuida,
        que es col·locarà en 3a posició dins el mateix bucket.
     */
    @Test
    public void putElementColTerceraPosTaulaNoBuida() {
        final String key = "clau";
        final String value = "valor";
        ArrayList<String> colKeys = new ArrayList<>();

        HashTable hashTable = createTableWithCollisions(key, value, 2, colKeys);

        final String expected = formatBucketChainFromList(key, value, colKeys, value);
        // System.out.printf(expected);
        // System.out.println(hashTable.toString());
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que no col·lisiona dins una taula no vuida.
     */
    @Test
    public void updateElementNoColTaulaNoBuida() {
        // Elements previs que no col·lisionen amb "clau"
        Map<String, String> dades = Map.of(
                "fer", "m03",
                "david", "m05",
                "yago", "m12"
        );
        HashTable hashTable = createTableWithMultipleElements(dades);

        final String key = "clau";
        final String initialValue  = "valor";
        final String updatedValue = "nou valor";

        // Afegim la clau per primera vegada
        hashTable.put(key, initialValue);
        // System.out.println(hashTable.toString());
        // La reassinem amb un valor nou (update)
        hashTable.put(key, updatedValue);
        // System.out.println(hashTable.toString());
        final String expected = formatExpectedEntry(key, updatedValue);
        assertTrue(hashTable.toString().contains(expected), errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un elements que ja existeix (update) sobre un element
        que si col·lisiona (1a posició) dins una taula no vuida.
     */
    @Test
    public void updateElementColPrimeraPosTaulaNoBuida() {
        final String key = "clau";
        final String value = "valor";
        ArrayList<String> colKeys = new ArrayList<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colKeys);
        final String updatedValue = "nou valor";
        hashTable.put(key, updatedValue);

        final String expected = formatBucketChainFromList(key, updatedValue, colKeys, value);
        // System.out.printf(expected);
        assertTrue(hashTable.toString().contains(expected), errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona
        (2a posició) dins una taula no vuida.
     */
    @Test
    public void updateElementColSegonaPosTaulaNoBuida() {
        final String key = "clau"; // primera clau, entrada al bucket
        final String value = "valor";
        ArrayList<String> colKeys = new ArrayList<>();

        // Ara a colKeys tindrem un colKey.get(0) que es la segona entrada al bucket (primer col·lisionat)
        HashTable hashTable = createTableWithCollisions(key, value, 1, colKeys);

        // Actualitcem el segon element (col·lideix en 2a posicio)
        final String updatedValue = "nou valor";
        // La key del primer element es key, pero a partir del segon estan en colKeys
        hashTable.put(colKeys.get(0), updatedValue);

        String[] pairs = {
          colKeys.get(0), updatedValue
        };

        final String expected = formatBucketChain(key, value, pairs);
        // System.out.printf(expected);
        // System.out.printf(hashTable.toString());
        assertTrue(hashTable.toString().contains(expected), errorMessage(expected, hashTable.toString()));
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona (3a posició)
        dins una taula no vuida.
    */
    @Test
    public void testUpdateElementColisioTerceraPosTaulaNobuida() {
        final String key = "clau";
        final String value = "valor";
        ArrayList<String> colKeys = new ArrayList<>();

        HashTable hashTable = createTableWithCollisions(key, value, 2, colKeys);

        // Actualitcem el segon element (col·lideix en 3a posicio)
        final String updatedValue = "nou valor";
        // 0 es la segona pos, 1 es la tercera.
        hashTable.put(colKeys.get(1), updatedValue);

        // key: 1a posició al bucket
        // colKeys.get(0): 2a posició (1a col·lisió)
        // colKeys.get(1): 3a posició (2a col·lisió) -> actualitzem aquest
        String[] pairs = {
                colKeys.get(0), value,
                colKeys.get(1), updatedValue
        };
        final String expected = formatBucketChain(key, value, pairs);
        // System.out.printf(expected);
        assertTrue(hashTable.toString().contains(expected), errorMessage(expected, hashTable.toString()));
    }

}
