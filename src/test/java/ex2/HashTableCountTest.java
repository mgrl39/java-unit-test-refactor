package ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static ex2.HashTableTestHelper.*;
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
        final String value = "valor";

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
        final String value = "valor";
        Map<String, String> colMap = new LinkedHashMap<>();
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
        Map<String, String> colMap = new LinkedHashMap<>(); // colMap tindra la 1 clau colisionada (2na pos)

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
    public void testComptarUpdateElementNoColTablaNoBuidaEliminarElementNoExisteixPosBuida() {
        HashTable hashTable = new HashTable();

        // Aquest primer element es per complir que la taula no es buida.
        String primera;
        String primeraValue;
        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);
        // La taula ara no hauria d'estar buida
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));


        String key;
        String value;
        key = "prova";
        value = "key value";
        // System.out.printf("Hash de la primera: %s\nHash de la segona: %s\n", hashTable.getHash(primera), hashTable.getHash(key));
        assertNotEquals(hashTable.getHash(primera), hashTable.getHash(key), "S'esperava que fossin de diferent bucket");

        // Insereixo l'element per després fer la prova
        hashTable.put(key, value);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        // System.out.printf(hashTable.toString());
        // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
        value = "canvio el value del key";
        hashTable.put(key, value);

        // El count no hauria de canviar perque s'ha realitzat un UPDATE sobre un element, no afegit.
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        // Si es realitza un get del key hauria de donar el valor de value
        assertEquals(value, hashTable.get(key), "S'esperava que el valor del key");

        // Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
        String segonaKey;
        segonaKey = "temari";
        // fent el hash de temari m'he adonat compte de que el getHash pot donar negatius per tant peta el meu test.
        // System.out.printf("Hash de la primera: %s\nHash de la segona: %s\nHash de la tercera: %s\n", hashTable.getHash(primera), hashTable.getHash(key), hashTable.getHash(segonaKey));
        assertNotEquals(hashTable.getHash(primera), hashTable.getHash(segonaKey), "S'esperava que fossin de diferent bucket");
        assertNotEquals(hashTable.getHash(key), hashTable.getHash(segonaKey), "esperava que fossin de diferent bucket");
        // Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket
        // System.out.printf(hashTable.toString());
        hashTable.drop(segonaKey);
        // System.out.printf(hashTable.toString());
        // Com hem eliminat un element que no existeix no s'hauria de haver fet res. Per tant, no s'hauria d'haver restat el compte
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void testComptarUpdateElementSiColisionaTaulaNoBuidaIEliminarElementNoExisteixTotIquePosOcupada() {
        HashTable hashTable = new HashTable();

        // El compte ha de ser 0, no hi ha res encar
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));

        String key;
        String value;
        key = "prova";
        value = "key value";
        hashTable.put(key, value);

        // El compte ha de ser 1
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        String segona;
        String segonaValue;
        segona = hashTable.getCollisionsForKey(key);
        segonaValue = "m03";
        hashTable.put(segona, segonaValue);

        // El compte ha de ser 2
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
        value = "actualizant key value";
        hashTable.put(key, value);
        // El compte ha de seguir sent 2
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        String expected;
        String actual;

        expected = HashTableTestHelper.formatExpectedEntry(key, value);
        expected += HashTableTestHelper.formatChainedEntry(segona, segonaValue);

        actual = hashTable.toString();

        // System.out.printf("EXPECTED: %s\n", expected);
        // System.out.printf("ACTUAL: %s\n", actual);
        assertEquals(expected, actual, "El resultat no es el que s'esperava. \nExpected:" + expected + "\nActual: " + actual);


        String unExtra;
        String unExtraValue;

        unExtra = "temari";
        unExtraValue = "key value";
        assertNotEquals(hashTable.getHash(key), hashTable.getHash(unExtra), "S'esperava que el hash fos diferent");
        // Si es passa la comprovacio anterior del assertNotEquals vol dir que sera un element que no col·Lisiona amb res mes.
        // Afegeixo aquest element
        hashTable.put(unExtra, unExtraValue);

        // actual = hashTable.toString();
        // System.out.printf("ACTUAL: %s\n", actual);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        // Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        // Utilitzo l'ultim element afegit per buscar una key que col·lisioni i passar-la al drop, d'aquesta manera
        // compleixo el cas de eliminar un que no existix. Perque no l'he creat.
        hashTable.drop(hashTable.getCollisionsForKey(unExtra));

        // actual = hashTable.toString();
        // System.out.printf("ACTUAL: %s\n", actual);
        // System.out.printf(hashTable.get(unExtra));
        assertNotNull(hashTable.get(unExtra), "S'esperava poder recuperar el valor de unExtra");
        // El compte ha de seguir sent 3
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
     */
}
