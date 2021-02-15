package aima.gui.nqueens.csp;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.SolutionStrategy;
import aima.core.search.csp.Variable;
import aima.gui.nqueens.csp.NQueensAssignment;


public class NQueensMinConflictApp {
		public static void main(String[] args) {
			Boolean solucion = false;
			while(!solucion) {
				CSP csp = new NQueensProblem();
				SolutionStrategy strategy = new MinConflictsStrategy(50);
				double start = System.currentTimeMillis();
				Assignment as = (Assignment)strategy.solve(csp.copyDomains());
				double end = System.currentTimeMillis();
				if (as != null) {
					System.out.println(as);
					System.out.println("");
					System.out.println("Time to solve = " + ((end - start) / 1000) + "segundos");
					System.out.println("");
					imprimirTablero(as);
					solucion = true;
				}
			}
		}
		
		public static void imprimirTablero(Assignment as) {
			for(int i = 0; i < 8; i++) {
				for (Variable var : as.getVariables()) {
					NQueensVariable v = (NQueensVariable) var;
					int fila = (int)as.getAssignment(var);
					int columna = v.getCol();
					if (fila == i) System.out.print('Q');
					else System.out.print('-');
				}
				System.out.println("");
			}
		}
		
		
		
}

