# EX2 Explicació
## Estructura interna
```java
private HasEntry[] entries = new HashEntry[SIZE];
private int SIZE = 16;
```
Aqui tenim un array de 16 "buckets" (`entries[0]` fins `entries[15]`)
Cada bucket pot contenir una llista enllaçada (per col·lisions).

## put(key, value)
Aquest calcula la posicio del bucket on hauria de anar.
```java
int hash = getHash(key); // return key.hashCode() % SIZE; (SIZE == 16)
```
- Si el bucket està buit --> hi posa l'element.
- Si hi ha algun element ja --> Va al final de la llista enllaçada i hi afegeix el nou

## hashCode
Quan fem `put("abc", "123)`, la taula ha de saber a quin lloc (bucket) posar-ho.

## toString
- Retorna: `bucket[INDEX] = [clau, valor] -> [clau2, valor2] -> ...`
- Exemple sense col·lisions: `bucket[2] = [abc, 123]`
- Exemple amb col·lisions: `bucket[7] = [abc, 123] -> [def, 456] -> [xyz, 789]`
