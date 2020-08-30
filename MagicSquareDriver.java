/*
 * @author JalenNall
 * driver class
 */
public class MagicSquareDriver {
static boolean check;
	public static void main(String[] args) throws Exception {
		if(args[0].equals("-check")) {
			check = MagicSquare.checkMatrix(args[1]);
		}
		
		
		else if (args[0].equals("-create")){
			int n = Integer.parseInt(args[2]);
			if(n%2!=0) {
			MagicSquare.createMagicSquare(args[1],n);
		}
			else {
				System.out.print(" error, you picked an even number");
			}
		}
	else { 
		System.out.println("Usage: java MagicDriver [-check | -create] [filename] |size]");
	}
}
}
