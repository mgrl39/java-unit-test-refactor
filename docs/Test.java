public class Test {
	public static void main(String args[]) {
		int a = -3;
		int b = 16;

		System.out.println(-3 % 16);            // -3  (malament per a índexs d'array)
		System.out.println(Math.floorMod(-3, 16)); // 13 (correcte per a índexs d'array)

		System.out.println(-18 % 7);
		System.out.println(-3 % 16);
	}

}
