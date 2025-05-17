package ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ex2.HashTableTestHelper.*;
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
    public void testEsborrarElementNoExisteixPosBuida() {
        HashTable hashTable = new HashTable();

        String key;
        key = "fer";

        hashTable.drop(key);
        assertNull(hashTable.get(key), "S'esperava null perquè no s'ha afegit cap clau amb aquest nom.");
        assertTrue(hashTable.toString().isBlank(), "La taula hauria d'estar buida.");
    }

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void testEsborrarElementNoExisteixSevaPosOcupadaPerAltreNoCol() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        String key;
        key = hashTable.getCollisionsForKey(primera);
        hashTable.drop(key);
        assertNull(hashTable.get(key), "S'esperava null perquè no s'ha afegit cap clau amb aquest nom.");

        String expected;
        String actual;
        expected = HashTableTestHelper.formatExpectedEntry(primera, primeraValue);
        actual = hashTable.toString();
        // System.out.printf("ACTUAL: %s\n", actual);
        // System.out.printf("EXPECTED: %s\n", expected);
        assertEquals(expected, actual, "S'esperava altre resposta. \nExpected:" + expected + "\nActual:" + actual);
    }

    /**
     * Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void testEsborrarElementNoExisteixSevaPosOcupadaPerTresElementsColisionats() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;
        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

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

        String key;
        key = colls.get(2); // No ficare aquesta!
        assertNull(hashTable.get(key), "S'esperava null perquè no s'ha afegit cap clau amb aquest nom.");
        hashTable.drop(key);
        assertNull(hashTable.get(key), "S'esperava null perquè no s'ha afegit cap clau amb aquest nom.");

        String expected;
        String actual;

        expected = HashTableTestHelper.formatExpectedEntry(primera, primeraValue);
        expected += HashTableTestHelper.formatChainedEntry(segona, segonaValue);
        expected += HashTableTestHelper.formatChainedEntry(tercera, terceraValue);
        actual = hashTable.toString();
        // System.out.printf("ACTUAL: %s\n", actual);
        // System.out.printf("EXPECTED: %s\n", expected);
        assertEquals(expected, actual, "S'esperava altre resposta. \nExpected:" + expected + "\nActual:" + actual);
    }
}
