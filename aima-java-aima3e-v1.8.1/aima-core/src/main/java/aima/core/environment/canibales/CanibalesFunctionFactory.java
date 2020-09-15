package aima.core.environment.canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Diego Marco, 755232
 */
public class CanibalesFunctionFactory {
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

		private static class EPActionsFunction implements ActionsFunction {
			public Set<Action> actions(Object state) {
				CanibalesBoard board = (CanibalesBoard) state;

				Set<Action> actions = new LinkedHashSet<Action>();

				if(board.canMove(CanibalesBoard.Mover1Cd)) {
					actions.add(CanibalesBoard.Mover1Cd);
				}
				if(board.canMove(CanibalesBoard.Mover1Md)) {
					actions.add(CanibalesBoard.Mover1Md);
				}
				if(board.canMove(CanibalesBoard.Mover2Cd)) {
					actions.add(CanibalesBoard.Mover2Cd);
				}
				if(board.canMove(CanibalesBoard.Mover2Md)) {
					actions.add(CanibalesBoard.Mover2Md);
				}
				if(board.canMove(CanibalesBoard.Mover1M1Cd)) {
					actions.add(CanibalesBoard.Mover1M1Cd);
				}
				if(board.canMove(CanibalesBoard.Mover1Ci)) {
					actions.add(CanibalesBoard.Mover1Ci);
				}
				if(board.canMove(CanibalesBoard.Mover1Mi)) {
					actions.add(CanibalesBoard.Mover1Mi);
				}
				if(board.canMove(CanibalesBoard.Mover2Ci)) {
					actions.add(CanibalesBoard.Mover2Ci);
				}
				if(board.canMove(CanibalesBoard.Mover2Mi)) {
					actions.add(CanibalesBoard.Mover2Mi);
				}
				if(board.canMove(CanibalesBoard.Mover1M1Ci)) {
					actions.add(CanibalesBoard.Mover1M1Ci);
				}
				return actions;
			}
		}

		private static class EPResultFunction implements ResultFunction {
			public Object result(Object s, Action a) {
				CanibalesBoard board = (CanibalesBoard) s;

				if(CanibalesBoard.Mover1Cd.equals(a) && board.canMove(CanibalesBoard.Mover1Cd)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1Cd();
					return newBoard;
				}
				
				if(CanibalesBoard.Mover1Md.equals(a) && board.canMove(CanibalesBoard.Mover1Md)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1Md();
					return newBoard;
				}
				if(CanibalesBoard.Mover2Cd.equals(a) && board.canMove(CanibalesBoard.Mover2Cd)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover2Cd();
					return newBoard;
				}
				
				if(CanibalesBoard.Mover2Md.equals(a) && board.canMove(CanibalesBoard.Mover2Md)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover2Md();
					return newBoard;
				}
				if(CanibalesBoard.Mover1M1Cd.equals(a) && board.canMove(CanibalesBoard.Mover1M1Cd)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1M1Cd();
					return newBoard;
				}
				
				if(CanibalesBoard.Mover1Ci.equals(a) && board.canMove(CanibalesBoard.Mover1Ci)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1Ci();
					return newBoard;
				}
				if(CanibalesBoard.Mover1Mi.equals(a) && board.canMove(CanibalesBoard.Mover1Mi)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1Mi();
					return newBoard;
				}
				
				if(CanibalesBoard.Mover2Ci.equals(a) && board.canMove(CanibalesBoard.Mover2Ci)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover2Ci();
					return newBoard;
				}
				if(CanibalesBoard.Mover2Mi.equals(a) && board.canMove(CanibalesBoard.Mover2Mi)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover2Mi();
					return newBoard;
				}
				if(CanibalesBoard.Mover1M1Ci.equals(a) && board.canMove(CanibalesBoard.Mover1M1Ci)) {
					CanibalesBoard newBoard = new CanibalesBoard(board);
					newBoard.mover1M1Ci();
					return newBoard;
				}

				// The Action is not understood or is a NoOp
				// the result will be the current state.
				return s;
			}
		}

}