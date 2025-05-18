package ex4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ex4.HashTableTestHelper.*;
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
    public void getElementAfegitAPosNova() {
        final String key = "clau";
        final String value = "valor";

        HashTable hashTable = createTableWithOneElement(key, value);
        assertEquals(value, hashTable.get(key), errorMessage(value, hashTable.get(key)));
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void getPrimerElementDeBucketAmbColisio() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        assertEquals(value, hashTable.get(key), errorMessage(value, hashTable.get(key)));
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
     */
    @Test
    public void getElementColisioTaulaSegonaPos() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);

        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);

        // Recuperem el valor de la 1a clau col·lisionada (2a posició del bucket)
        final String expected = entries.get(0).getValue();
        final String actual = hashTable.get(entries.get(0).getKey());
        assertEquals(expected, actual, errorMessage(expected, actual));
    }

    /**
     * Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    @Test
    public void getElementColisioTaulaTerceraPos() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        // Crear una taula amb clau base (pos 1) + 2 col·lisions (posicions 2 i 3)
        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);
        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        // Recuperem el valor de la 2a clau col·lisionada (3a posició del bucket)
        final String expected = entries.get(1).getValue();
        final String actual = hashTable.get(entries.get(1).getKey());
        assertEquals(expected, actual, errorMessage(expected, actual));
    }

    /**
     * Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void getElementNoExisteixPosBuida() {
        final String key = "clau";
        HashTable hashTable = createEmptyTable();
        final String msg = String.format("S'esperava NULL perquè la clau %s no existeix", key);

        assertNull(hashTable.get(key), msg);
    }

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void getElementNoExisteixPosOcupadaPerAltreNoCol() {
        final String key = "clau";
        final String value = "valor";
        HashTable hashTable = createTableWithOneElement(key, value);

        // Calculem una clau que col·lisionaria però que no s'ha afegit mai
        final String clauInexistent = hashTable.getCollisionsForKey(key);
        final String msg = String.format("S'esperava NULL perquè la clau %s no existeix", clauInexistent);

        assertNull(hashTable.get(clauInexistent), msg);
    }

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void getElementQueNoExisteixPosOcupadaPerElementsCol() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);

        final String missingKey = getUnusedCollisionKey(hashTable, key, colMap);
        assertNull(hashTable.get(missingKey),  "S'esperava null perque la clau no existeix pero col·lisionaria amb altres");
    }
}
