package ex3;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ex3.HashTableTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *       Size:
 *         S'ha de repetir tot el que s'ha fet amb "put" i comprovar amb "size" que el tamany de la taula és correcte.
 *         S'ha de repetir tot el que s'ha fet amb "drop" i comprovar amb "size" que el tamany de la taula és correcte.
 */
public class HashTableSizeTest {

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula vuida (sense elements).
     * DROP: Esborrar un element que no col·lisiona dins una taula.
     */
    @Test
    public void sizePutElementNoColDinsTaulaBuidaAndDrop() {
        final String key = "clau";
        final String value = "valor";

        HashTable hashTable = createEmptyTable();
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
        hashTable.put(key, value);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
        hashTable.drop(key);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
     * DROP: Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void sizePutElementNoColDinsTaulaNoBuidaDespresDropElPrimer() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();
        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);

        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        final String element = "elementExtra";
        final String valorElement = "extraValor";
        hashTable.put(element, valorElement);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        hashTable.drop(key);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }

    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */
    @Test
    public void sizePutElementColDinsTaulaNoBuidaSegonaPosDropSegon() {
        final String key = "clau";
        final String value = "valor";
        HashTable hashTable = createTableWithOneElement(key, value);

        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Inserir un element que col·lisiona dins una taula no vuida,
        // que es col·locarà en 2a posició dins el mateix bucket.
        final String nouKey = hashTable.getCollisionsForKey(key);
        hashTable.put(nouKey, value);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Esborrar un element que si col·lisiona dins una taula (2a pos)
        hashTable.drop(nouKey);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }


    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    @Test
    public void sizePutElementColDinsTaulaNoBuidaTerceraPosDropTercera() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>(); // colMap tindra la 1 clau colisionada (2na pos)

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Inserir un element que col·lisiona dins una taula no vuida,
        // que es col·locarà en 3a posició dins el mateix bucket.
        final String novaClau = getUnusedCollisionKey(hashTable, key, colMap);
        hashTable.put(novaClau, value);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.drop(novaClau);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void sizeUpdateElementNoColTablaNoBuidaEliminarElementNoExisteixPosBuida() {
        final String key = "clau";
        final String value = "valor";
        HashTable hashTable = createTableWithOneElement(key, value);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Update del element que no col·lisiona
        final String nouValue = "nou valor";
        hashTable.put(key, nouValue);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
        final String altreKey = "zzz";
        hashTable.drop(altreKey);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void sizeUpdateElementSiColisionaTaulaNoBuidaDropElementNoExisteixTotIquePosOcupada() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Update sobre un element que si col·lisiona
        final String nouValue = "nou valor";
        hashTable.put(key, nouValue);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Eliminar un elements que no existeix,
        // tot i que la seva posició està ocupada per un altre que no col·lisiona
        final String keyNoCollide = "zzz";
        hashTable.put(keyNoCollide, value);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Ara busquem una clau que col·lisionaria amb "zzz" però que NO ha estat afegida
        final String missingKey = getUnusedCollisionKey(hashTable, keyNoCollide, Map.of(keyNoCollide, value));

        // Eliminem aquesta clau inexistent (col·lisionaria amb "zzz", pero no esta afegida)
        hashTable.drop(missingKey);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void sizeUpdateSegonElementSiColisionaDropElementNoExisteixSevaPosOcupada() {
        final String key = "clau";
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();
        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);

        List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Update sobre un element que col en segona pos
        hashTable.put(entries.get(0).getKey(), "NOU VALOR, BON DIA!");
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Update sobre un element que col en tercera pos
        hashTable.put(entries.get(1).getKey(), "NOU VALOR, BONA NIT!");
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));

        // Eliminar un elements que no existeix,
        // tot i que la seva posició està ocupada per 3 elements col·lisionats.
        final String missingKey = getUnusedCollisionKey(hashTable, key, colMap);
        hashTable.drop(missingKey);
        assertEquals(HASH_TABLE_SIZE, hashTable.size(), valueSizeNotCorrect(HASH_TABLE_SIZE, hashTable.size()));
    }
}
