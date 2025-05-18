package ex4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashTableTestHelper {

    public static final int HASH_TABLE_SIZE = 16;

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

    public static String valueSizeNotCorrect(int expected, int actual) {
        return "El valor del size no es correcte. \nExpected:" + expected + "\nActual: " + actual;
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

    public static HashTable createTableWithCollisions(String baseKey, String baseValue, int numCollisions, Map<String, String> collisionKeyValueMap) {
        HashTable ht = new HashTable();
        ht.put(baseKey, baseValue);

        ArrayList<String> colls = ht.getCollisionsForKey(baseKey, numCollisions);
        for (int i = 0 ; i < numCollisions ; i++) {
            String colKey = colls.get(i);
            String colValue = baseValue + "_" + (i + 2);
            ht.put(colKey, colValue);
            collisionKeyValueMap.put(colKey, colValue);
        }
        return ht;
    }

    public static String formatBucketChainFromList(String baseKey, String baseValue, List<Map.Entry<String, String>> entries) {
        String[] pairs = new String[entries.size() * 2];
        for (int i = 0; i < entries.size(); i++) {
            pairs[i * 2] = entries.get(i).getKey();
            pairs[i * 2 + 1] = entries.get(i).getValue();
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

    public static String[] mapToKeyValueArray(Map<String, String> map) {
        String[] result = new String[map.size() * 2];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result[i++] = entry.getKey();
            result[i++] = entry.getValue();
        }
        return result;
    }

    public static ArrayList<Map.Entry<String, String>> getCollisionEntries(Map<String, String> map) {
        return new ArrayList<>(map.entrySet());
    }

    public static String getUnusedCollisionKey(HashTable table, String baseKey, Map<String, String> usedKeys) {
        List<String> candidates = table.getCollisionsForKey(baseKey, 10);
        for (String candidate : candidates) {
            if (!usedKeys.containsKey(candidate) && !candidate.equals(baseKey)) {
                return candidate;
            }
        }
        throw new RuntimeException("No col·lisions noves s'han trobat");
    }

}
