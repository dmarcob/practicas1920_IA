package aima.gui.sudoku.csp;

import aima.core.search.csp.Variable;

public class SudokuVariable extends Variable{
	private int x;
	private int y;
	private int value;
	
	SudokuVariable(String _name, int _x, int _y) {
		super(_name);
		this.x = _x;
		this.y=_y;
		this.value=0;
	}
	
	public void setValue(int _value) {
		this.value = _value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
