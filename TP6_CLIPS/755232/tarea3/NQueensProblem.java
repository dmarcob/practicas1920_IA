package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.gui.sudoku.csp.AvailableCells;
import aima.gui.sudoku.csp.SudokuConstraint;
import aima.gui.sudoku.csp.SudokuVariable;  

/**
 * 
 * @author Diego Marco, 755232
 *
 */
public class NQueensProblem extends CSP{
	private static final int cells = 64;
	private static List<Variable> variables = null;
	
	private static List<Variable> collectVariables() {
		variables = new ArrayList<Variable>();
		for (int i = 0; i < 8; i++) {
                 variables.add(new NQueensVariable("Reina en columna [" + i + "]", i));
         }
		 return variables;
	}
	
	private static List<Integer> getNQueensDomain(NQueensVariable var) {
        List<Integer> list = new ArrayList<Integer>();
        //Una variable (reina en columna) tiene asociada un dominio de [0,7] filas
        for (int i = 0; i < 8; i++)
             list.add(new Integer(i));
        return list;
    }
	
	public NQueensProblem() {
	     //variables
        super(collectVariables());
        for (int i=0;i<8;i++) {
             NQueensVariable x = (NQueensVariable) variables.get(i);
        }
        //Define dominios de variables
        Domain domain;
        for (Variable var : getVariables()) {
             domain = new Domain(getNQueensDomain((NQueensVariable) var));
             setDomain(var, domain);
        }
        //restricciones
        doConstraint();
	}
       
	 private void doConstraint() {
		 //Se añade una restricción por cada par de variables distinto.
		 //Estas restricciones serán satisfechas si las variables se encuentran en 
		 //filas, columnas y diagonales distintas.
         for (int i = 0; i < 8; i++) {
           	 for (int j = i+1; j <8; j++) {
           			 addConstraint(new NQueensConstraint(variables.get(i), variables.get(j)));
           	 }
          }
      }
   
	
	 
}
