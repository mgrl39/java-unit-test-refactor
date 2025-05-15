package ex2;

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
}
