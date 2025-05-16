package ex2;

import java.util.ArrayList;
import java.util.Map;

public class HashTableTestHelper {
    public static int getBucketIndex(String k) {
        return Math.floorMod(k.hashCode(), 16);
    }

    public static String formatExpectedEntry(String key, String value) {
        return ("\n bucket[" + getBucketIndex(key) + "] = [" + key + ", " + value + "]");
    }

    public static String formatChainedEntry(String key, String value) {
        return " -> [" + key + ", " + value + "]";
    }

    public static String valueCountNotCorrect(int expected, int actual) {
        return "El valor del compte no es correcte. \nExpected:" + expected + "\nActual: " + actual;
    }

    /* Crea una taula de hash buida */
    public static HashTable createEmptyTable() {
        return new HashTable();
    }

    /* Crea una taula amb un unic element */
    public static HashTable createTableWithOneElement(String key, String value) {
        HashTable ht = new HashTable();
        ht.put(key, value);
        return ht;
    }

    /* Crea una taula amb múltiples elements */
    public static HashTable createTableWithMultipleElements(Map<String, String> map) {
        HashTable ht = new HashTable();
        map.forEach(ht::put);
        return ht;
    }

    public static HashTable createTableWithCollisions(String baseKey, String baseValue, int numCollisions, ArrayList<String> collisionKeysOut) {
        HashTable ht = new HashTable();
        ht.put(baseKey, baseValue);

        ArrayList<String> colls = ht.getCollisionsForKey(baseKey, numCollisions);
        for (int i = 0 ; i < numCollisions ; i++) {
            String colKey = colls.get(i);
            ht.put(colKey, baseValue);
            collisionKeysOut.add(colKey);
        }
        return ht;
    }

    public static String formatBucketChainFromList(String baseKey, String baseValue, ArrayList<String> keys, String value) {
        String[] pairs = new String[keys.size() * 2];
        for (int i = 0; i < keys.size(); i++) {
            pairs[i * 2] = keys.get(i);
            pairs[(i * 2) + 1] = value;
        }
        return formatBucketChain(baseKey, baseValue, pairs);
    }

    /**
     * Retorna el format complet d'un bucket amb multiples claus i valors coL·lisionats.
     * El primer element defineix el bucket (amb index), els següents són afegits a la cadena.
     */
    public static String formatBucketChain(String firstKey, String firstValue, String... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) throw new IllegalArgumentException("Cal passar parrels de clau i valors");

        StringBuilder sb = new StringBuilder();
        sb.append("\n bucket[").append(getBucketIndex(firstKey)).append("] = [")
                .append(firstKey).append(", ").append(firstValue).append("]");

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i];
            String value = keyValuePairs[i + 1];
            sb.append(formatChainedEntry(key, value));
        }
        return sb.toString();
    }

    public static String errorMessage(String expected, String actual) {
        return "\nLa taula no conté l'element esperat.\nExpected:\n" + expected + "\nActual:\n" + actual;
    }


}
