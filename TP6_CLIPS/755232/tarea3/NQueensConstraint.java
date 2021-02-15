package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
/**
 * 
 * @author Diego Marco, 755232
 *
 */
public class NQueensConstraint implements Constraint{
	private Variable var1;
	private Variable var2;
	private List<Variable> scope;
	
	NQueensConstraint(Variable _var1, Variable _var2) {
		this.var1=_var1; 
		this.var2=_var2;
		scope = new ArrayList<Variable>(2);
		scope.add(var1);
		scope.add(var2);
	}
	@Override
	public List<Variable> getScope() {
		return scope;
	}
	
	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		int fila1 = (int)assignment.getAssignment(var1); //Fila de var1 asignada (sacada de su dominio) 
		int fila2 = (int)assignment.getAssignment(var2); //Fila de var2 asignada (sacada de su dominio)
		int columna1 = ((NQueensVariable) var1).getCol(); //Columna asignada al construir  var1
		int columna2 = ((NQueensVariable) var2).getCol();	//Columna asignada al construir la var2

		//Se cumple restriccion si no están en la misma columna ni en la misma fila ni el misma diagonal.
		//Que no estén en la misma diagonal se comprueba si la diferencia absoluta de filas y de columnas 
		//no coincide
		return (fila1 != fila2) && (columna1 != columna2) && 
				((Math.abs(fila1 - fila2) != Math.abs(columna1 - columna2)));
	}
	
}
