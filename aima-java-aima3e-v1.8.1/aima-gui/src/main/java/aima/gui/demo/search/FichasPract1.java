package aima.gui.demo.search;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.fichas.FichasBoard;

public class FichasPract1 {

	static FichasBoard a = new FichasBoard(new char[] {'B','B','B',' ','V','V','V'});
	static FichasBoard b = new FichasBoard(new char[] {'C','B','B',' ','V','V','V'});

	public static void main(String[] args) {
		System.out.println(a.canMoveGap(new DynamicAction("GapRightJump0")));
	}
	
}
