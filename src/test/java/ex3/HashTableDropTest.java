package ex3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ex3.HashTableTestHelper.*;
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
    public void dropElementNoColDinsUnaTaula() {
        final String key = "clau";
        final String value = "valor";

        HashTable hashTable = createTableWithOneElement(key, value);
        hashTable.drop(key);
        assertTrue(hashTable.toString().isBlank(), "S'esperava que l'element " + key + " no hi fos.");
    }

    /**
     * Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void dropElementColisionaDinsUnaTaulaPrimeraPos() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        hashTable.drop(key);

        final String expected = formatExpectedEntry(entries.get(0).getKey(), entries.get(0).getValue());
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
     * Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */
    @Test
    public void dropElementColisionaDinsUnaTaulaSegonaPos() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        hashTable.drop(entries.get(0).getKey());

        final String expected = formatExpectedEntry(key, value);
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
     * Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    @Test
    public void dropElementcolisionaDinsUnaTaulaTerceraPos() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);
        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        hashTable.drop(entries.get(1).getKey());

        final String expected = formatBucketChain(key, value, entries.get(0).getKey(), entries.get(0).getValue());
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
     * Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void dropElementNoExisteixPosBuida() {
        final String key = "clau";
        HashTable hashTable = createEmptyTable();

        // Prova d'esborrar una clau que no hi es dins d'una taula buida.
        hashTable.drop(key);

        // Comprovem que la taula continua buida
        assertTrue(hashTable.toString().isBlank(),
                "S'esperava que la taula seguís buida després d'un drop inexistent.");
    }

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void dropElementNoExisteixSevaPosOcupadaPerAltreNoCol() {
        final String key = "clau";
        final String value = "valor";

        HashTable hashTable = createTableWithOneElement(key, value);
        final String keyEsborrar = hashTable.getCollisionsForKey(key);

        hashTable.drop(keyEsborrar);
        final String expected = formatExpectedEntry(key, value);
        // System.out.printf(expected);
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void dropElementNoExisteixSevaPosOcupadaPerTresElementsColisionats() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);
        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        final String missingKey = getUnusedCollisionKey(hashTable, key, colMap);
        hashTable.drop(missingKey);
        final String expected = formatBucketChainFromList(key, value, entries);
        // System.out.printf(expected);
        assertEquals(expected, hashTable.toString(), errorMessage(expected, hashTable.toString()));
    }
}
