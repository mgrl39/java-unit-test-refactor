package ex4;

import org.junit.jupiter.api.Test;

import java.util.*;

import static ex4.HashTableTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

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
    public void countPutElementNoColDinsTaulaBuidaAndDrop() {
        final String key = "clau";
        // final String value = "valor";
        final boolean value = true;

        HashTable hashTable = createEmptyTable();  // Inicialment hauria d'estar buida
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));
        hashTable.put(key, value); // Després d'afegir un element
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));
        hashTable.drop(key); // Després d'esborrar-lo
        assertEquals(0, hashTable.count(),valueCountNotCorrect(0, hashTable.count()));
    }

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
     * DROP: Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void countPutElementNoColDinsTaulaNoBuidaDespresDropElPrimer() {
        final String key = "clau";
        final double value = 3.5;
        // final String value = "valor";
        // Map<String, String> colMap = new LinkedHashMap<>();
        Map<String, Object> colMap = new LinkedHashMap<>();
        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);

        // Inicialment hauria d'haver ja dos elements dins
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        final String element = "elementExtra";
        final String valorElement = "extraValor";
        // Inserim l'element que no col·lisiona dins una taula no buida
        hashTable.put(element, valorElement); // El compte ha de ser 3 (els dos que col·lisionen + el nou)
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        // System.out.printf(hashTable.toString());

        // Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.drop(key); // Ara hauria de ser dos el count
        // System.out.printf(hashTable.toString());
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
    }

    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */
    @Test
    public void countPutElementColDinsTaulaNoBuidaSegonaPosDropSegon() {
        final String key = "clau";
        final String value = "valor";
        HashTable hashTable = createTableWithOneElement(key, value);

        // Inicialment hauria d'haver ja un element afegit
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        // Inserir un element que col·lisiona dins una taula no vuida,
        // que es col·locarà en 2a posició dins el mateix bucket.
        final String nouKey = hashTable.getCollisionsForKey(key);
        hashTable.put(nouKey, value);
        // Hauria d'haver ja dos elements dins
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        // Esborrar un element que si col·lisiona dins una taula (2a pos)
        hashTable.drop(nouKey);
        // Hauria d'haver un unic element ara
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));
    }


    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    @Test
    public void countPutElementColDinsTaulaNoBuidaTerceraPosDropTercera() {
        final String key = "clau";
        final String value = "valor";
        // Map<String, String> colMap = new LinkedHashMap<>(); // colMap tindra la 1 clau colisionada (2na pos)
        Map<String, Object> colMap = new LinkedHashMap<>(); // colMap tindra la 1 clau colisionada (2na pos)

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        // Inicialment hauria d'haver ja dos elements afegits
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
        // System.out.printf(hashTable.toString()); //  bucket[13] = [clau, valor] -> [30, valor_2]

        // Inserir un element que col·lisiona dins una taula no vuida,
        // que es col·locarà en 3a posició dins el mateix bucket.
        final String novaClau = getUnusedCollisionKey(hashTable, key, colMap);
        hashTable.put(novaClau, value);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        // System.out.printf(hashTable.toString());

        // Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.drop(novaClau);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
        // System.out.printf(hashTable.toString());
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void countUpdateElementNoColTablaNoBuidaEliminarElementNoExisteixPosBuida() {
        final String key = "clau";
        final String value = "valor";
        HashTable hashTable = createTableWithOneElement(key, value);

        // Comprovacio de que el element esta comptat al count
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        // Update del element que no col·lisiona
        final String nouValue = "nou valor";
        hashTable.put(key, nouValue);
        // Comprovacio de que el element esta comptat al count. Que no hagi pujat
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        // Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
        final String altreKey = "zzz";
        hashTable.drop(altreKey);

        // Comprovacio de que no hagi baixat el count.
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void countUpdateElementSiColisionaTaulaNoBuidaDropElementNoExisteixTotIquePosOcupada() {
        final String key = "clau";
        final String value = "valor";
        // Map<String, String> colMap = new LinkedHashMap<>();
        Map<String, Object> colMap = new LinkedHashMap<>();

        HashTable hashTable = createTableWithCollisions(key, value, 1, colMap);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
        // System.out.printf(hashTable.toString()); // bucket[13] = [clau, valor] -> [30, valor_2]

        // Update sobre un element que si col·lisiona
        final String nouValue = "nou valor";
        hashTable.put(key, nouValue);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
        // System.out.printf(hashTable.toString()); // bucket[13] = [clau, nou valor] -> [30, valor_2]

        // Eliminar un elements que no existeix,
        // tot i que la seva posició està ocupada per un altre que no col·lisiona
        final String keyNoCollide = "zzz";
        hashTable.put(keyNoCollide, value);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        // Ara busquem una clau que col·lisionaria amb "zzz" però que NO ha estat afegida
        final String missingKey = getUnusedCollisionKey(hashTable, keyNoCollide, Map.of(keyNoCollide, value));

        // Eliminem aquesta clau inexistent (col·lisionaria amb "zzz", pero no esta afegida)
        hashTable.drop(missingKey);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        // System.out.printf(hashTable.toString());
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void countUpdateSegonElementSiColisionaDropElementNoExisteixSevaPosOcupada() {
        final String key = "clau";
        final String value = "valor";

        // Map<String, String> colMap = new LinkedHashMap<>();
        Map<String, Object> colMap = new LinkedHashMap<>();
        HashTable hashTable = createTableWithCollisions(key, value, 2, colMap);

        // List<Map.Entry<String, String>> entries = getCollisionEntries(colMap);
        List<Map.Entry<String, Object>> entries = getCollisionEntries(colMap);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        // Update sobre un element que col en segona pos
        hashTable.put(entries.get(0).getKey(), "NOU VALOR, BON DIA!");
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        // System.out.printf(hashTable.toString());

        // Update sobre un element que col en tercera pos
        hashTable.put(entries.get(1).getKey(), "NOU VALOR, BONA NIT!");
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        // Eliminar un elements que no existeix,
        // tot i que la seva posició està ocupada per 3 elements col·lisionats.
        final String missingKey = getUnusedCollisionKey(hashTable, key, colMap);
        hashTable.drop(missingKey);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        // System.out.printf(hashTable.toString());
    }

}
