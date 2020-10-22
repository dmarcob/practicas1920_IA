package aima.core.environment.fichas;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
/**
 * @author Diego Marco, 755232
 * 
 */
public class FichasBoard {
	//Mover el hueco a la derecha y la ficha permutada no salta
	public static Action GapRightJump0 = new DynamicAction("GapRightJump0");
		
	//Mover el hueco a la derecha y la ficha permutada salta una vez
	public static Action GapRightJump1 = new DynamicAction("GapRightJump1");
		
	//Mover el hueco a la derecha y la ficha permutada salta dos veces
	public static Action GapRightJump2 = new DynamicAction("GapRightJump2");
		
	//Mover el hueco a la izquierda y la ficha permutada no salta
	public static Action GapLeftJump0 = new DynamicAction("GapLeftJump0");
		
	//Mover el hueco a la izquierda y la ficha permutada salta una vez
	public static Action GapLeftJump1 = new DynamicAction("GapLeftJump1");
		
	//Mover el hueco a la izquierda y la ficha permutada salta dos veces
	public static Action GapLeftJump2 = new DynamicAction("GapLeftJump2");

	public char[] state;
		
	//'B' = Fichas tipo B
	//'V' = Fichas tipo V
	//' ' = Espacio
	public FichasBoard() {
		state = new char[] {'B','B','B',' ','V','V','V'};
	}
		
	public FichasBoard(char[] state) {
		this.state = new char[state.length];
		System.arraycopy(state,  0, this.state, 0, state.length);
	}
		
	public FichasBoard(FichasBoard copyBoard) {
		this(copyBoard.getState());
	}
	
	public char[] getState() {
		return this.state;
	}
	
	
	//TODO: IMPLEMENTAR CLASE DESDE AQUÍ
	
	//El hueco se mueve a la derecha
	public void moveGapRightJump0 () {
		int gapPos = getGapPosition();
		if (gapPos < 6) {
			permute(gapPos, gapPos + 1);
		}
	}
	
	//El hueco se mueve a la derecha y la ficha salta una vez hacia la izquierda
	public void moveGapRightJump1 () {
		int gapPos = getGapPosition();
		if (gapPos < 6 && gapPos > 0) {
			permute(gapPos, gapPos + 1);
			gapPos = getGapPosition(); //Actualizo posición del hueco
			permute(gapPos - 1, gapPos - 2);
		}
	}
	
	//El hueco se mueve a la derecha y la ficha salta dos veces hacia la izquierda
	public void moveGapRightJump2() {
		int gapPos = getGapPosition();
		if (gapPos < 6 && gapPos > 1) {
			permute(gapPos, gapPos + 1);
			gapPos = getGapPosition(); //Actualizo posición del hueco
			permute(gapPos - 1, gapPos - 2);
			permute(gapPos - 2, gapPos - 3);
		}
	}
	
	//El hueco se mueve a la izquierda
	public void moveGapLeftJump0 () {
		int gapPos = getGapPosition();
		if (gapPos > 0) {
			permute(gapPos, gapPos - 1);
		}
	}

	//El hueco se mueve a la izuierda y la ficha salta una vez hacia la derecha
		public void moveGapLeftJump1 () {
			int gapPos = getGapPosition();
			if (gapPos > 0 && gapPos < 6) {
				permute(gapPos, gapPos - 1);
				gapPos = getGapPosition(); //Actualizo posición del hueco
				permute(gapPos + 1, gapPos + 2);
			}
		}
	
	//El hueco se mueve a la izquierda y la ficha salta dos veces hacia la derecha
	public void moveGapLeftJump2() {
		int gapPos = getGapPosition();
		if (gapPos > 0 && gapPos < 5 ) {
			permute(gapPos, gapPos - 1);
			gapPos = getGapPosition(); //Actualizo posición del hueco
			permute(gapPos + 1, gapPos + 2);
			permute(gapPos + 2, gapPos + 3);
		}
	}	
	
	
	
	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int gapPos = getGapPosition();
		if (where.equals(GapRightJump0))
			retVal = gapPos < 6;
		else if(where.equals(GapRightJump1))
			retVal = gapPos < 6 && gapPos > 0;
		else if(where.equals(GapRightJump2))
			retVal = gapPos < 6 && gapPos > 1;
		else if(where.equals(GapLeftJump0))
			retVal = gapPos > 0;
		else if(where.equals(GapLeftJump1))
			retVal = gapPos > 0 && gapPos < 6;
		else if(where.equals(GapLeftJump2))
			retVal = gapPos > 0 && gapPos < 5;
		return retVal;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichasBoard other = (FichasBoard) obj;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String sep = new String("|");
		StringBuilder retVal = new StringBuilder(sep);
		for (int i = 0; i <=6; i++) {
			retVal.append(state[i] + sep);
		}
		return retVal.toString();
	}
	
	//PRIVATE METHODS
	
	private int getGapPosition() {
		int retVal = -1;
		for (int i = 0; i <= 6; i++) {
			if (state[i] == ' ') {
				retVal = i;
			}
		}
		return retVal;
	}
	
	private void permute(int a, int b) {
		if (a >= 0 && a <= 6 && b >= 0 && b<=6) {
			char aux = state[b];
			state[b] = state[a];
			state[a] = aux;
		}
	}
	
}
