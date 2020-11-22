package aima.gui.nqueens.csp;

import aima.core.search.csp.Variable;
/**
 * 
 * @author Diego Marco, 755232
 *
 */
public class NQueensVariable extends Variable{
	private int col; //Columna [0,7]
	
	//Representa una reina en la columna "col"
	NQueensVariable(String _name, int _col) {
		super(_name);
		this.col=_col;
	}
	
	
	public void setCol(int _col) {
		this.col = _col;
	}
	
	public int getCol() {
		return this.col;
	}
}
