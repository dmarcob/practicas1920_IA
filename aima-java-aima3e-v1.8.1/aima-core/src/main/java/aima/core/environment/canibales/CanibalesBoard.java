package aima.core.environment.canibales;


import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
/**
 * @author Diego Marco, 755232
 */

public class CanibalesBoard {		
	
	//Mover a la derecha

	public static Action Mover1Cd = new DynamicAction("Mover1Cd");		//Mover 1 canival derecha
	public static Action Mover1Md = new DynamicAction("Mover1Md"); 		//Mover 1 misionero derecha
	public static Action Mover2Cd = new DynamicAction("Mover2Cd");		//Mover 2 canivales derecha
	public static Action Mover2Md = new DynamicAction("Mover2Md");		//Mover 2 misioneros derecha
	public static Action Mover1M1Cd = new DynamicAction("Mover1M1Cd");	//MOver 1 misionero y 1 canibal derecha
	//Mover a la izquierda
	public static Action Mover1Ci = new DynamicAction("Mover1Ci");		//Mover 1 canival izquierda
	public static Action Mover1Mi = new DynamicAction("Mover1Mi"); 		//Mover 1 misionero izquierda
	public static Action Mover2Ci = new DynamicAction("Mover2Ci");		//Mover 2 canivales izquierda
	public static Action Mover2Mi = new DynamicAction("Mover2Mi");		//Mover 2 misioneros izquierda
	public static Action Mover1M1Ci = new DynamicAction("Mover1M1Ci");	//MOver 1 misionero y 1 canival izquierda
	
	private int [] state;
	
	//
	// PUBLIC METHODS
	//
	
	
	
	/* CODIFICACIÓN DEL ESTADO
	 * state [0] -> Número de misioneros ribera izq
	 * state [1] -> Número de caníbales ribera izq
	 * state [2] -> si 1, hay bote (B) orilla izq. SI 0, hay bote (B) orilla dcha
	 * state [3] -> Número de misioneros ribera dcha
	 * state [4] -> Número de caníbales ribera dcha
	 */
	
	//Constructor por defecto
	public CanibalesBoard() {
		state = new int[] {3,2,1,0,1};
	}
	
	//Constructor 2
	public CanibalesBoard(int[] state) {
		this.state = new int[state.length];
		//Copia el array state desde el índice 0 en el array interno de la clase
		//desde el índice 0.
		System.arraycopy(state, 0, this.state, 0, state.length);
	}	
	
