package aima.core.environment.fichas;

import aima.core.agent.Action;
import aima.core.environment.map.Map;
import aima.core.search.framework.StepCostFunction;

public class FichasStepCostFunction implements StepCostFunction {
	private Map map = null;

	//
	// Used by Uniform-cost search to ensure every step is greater than or equal
	// to some small positive constant
	private static double constantCost = 1.0;

	//
	// START-StepCostFunction
	public double c(Object fromCurrentState, Action action, Object toNextState) {
		Double cost;
		if (FichasBoard.GapRightJump1.equals(action) || FichasBoard.GapLeftJump1.equals(action)) {
			cost = 2.0; //Ficha se mueve a hueco y salta una ficha
		} else if (FichasBoard.GapRightJump2.equals(action) || FichasBoard.GapLeftJump2.equals(action)) {
			cost = 3.0; //Ficha se mueve a hueco y salta dos fichas
		} else { 
			cost = 1.0; //Ficha se mueve a hueco y no salta
		}

		return new Double(cost);
	}

}
