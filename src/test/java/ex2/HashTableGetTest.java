package ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ex2.HashTableTestHelper.*;
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
    // TODO: he afegit extra un 4t, preguntar si afectara en alguna forma...
    @Test
    public void testObtenirElementColisioTaulaTerceraPos() {
        HashTable hashTable = new HashTable();

        String primer;
        String primerValue;
        primer = "fer";
        primerValue = "m03";
        hashTable.put(primer, primerValue);

        ArrayList<String> colls = hashTable.getCollisionsForKey(primer, 3);
        String segona;
        String segonaValue;
        segona = colls.get(0);
        segonaValue = "m05";
        hashTable.put(segona, segonaValue);

        String key;
        String value;
        String msg;
        key = colls.get(1);
        value = "m12";
        hashTable.put(key, value);

        msg = String.format(
                "La taula no ha retornat el valor esperat per la clau: %s\nValor esperat: %s\nValor retornat: %s",
                key, value, hashTable.get(key)
        );

        String quarta;
        String quartaValue;
        quarta = colls.get(2);
        quartaValue = "m00";
        hashTable.put(quarta, quartaValue);

        assertEquals(value, hashTable.get(key), msg);
    }

    /**
     * Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
     */
    @Test
    public void testObtenirElementNoExisteixPosBuida() {
        HashTable hashTable = new HashTable();

        String key;
        String msg;
        String result;

        key = "abc";
        result = hashTable.get(key);
        msg = String.format("S'esperava NULL perquè la clau %s no existeix, pero s'ha retornat %s",
                key, result);
        assertNull(result, msg);
    }

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
     */
    @Test
    public void testObtenirElementNoExisteixPosOcupadaPerAltreNoCol() {
        HashTable hashTable = new HashTable();

        String primera;
        String primeraValue;

        primera = "fer";
        primeraValue = "m03";
        hashTable.put(primera, primeraValue);

        String key;
        String msg;
        String result;

        key = hashTable.getCollisionsForKey(primera);
        result = hashTable.get(key);
        msg = String.format("S'esperava NULL perquè la clau %s no existeix, pero s'ha retornat %s",
                key, result);
        assertNull(result, msg);
    }

    /**
     * Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
     */
    @Test
    public void testObtenirElementQueNoExisteixPosOcupadaPerElementsCol() {
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
        String msg;
        String result;
        key = colls.get(2); // No s'afegeix
        result = hashTable.get(key);
        msg = String.format("S'esperava NULL perquè la clau %s no existeix, pero s'ha retornat %s",
                key, result);
        /*
        if (result != null) {
            System.out.printf("%s\n%s\n", msg, result);
        } else System.out.printf("result es null\nKey: %s\n", key);
        */
        //System.out.printf(hashTable.toString());
        assertNull(result, msg);
    }
}