	//Constructor 3
	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState());
	}
	
	//Devuelve el estado 
	public int[] getState() {
		return state;
	}
	
	
	//************************************
	//TODOS LOS MOVIMIENTOS POSIBLES
	//************************************
	
	//Mover un canibal a la derecha
	public void mover1Cd() {
		//Puede mover si hay por lo menos un canibal en la orilla izquierda y el bote está en la orilla izquierda.
		//En la orilla derecha hay mínimo un misionero más que caníbales o no hay nadie
		
		//Pre-condición = Ci >= 1 AND B = 1 AND (Md >= Cd + 1 OR Md == 0)
		//Resultado = B = 0, Ci = Ci - 1, Cd = Cd + 1
		
		if ((state[1] >= 1) && (state[2] == 1) && ((state[3] >= state[4] + 1) || (state[3] == 0))) {
			state[2] = 0;
			state[1]--;
			state[4]++;
		}
	}
	
	//Mover un misionero a la derecha
	public void mover1Md() {
		//Puede mover si hay por lo menos un misionero en la orilla izquierda y el bote está en la orilla izquierda.
		//En la orilla izquierda hay mínimo un misionero más que caníbales o solo un misionero.
		//En la orilla derecha puede haber un canibal o ninguno (restringido por la pre-condicion de mover1Cd())
	
		//Pre-condicion = Mi >= 1 AND B = 1 AND (Mi >= Ci + 1 OR  Mi = 1) AND Md >= Cd - 1	
		//Resultado = B=0, Mi = Mi -1, Md = Md + 1
		
		if((state[0] >= 1) && (state[2] == 1) && ((state[0] >= state[1] + 1) || (state[0] == 1)) && (state[3] >= state[4] - 1)) {
			state[2] = 0;
			state[0]--;
			state[3]++;
		}
	}
	
	//Mover dos canibales a la derecha
	public void mover2Cd() {
		//Puede mover si hay por lo menos dos caníbales en la orilla izquierda y el bote está en la orilla izquierda.
		//En la orilla derecha hay mínimo dos misioneros más que caníbales o no hay nadie
		
		//Pre-condición = Ci >= 2 AND B = 1 AND (Md >= Cd + 2 OR Md == 0)
		//Resultado = B = 0, Ci = Ci - 2, Cd = Cd + 2
		
		if ((state[1] >= 2) && (state[2] == 1) && ((state[3] >= state[4] + 2) || (state[3] == 0))) {
			state[2] = 0;
			state[1] = state[1] - 2;
			state[4] = state[4] + 2;
		}
	}
	
	
	//Mover dos misioneros a la derecha
	public void mover2Md() {
		//Puede mover si hay por lo menos dos misioneros en la orilla izquierda y el bote está en la orilla izquierda.
		//En la orilla izquierda hay mínimo dos misioneros más que caníbales o solo dos misioneros.
		//En la orilla derecha puede haber dos canibales, uno o ninguno (restringido por la pre-condicion de mover2Cd())

		//Pre-condicion = Mi >= 2 AND B = 1 AND (Mi >= Ci + 2 OR  Mi = 2) AND Md >= Cd - 2	
		//Resultado = B=0, Mi = Mi -2, Md = Md + 2
		
		if((state[0] >= 2) && (state[2] == 1) && ((state[0] >= state[1] + 2) || (state[0] == 2)) && (state[3] >= state[4] - 2)) {
			state[2] = 0;
			state[0] = state[0] - 2;
			state[3] = state[3] + 2;
		}
	}
	
	//Mover un misionero y un canibal a la derecha
	public void mover1M1Cd() {
		//Puede mover si hay por lo menos un canibal y misionero en la orilla izquierda y el bote está en la orilla izquierda.
		//En la orilla derecha tine que haber igual o más misioneros que caníbales

		//Pre-condicion = Ci >= 1 AND Mi >=1 AND B=1 AND Md >= Cd
		//Resultado= B=0, Mi = Mi - 1, Ci = Ci -1, Md = Md + 1, Cd = Cd + 1
		if((state[1]>=1) && (state[0] >=1) && (state[2] == 1) && (state[3] >= state[4])) {
			state[2] = 0;
			state[0]--;
			state[1]--;
			state[3]++;
			state[4]++;
		}
	}
	
	
	//Mover un canibal a la izquierda
	public void mover1Ci() {
		//Puede mover si hay por lo menos un canibal en la orilla derecha y el bote está en la orilla derecha.
		//En la orilla izquierda hay mínimo un misionero más que caníbales o no hay nadie
		
		//Pre-condición = Cd >= 1 AND B = 0 AND (Mi >= Ci + 1 OR Mi == 0)
		//Resultado = B = 1, Cd = Cd - 1, Ci = Ci + 1
		
		if ((state[4] >= 1) && (state[2] == 0) && ((state[0] >= state[1] + 1) || (state[0] == 0))) {
			state[2] = 1;
			state[4]--;
			state[1]++;
		}
	}
	
	//Mover un misionero a la izquierda
		public void mover1Mi() {
			//Puede mover si hay por lo menos un misionero en la orilla derecha y el bote está en la orilla derecha.
			//En la orilla derecha hay mínimo un misionero más que caníbales o solo un misionero.
			//En la orilla izquierda puede haber un canibal o ninguno (restringido por la pre-condicion de mover1Ci())
		
			//Pre-condicion = Md >= 1 AND B = 0 AND (Md >= Cd + 1 OR  Md = 1) AND Mi >= Ci - 1	
			//Resultado = B=1, Md = Md -1, Mi = Mi + 1
			
			if((state[3] >= 1) && (state[2] == 0) && ((state[3] >= state[4] + 1) || (state[3] == 1)) && (state[0] >= state[1] - 1)) {
				state[2] = 1;
				state[3]--;
				state[0]++;
			}
		}
	
		//Mover dos canibales a la izquierda
		public void mover2Ci() {
			//Puede mover si hay por lo menos dos caníbales en la orilla derecha y el bote está en la orilla derecha.
			//En la orilla izquierda hay mínimo dos misioneros más que caníbales o no hay nadie
			
			//Pre-condición = Cd >= 2 AND B = 0 AND (Mi >= Ci + 2 OR Mi == 0)
			//Resultado = B = 1, Cd = Cd - 2, Ci = Ci + 2
			
			if ((state[4] >= 2) && (state[2] == 0) && ((state[0] >= state[1] + 2) || (state[0] == 0))) {
				state[2] = 1;
				state[4] = state[4] - 2;
				state[1] = state[1] + 2;
			}
		}
		
		//Mover dos misioneros a la izquierda
		public void mover2Mi() {
			//Puede mover si hay por lo menos dos misioneros en la orilla derecha y el bote está en la orilla derecha.
			//En la orilla derecha hay mínimo dos misioneros más que caníbales o solo dos misioneros.
			//En la orilla izquierda puede haber dos canibales, uno o ninguno (restringido por la pre-condicion de mover2Ci())

			//Pre-condicion = Md >= 2 AND B = 0 AND (Md >= Cd + 2 OR  Md = 2) AND Mi >= Ci - 2	
			//Resultado = B=1, Md = Md -2, Mi = Mi + 2
			
			if((state[3] >= 2) && (state[2] == 0) && ((state[3] >= state[4] + 2) || (state[3] == 2)) && (state[0] >= state[1] - 2)) {
				state[2] = 1;
				state[3] = state[3] - 2;
				state[0] = state[0] + 2;
			}
		}
		
		//Mover un misionero y un canibal a la izquierda
		public void mover1M1Ci() {
			//Puede mover si hay por lo menos un canibal y misionero en la orilla derecha y el bote está en la orilla derecha.
			//En la orilla izquierda tine que haber igual o más misioneros que caníbales

			//Pre-condicion = Cd >= 1 AND Md >=1 AND B=0 AND Mi >= Ci
			//Resultado= B=1, Md = Md - 1, Cd = Cd -1, Mi = Mi + 1, Ci = Ci + 1
			if((state[4]>=1) && (state[3] >=1) && (state[2] == 0) && (state[0] >= state[1])) {
				state[2] = 1;
				state[3]--;
				state[4]--;
				state[0]++;
				state[1]++;
			}
		}
		
		
	//RESTRICCIÓN DE UNA ACCIÓN
		public boolean canMove(Action where) {
			boolean retVal = false; //Por defecto movimiento inválido
			if(where.equals(Mover1Cd)) {
					//Pre-condición = Ci >= 1 AND B = 1 AND (Md >= Cd + 1 OR Md == 0)
					retVal = (state[1] >= 1) && (state[2] == 1) && ((state[3] >= state[4] + 1) || (state[3] == 0));
			}
			else if(where.equals(Mover1Md)) {
				//Pre-condicion = Mi >= 1 AND B = 1 AND (Mi >= Ci + 1 OR  Mi = 1) AND Md >= Cd - 1	
				retVal = (state[0] >= 1) && (state[2] == 1) && ((state[0] >= state[1] + 1) || (state[0] == 1)) && (state[3] >= state[4] - 1);
			}
			else if(where.equals(Mover2Cd)) {				
				//Pre-condición = Ci >= 2 AND B = 1 AND (Md >= Cd + 2 OR Md == 0)
				retVal = (state[1] >= 2) && (state[2] == 1) && ((state[3] >= state[4] + 2) || (state[3] == 0));
				
			}
			else if(where.equals(Mover2Md)) {				
				//Pre-condicion = Mi >= 2 AND B = 1 AND (Mi >= Ci + 2 OR  Mi = 2) AND Md >= Cd - 2	
				retVal = (state[0] >= 2) && (state[2] == 1) && ((state[0] >= state[1] + 2) || (state[0] == 2)) && (state[3] >= state[4] - 2);
				
			}
			else if(where.equals(Mover1M1Cd)) {
				//Pre-condicion = Ci >= 1 AND Mi >=1 AND B=1 AND Md >= Cd
				retVal = (state[1]>=1) && (state[0] >=1) && (state[2] == 1) && (state[3] >= state[4]);
			}
			else if(where.equals(Mover1Ci)) {
				//Pre-condición = Cd >= 1 AND B = 0 AND (Mi >= Ci + 1 OR Mi == 0)
				retVal = (state[4] >= 1) && (state[2] == 0) && ((state[0] >= state[1] + 1) || (state[0] == 0));
			}
			else if(where.equals(Mover1Mi)) {
				//Pre-condicion = Md >= 1 AND B = 0 AND (Md >= Cd + 1 OR  Md = 1) AND Mi >= Ci - 1				
				retVal= (state[3] >= 1) && (state[2] == 0) && ((state[3] >= state[4] + 1) || (state[3] == 1)) && (state[0] >= state[1] - 1);
			}
			else if(where.equals(Mover2Ci)) {
				//Pre-condición = Cd >= 2 AND B = 0 AND (Mi >= Ci + 2 OR Mi == 0)
				retVal = (state[4] >= 2) && (state[2] == 0) && ((state[0] >= state[1] + 2) || (state[0] == 0));
			}
			else if(where.equals(Mover2Mi)) {
				//Pre-condicion = Md >= 2 AND B = 0 AND (Md >= Cd + 2 OR  Md = 2) AND Mi >= Ci - 2	
				retVal = (state[3] >= 2) && (state[2] == 0) && ((state[3] >= state[4] + 2) || (state[3] == 2)) && (state[0] >= state[1] - 2);
			}
			else if(where.equals(Mover1M1Ci)) {
				//Pre-condicion = Cd >= 1 AND Md >=1 AND B=0 AND Mi >= Ci
				retVal = (state[4]>=1) && (state[3] >=1) && (state[2] == 0) && (state[0] >= state[1]);
			}
			return retVal;
		}

		//SOBRECARGA DE OPERADORES
		
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
			CanibalesBoard other = (CanibalesBoard) obj;
			if (!Arrays.equals(state, other.state))
				return false;
			return true;
		}
	
		@Override
		public String toString() {
			//String retVal = "RIBERA-IZQ " + state[0] + state[1] + state[2] + state[3] + state[4]; 
			String retVal = "RIBERA-IZQ ";
			//Misioneros izquierda
			if(state[0] == 3) {retVal += "M M M ";}
			else if(state[0] == 2) {retVal += "  M M ";}
			else if(state[0] == 1) {retVal += "    M ";}
			else {retVal +="      ";}
			
			//Canibales izquierda
			if(state[1] == 3) {retVal += "C C C  ";}
			else if(state[1] == 2) {retVal += "  C C  ";}
			else if(state[1] == 1) {retVal += "    C  ";}
			else {retVal += "       ";}
			
			//Bote
			if(state[2] == 1) { retVal += "BOTE --RIO--      ";}
			else {retVal += "     --RIO-- BOTE ";}
		
			//Misioneros derecha
			if(state[3] == 3) {retVal += "M M M ";}
			else if(state[3] == 2) {retVal += "  M M ";}
			else if(state[3] == 1) {retVal += "    M ";}
			else {retVal +="      ";}
			
			//Canibales derecha
			if(state[4] == 3) {retVal += "C C C  ";}
			else if(state[4] == 2) {retVal += "  C C  ";}
			else if(state[4] == 1) {retVal += "    C  ";}
			else {retVal += "      ";}
			
			retVal += "RIBERA-DCH";
			
			return retVal;
		}
		
}
	
