package aima.gui.sudoku.csp;

import java.io.FileNotFoundException;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.SolutionStrategy;
/**
 * 
 * @author Diego Marco, 755232
 *
 */

public class SudokuApp {
	public static void main(String[] args) throws FileNotFoundException {
		String path = new String("/home/diego/Escritorio/practicas1920_IA/TP6_CLIPS/");
		Sudoku [] s1 = Sudoku.listaSudokus2(path + "easy50.txt");
		Sudoku [] s2 = Sudoku.listaSudokus2(path + "top95.txt");
		Sudoku [] s3 = Sudoku.listaSudokus2(path + "hardest.txt");
		Sudoku [] lista = union(union(s1, s2), s3);
		
		int solucionados = 0;
		for (Sudoku s : lista) {
			System.out.println("---------"); 
			s.imprimeSudoku();
			if (!s.completo()) {
				CSP csp = new SudokuProblem(s.pack_celdasAsignadas());
				SolutionStrategy strategy = new ImprovedBacktrackingStrategy(true, true, true, true);
				double start = System.currentTimeMillis();
				Assignment sol = strategy.solve(csp);
				double end = System.currentTimeMillis();
				Sudoku sn = new Sudoku(sol);
				System.out.println("Time to solve = " + ((end - start) / 1000) + "segundos");
				System.out.println(sol);
				sn.imprimeSudoku();
				solucionados++;
				System.out.println("Sudoku solucionado correctamente");
			}
		}
		System.out.println("+++++++++");
		System.out.println("Numero sudokus solucionados:" + solucionados);

		

	}
	
	private static Sudoku[] union (Sudoku[] s1, Sudoku[] s2) {
		Sudoku[] result = new Sudoku[s1.length + s2.length];
		int contador = 0;
		for(Sudoku s : s1) {
			result[contador] = s;
			contador ++;
		}
		for(Sudoku s : s2) {
			result[contador] = s;
			contador++;
		}
		return result;
	}
}
