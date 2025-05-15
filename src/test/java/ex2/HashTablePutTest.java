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
import static org.junit.jupiter.api.Assertions.assertTrue;

//   S'ha de fer servir el mètode "put" generar les següents situacions:
public class HashTablePutTest {
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
        assertTrue(hashTable.toString().contains(expected),
                "La taula no conté l'element esperat:\n " + hashTable + "\nExpected: " + expected);
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
                "La taula no conté l'element esperat:\n " + hashTable + "\nExpected: " + expected);
    }


    /**
       Inserir un element que col·lisiona dins una taula no vuida,
       que es col·locarà en 2a posició dins el mateix bucket.
     */
    @Test
    public void testInserirElementColisioSegonaPosTaulaNoBuida() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        String key;
        String value;
        String expected;

        key = hashTable.getCollisionsForKey(primera); // Per trobar una clau que col·lisiona amb "abc"
        value = "prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(primera, primeraValue) + " -> [" + key + ", " + value + "]";
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra els valors col·lisionats com esperat:\n " + hashTable + "\nExpected:" + expected);
    }


    /**
        Inserir un element que col·lisiona dins una taula no vuida,
        que es col·locarà en 3a posició dins el mateix bucket.
     */
    @Test
    public void testInserirElementColisioTerceraPosTaulaNoBuida() {
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

        String key;
        String value;
        String expected;

        key = colls.get(1); // Per trobar una clau que col·lisiona amb "abc"
        value = "prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(primera, primeraValue) + " -> [" + segona + ", " + segonaValue + "]";
        expected += " -> [" + key + ", " + value + "]";
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra els valors col·lisionats com esperat:\n " + hashTable + "\nExpected: " + expected);
    }

    /**
        Inserir un elements que ja existeix (update)
        sobre un element que no col·lisiona dins una taula no vuida.
     */
    @Test
    public void testUpdateElementNoColisioTaulaNoBuida() {
        HashTable hashTable = new HashTable();

        // Elements previs (no col·lisionen amb "abc")
        Map<String, String> dades = Map.of(
                "fer", "m03",
                "david", "m05",
                "yago", "m12"
        );
        dades.forEach(hashTable::put);
        hashTable.put("abc", "prova");
        String key;
        String value;
        String expected;

        key = "abc";
        value = "canvi de la prova";
        hashTable.put(key, value);
        expected = formatExpectedEntry(key, value);
        assertTrue(hashTable.toString().contains(expected),
                "La taula no mostra el valor com esperat:\n" + hashTable + "\nExpected: " + expected);

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
        expected = formatExpectedEntry(key, value) + " -> [" + segona + ", " + segonaValue + "]";
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

}
