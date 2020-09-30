package aima.core.environment.fichas;


import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.fichas.FichasBoard;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

public class FichasFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;
	
	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}
	
	private static class EPActionsFunction implements ActionsFunction { //ACCIONES POSIBLES
		public Set<Action> actions(Object state) {
			FichasBoard board = (FichasBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FichasBoard.GapRightJump0)) {
				actions.add(FichasBoard.GapRightJump0);
			}
			if (board.canMoveGap(FichasBoard.GapRightJump1)) {
				actions.add(FichasBoard.GapRightJump1);
			}
			if (board.canMoveGap(FichasBoard.GapRightJump2)) {
				actions.add(FichasBoard.GapRightJump2);
			}
			if (board.canMoveGap(FichasBoard.GapLeftJump0)) {
				actions.add(FichasBoard.GapLeftJump0);
			}
			if (board.canMoveGap(FichasBoard.GapLeftJump1)) {
				actions.add(FichasBoard.GapLeftJump1);
			}
			if (board.canMoveGap(FichasBoard.GapLeftJump2)) {
				actions.add(FichasBoard.GapLeftJump2);
			}

			return actions;
		}
	}
	
	private static class EPResultFunction implements ResultFunction { //FUNCIÓN DE TRANSICIÓN ESTADO,ACCION -> ESTADO
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.GapRightJump0.equals(a)
					&& board.canMoveGap(FichasBoard.GapRightJump0)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRightJump0();
				return newBoard;
			} else if (FichasBoard.GapRightJump1.equals(a)
					&& board.canMoveGap(FichasBoard.GapRightJump1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRightJump1();
				return newBoard;
			} else if (FichasBoard.GapRightJump2.equals(a)
					&& board.canMoveGap(FichasBoard.GapRightJump2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRightJump2();
				return newBoard;
			} else if (FichasBoard.GapLeftJump0.equals(a)
					&& board.canMoveGap(FichasBoard.GapLeftJump0)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeftJump0();
				return newBoard;
			} else if (FichasBoard.GapLeftJump1.equals(a)
				&& board.canMoveGap(FichasBoard.GapLeftJump1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeftJump1();
				return newBoard;
			} else if (FichasBoard.GapLeftJump2.equals(a)
				&& board.canMoveGap(FichasBoard.GapLeftJump2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeftJump2();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}

}
