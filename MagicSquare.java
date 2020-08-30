/*
 * @author JalenNall
 * 4 methods for the project
 */
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MagicSquare {
	/* instance variables*/

	private static int n;
	private static int row;
	private static int[][]ms;
	private static int diameter;
	/* methods */

	private static String readMatrix(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		diameter = input.nextInt();
		ms = new int[diameter][diameter];
		for(int i=0; i<diameter;i++) {
			for (int j=0;j<diameter;j++) {
				ms[i][j] = input.nextInt();
			}
		}
		input.close();
		return  diameter + ms.toString();
	}
	public static boolean checkMatrix(String filename) throws FileNotFoundException{
		readMatrix(filename);
		int[] spot =new int[diameter*diameter];
		int count = 0;
		int matrixInts = 0;
		int diameterNum = diameter*diameter;
		int magicNumber = (diameter*(diameterNum+1))/2;
		int sum;
		ArrayList<Integer> checker = new ArrayList<Integer>();


		//checks if all ints are there
		for (int i=0;i<ms.length;i++) {
			for (int j= 0;j<ms[row].length;j++) {
				matrixInts = ms[i][j];
				spot[count]= matrixInts;
				count++;
			}	
		}
		for (int i=0;i<spot.length;i++) {		
			checker.add(spot[i]);
		}
		for (int i=1;i<diameter*diameter;i++) {
			if(!checker.contains(i)) {
				System.out.println("The Matrix" + "\n");
				for (int j=0;j<ms.length;j++) {
					for(int k=0;k<ms[j].length;k++) {
						System.out.print(ms[j][k] + " ");
					}
					System.out.println();
				}
				System.out.println("Is not a magic Square.");
				return false;
			}

		}
		//rows
		for (int i= 0; i<ms.length;i++) {
			sum =0;
			for (int j =0; j<ms[i].length;j++) {
				sum += ms[i][j];
			}	
			if(sum != magicNumber) {
				System.out.println("The Matrix" + "\n");
				for (int j=0;j<ms.length;j++) {
					for(int k=0;k<ms[j].length;k++) {
						System.out.print(ms[j][k] + " ");
					}
					System.out.println();
				}
				System.out.println("Is not a magic Square.");
				return false;
			}
		}
		//cols
		for (int j= 0; j<ms.length;j++) {
			sum =0;
			for (int i =0; i<ms[j].length;i++) {
				sum += ms[i][j];
			}	
			if(sum != magicNumber) {
				System.out.println("The Matrix" + "\n");
				for (int z=0;z<ms.length;z++) {
					for(int k=0;k<ms[j].length;k++) {
						System.out.print(ms[j][k] + " ");
					}
					System.out.println();
				}
				System.out.println("Is not a magic Square.");
				return false;
			}
		}
		//diags
		sum = 0;
		for (int i =0; i<ms.length;i++) {
			sum+= ms[i][i];
		}
		if(sum != magicNumber) {
			System.out.println("The Matrix" + "\n");
			for (int j=0;j<ms.length;j++) {
				for(int k=0;k<ms[j].length;k++) {
					System.out.print(ms[j][k] + " ");
				}
				System.out.println();
			}
			System.out.println("Is not a magic Square.");
			return false;
		}
		//sec diag
		sum =0;
		int x1=ms.length-1;
		int y1 = 0;
		for (int i =0; i<ms.length;i++) {
			sum += ms[y1][x1];
			x1--;
			y1++;
		}
		if(sum != magicNumber) {
			System.out.println("The Matrix" + "\n");
			for (int j=0;j<ms.length;j++) {
				for(int k=0;k<ms[j].length;k++) {
					System.out.print(ms[j][k] + " ");
				}
				System.out.println();
			}
			System.out.println("Is not a magic Square.");
			return false;
		}
		System.out.println("The Matrix " + " \n" );
		for (int k = 0; k<ms.length;k++) {
			for (int l = 0; l<ms[k].length;l++) {
				System.out.print(ms[k][l] + " ");
			}
			System.out.println();
		}
		System.out.println("Is a magic Square.");
		return true;
	}




	private static void writeMatrix(String fileName) throws IOException {
		File filetext = new File(fileName);
		PrintWriter outFile = new PrintWriter(new FileWriter(filetext));
		outFile.print( n + "/n");
		for (int j=0;j<ms.length;j++) {
			outFile.println();
			for(int k=0;k<ms[j].length;k++) {
				outFile.print(ms[j][k] + " ");
			}
		}
		outFile.close();


	}


	public static void createMagicSquare(String s, int n) throws IOException {
		ms = new int[n][n];
		int row = n-1;
		int col = n/2;
		int oldrow;
		int oldcol;
		int nSquared = n*n;

		for(int i =1;i<=nSquared;i++) {
			ms[row][col] = i;
			oldrow = row;
			oldcol = col;
			row++;
			col++;
			if(row==n) {
				row=0;
			}
			if(col==n) {
				col=0;


			}
			if (ms[row][col]!=0) {
				row = oldrow;
				col = oldcol;
				row--;
			}

		}
		writeMatrix(s);
	}








}
