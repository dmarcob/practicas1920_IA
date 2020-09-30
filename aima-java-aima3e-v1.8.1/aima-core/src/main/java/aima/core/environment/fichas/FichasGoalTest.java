package aima.core.environment.fichas;

import aima.core.search.framework.GoalTest;

public class FichasGoalTest implements GoalTest {
	FichasBoard goal = new FichasBoard(new char[] {'V','V','V',' ','B','B','B'});
	
	public boolean isGoalState(Object state) {
		FichasBoard board = (FichasBoard) state;
		return board.equals(goal);
	}
}
