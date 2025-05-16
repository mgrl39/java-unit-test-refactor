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
    public void testUpdateElementColisioPrimeraPosTaulaNoBuida() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        String segona;
        String segonaValue;

        segona = hashTable.getCollisionsForKey(primera);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);

        String key;
        String value;
        String expected;
        key = primera;
        value = "prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(key, value) + formatChainedEntry(segona, segonaValue);
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra el valor com esperat:\n" + hashTable + "\nExpected: " + expected);
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona
        (2a posició) dins una taula no vuida.
     */
    @Test
    public void testUpdateElementColisioSegonaPosTaulaNoBuida() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        // Demanem dues col·lisions diferents per "fer"
        ArrayList<String> colls = hashTable.getCollisionsForKey(primera, 2);
        String segona;
        String segonaValue;
        segona = colls.get(0);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);


        String tercera;
        String terceraValue;
        tercera = colls.get(1);
        terceraValue = "prova";
        hashTable.put(tercera, terceraValue);

        String key;
        String value;
        String expected;

        key = segona;
        value = "canvi de la prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(primera, primeraValue) + " -> [" + segona + ", " + value + "]";
        expected += " -> [" + tercera + ", " + terceraValue + "]";
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra el valor com esperat:\n" + hashTable + "\nExpected: " + expected);
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que si col·lisiona (3a posició)
        dins una taula no vuida.
    */
    @Test
    public void testUpdateElementColisioTerceraPosTaulaNobuida() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        // Demanem tres col·lisions diferents per fer
        ArrayList<String> colls = hashTable.getCollisionsForKey(primera, 3);
        String segona;
        String segonaValue;
        segona = colls.get(0);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);

        String tercera;
        String terceraValue;
        tercera = colls.get(1);
        terceraValue = "m12";
        hashTable.put(tercera, terceraValue);

        String quarta;
        String quartaValue;
        quarta = colls.get(2);
        quartaValue = "m00";
        hashTable.put(quarta, quartaValue);

        String key;
        String value;
        String expected;

        key = tercera;
        value = "canvi de la prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(primera, primeraValue) + formatChainedEntry(segona, segonaValue);
        expected += formatChainedEntry(key, value) + formatChainedEntry(quarta, quartaValue);
        // System.out.printf("%s", expected);
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra el valor com esperat:\n" + hashTable + "\nExpected: " + expected);
    }

}
