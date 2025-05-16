# Operador % de Java i Math.floorMod
## % de Java
Quan es fa `int resultat = -3 % 16` el resultat es -3

Si el primer nombre és negatiu, el resultat és NEGATIU:

Exemples: 
`-3 % 16` dona -3 i `-18 % 7` dona -4

El que fa Java és agafar el signe del primer operand.

```
Si fem:
a % b a Java: 
El resultat té el mateix signe que el dividend (a).
Això no és el mateix que el mòdul matemàtic, que sempre dona un resultat positiu (si el divisor és positiu).

Per tant: System.out.println(-3 % 16); // Dona -3
Perquè:
-3 dividit per 16 = 0, resta -3 (0 × 16 = 0, -3 – 0 = -3)
    El resultat manté el signe del primer operand (el dividend).

```
Java va copiar aquest comportament de C i d’altres llenguatges antics, que feien servir l’operació de resta de la divisió sencera (remainder).

## Math.floorMod
Quan es fa `int resultat = Math.floorMod(-3, 16)` el resultat es 13

(si es fa la busqueda a google tambe surt aquest valor en -3 % 16)

- Sempre retorna un valor pos dins del rang, si es 16 --> [0,15]
- Si el nombre era positiu, retorna el mateix que `%`
- Si el primer nombre era negatiu, dona el residu "positiu"

## Resum amb exemples
| Operacio | `%` | `Math.floorMod` |
| -------- | --- | --------------- |
| 5 % 16   | 5   | 5               |
| -3 % 16  | -3  | 13              |
| 17 % 16  | 1   | 1               |
| -17 % 16 | -1  | 15              |

## Conclusio curta
- Amb `%`, si el primer nombre és negatiu, el resultat també pot ser negatiu.
- Amb `Math.floorMod`, el resultat **sempre es positiu** (o zero) i perfecte per indexs d'array/hash.
