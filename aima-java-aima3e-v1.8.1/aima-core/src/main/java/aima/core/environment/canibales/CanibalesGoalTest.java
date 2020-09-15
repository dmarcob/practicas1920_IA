package aima.core.environment.canibales;

import aima.core.search.framework.GoalTest;

/**
 * @author Diego Marco, 755232
 */
public class CanibalesGoalTest implements GoalTest {
	CanibalesBoard goal = new CanibalesBoard(new int[] { 0,0,0,3,3 });

	public boolean isGoalState(Object state) {
		CanibalesBoard board = (CanibalesBoard) state;
		return board.equals(goal);
	}
}