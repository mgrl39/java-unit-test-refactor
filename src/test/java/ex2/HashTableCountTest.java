package ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static ex2.HashTableTestHelper.valueCountNotCorrect;
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
    public void testComptarInserirElementNoColisionaDinsTaulaBuidaDespresEsborrar() {
        HashTable hashTable = new HashTable();

        String key;
        String value;

        key = "fer";
        value = "m03";
        // Inicialment hauria d'estar buida
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));
        // Després d'afegir un element
        hashTable.put(key, value);
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));
        // Després d'esborrar-lo
        hashTable.drop(key);
        assertEquals(0, hashTable.count(),valueCountNotCorrect(0, hashTable.count()));
    }

    /**
     * PUT:  Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
     * DROP: Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
     */
    @Test
    public void testComptarInserirElementNoColisionaDinsTaulaNoBuidaDespresAfegirAltreIEsborrarElPrimer() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;
        primera = "fer";
        primeraValue = "m03";
        // Inicialment hauria d'estar buida
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));

        // Després d'afegir un element (el primer). Per poder després inserir un element que no col·lisiona dins una taula no buida
        hashTable.put(primera, primeraValue);
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        // Després d'afegir un segon element. Aqui literalment estic afegint un element que no col·lisiona dins una taula no buida.
        String segona;
        String segonaValue;
        segona = "abc";
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        assertNotEquals(hashTable.getHash(segona), hashTable.getHash(primera), "S'esperava que fossin de diferent bucket");
        // Si el test anterior funciona: S'ha comprovat el fet de Inserir un element que no col·lisiona dins una taula no vuida (amb elements)

        // System.out.printf(hashTable.toString());
        //System.out.printf(hashTable.toString());
        /**
         *  bucket[2] = [abc, m05]
         *  bucket[3] = [fer, m03]
         */

        String key;
        String value;
        key = hashTable.getCollisionsForKey(segona);
        value = "HOLAA";
        hashTable.put(key, value);
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));
        //System.out.printf(hashTable.toString());
        /**
         *  bucket[2] = [abc, m05] -> [2, HOLAA]
         *  bucket[3] = [fer, m03]
         */
        // Ara esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.drop(segona);
        // System.out.printf(hashTable.get(key));
        // System.out.println(hashTable.get(segona));
        assertNull(hashTable.get(segona), "L'element hauria d'haver sigut esborrat");
        // Amb la assertNotNull del hashTable.get(key), encara que he tret "segona" ([abc, m05]), hauria de seguir [2, "HOLA"]
        assertNotNull(hashTable.get(key), "L'element col·lisionat hauria de continuar després de fer drop al primer del bucket");

        // S'hauria de baixar a dos elements, ja que el [abc, m05] ha sigut esborrat.
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));
    }

    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
     */
    @Test
    public void testComptarInserirElementColDinsTaulaNoBuidaSegonaPosDespresEsborrar() {
        HashTable hashTable = new HashTable();

        // Encara no hi ha elements
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));
        // Afegit i comprovar un
        String primera;
        String primeraValue;
        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        // Afegit el element que col·lisiona dins una taula no buida, que es col·loca a 2nda pos dins un mateix bucket.
        String key;
        String value;
        key = hashTable.getCollisionsForKey(primera);
        value = "HOLAA";
        hashTable.put(key, value);
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        String expected;
        String actual;

        expected = HashTableTestHelper.formatExpectedEntry(primera, primeraValue);
        expected += HashTableTestHelper.formatChainedEntry(key, value);

        actual = hashTable.toString();
        // System.out.printf("EXPECTED: %s\n",expected);
        // System.out.printf("ACTUAL: %s\n", actual);
        assertEquals(expected, actual, "No s'esperava que la llista fos d'aquesta manera. \nExpected: " + expected + "\nActual: " + actual);

        // Esborro l'element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.drop(key);
        assertNull(hashTable.get(key), "S'esperava que la clau " + key + " hagues desaparegut.");
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        expected = HashTableTestHelper.formatExpectedEntry(primera, primeraValue);
        actual = hashTable.toString();
        // System.out.printf("EXPECTED: %s\n",expected);
        // System.out.printf("ACTUAL: %s\n", actual);
        assertEquals(expected, actual, "No s'esperava que la llista fos d'aquesta manera. \nExpected: " + expected + "\nActual: " + actual);
        // Validacio que el primer node no s'ha afectat
        assertNotNull(hashTable.get(primera), "S'esperava que la primera segueixi sent accessible");
    }


    /**
     * PUT:  Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
     * DROP: Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
     */
    @Test
    public void testComptarInserirElementColDinsTaulaNoBuidaTerceraPosDespresEsborrar() {
        HashTable hashTable = new HashTable();

        // Encara no hi ha elements
        assertEquals(0, hashTable.count(), valueCountNotCorrect(0, hashTable.count()));

        // Afegeixo el primer element
        String primera;
        String primeraValue;
        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);
        // Revisar el compte (ha de ser 1)
        assertEquals(1, hashTable.count(), valueCountNotCorrect(1, hashTable.count()));

        ArrayList<String> colls = hashTable.getCollisionsForKey(primera, 3);
        String segona;
        String segonaValue;
        segona = colls.get(0);
        segonaValue = "segona value";
        hashTable.put(segona, segonaValue);
        // Revisar el compte (ha de ser 2)
        assertEquals(2, hashTable.count(), valueCountNotCorrect(2, hashTable.count()));

        String key;
        String value;
        key = colls.get(1);
        value = "Aquest l'esborrare";
        hashTable.put(key, value);
        // Revisar el compte (ha de ser 3)
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        String unExtra;
        String unExtraValue;
        unExtra = colls.get(2);
        unExtraValue = "un mes...";
        hashTable.put(unExtra, unExtraValue);
        // Revisar el compte (ha de ser 4)
        assertEquals(4, hashTable.count(), valueCountNotCorrect(4, hashTable.count()));

        // System.out.printf("ACTUAL: %s\n", hashTable.toString());

        // Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.drop(key);

        assertNull(hashTable.get(key), "S'esperava que la clau " + key + " hagues desaparegut.");
        // Revisar el compte (ha de ser 3 per l'extra que he afegit anteriorment)
        assertEquals(3, hashTable.count(), valueCountNotCorrect(3, hashTable.count()));

        String expected;
        String actual;
        expected = HashTableTestHelper.formatExpectedEntry(primera, primeraValue);
        expected += HashTableTestHelper.formatChainedEntry(segona, segonaValue);
        expected += HashTableTestHelper.formatChainedEntry(unExtra, unExtraValue);

        actual = hashTable.toString();
        // System.out.printf(actual);
        assertEquals(expected, actual, "No s'esperava que la llista fos d'aquesta manera. \nExpected: " + expected + "\nActual: " + actual);
        assertNotNull(hashTable.get(primera), "S'esperava que la primera segueixi sent accessible");
        assertNotNull(hashTable.get(segona), "S'esperava que la segona segueixi sent accessible");
        assertNotNull(hashTable.get(unExtra), "S'esperava que l'element extra segueixi sent accessible");
    }


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
     * DROP: Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */


    /**
     * PUT:  Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
     */
}
