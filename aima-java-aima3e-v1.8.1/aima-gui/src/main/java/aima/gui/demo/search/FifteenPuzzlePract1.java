package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleBoard;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleFunctionFactory;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class FifteenPuzzlePract1 {
	static FifteenPuzzleBoard board1 = new FifteenPuzzleBoard(
			new int[] {  1,5,2,3,4,9,6,7,8,0,10,11,12,13,14,15 }); //Solución en 3 estados


	public static void main(String[] args) {
		//***********************************
		//Búsqueda primero en anchura (BFS)
		//***********************************
		System.out.print(String.format("%8s","BFS-G-3")); //En grafo
		fifteenPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), board1);
		System.out.print(String.format("%8s","BFS-T-3")); //En árbol
		fifteenPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), board1);
		
		//**************************************
		//Búsqueda primero en profundidad (DFS)
		//**************************************
		System.out.print(String.format("%8s","DFS-G-3")); //En grafo
		excesivoCoste(1); //Coste excesivo en tiempo
		System.out.print(String.format("%8s","DFS-T-3")); //En árbol
		excesivoCoste(1); //Coste excesivo en tiempo
		
		//**************************************
		//Búsqueda en profundidad limitada (DLS)
		//**************************************
		System.out.print(String.format("%8s","DLS-9-3")); 
		fifteenPuzzleSearch(new DepthLimitedSearch(9), board1);
		
		//***************************************
		//Búsqueda en profundidad iterativa (IDS)
		//***************************************
		System.out.print(String.format("%8s","IDS-3")); 
		fifteenPuzzleSearch(new IterativeDeepeningSearch(), board1);

		//*************************************
		//Búsqueda con coste uniforme (UCS)
		//*************************************
		System.out.print(String.format("%8s","UCS-G-3"));
		fifteenPuzzleSearch(new UniformCostSearch(new GraphSearch()), board1);


	}
	
	//Salida por pantalla cuando coste excesivo temporal(tipoCoste = 1)
	// o espacial (tipoCoste = 2)
	public static void excesivoCoste(int tipoCoste) {
		for (int i = 0; i < 4; i++) {
			System.out.print("         ---|"); 
		}
		System.out.println("         (" + tipoCoste + ")");
	}

	

	
	private static void fifteenPuzzleSearch(Search search,FifteenPuzzleBoard initialState) {
		try {
		Problem problem = new Problem(initialState, FifteenPuzzleFunctionFactory
				.getActionsFunction(), FifteenPuzzleFunctionFactory
				.getResultFunction(), new FifteenPuzzleGoalTest());
		
				long t1 = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, search);
				long t2= System.currentTimeMillis();
				int depth, expandedNodes, queueSize, maxQueueSize;
				
				//Coste del camino
				String pathcostM = agent.getInstrumentation().getProperty("pathCost");
				if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
				else depth = 0;
				
				//Nodos expandidos
				if(agent.getInstrumentation().getProperty("nodesExpanded") == null) expandedNodes = 0;
				else expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
				
				//Tamaño frontera
				if(agent.getInstrumentation().getProperty("queueSize") == null) queueSize = 0;
				else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
				
				//Tamaño máximo frontera
				if(agent.getInstrumentation().getProperty("maxQueueSize") == null) maxQueueSize = 0;
				else maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
				
				
				//Imprimir métricas
				System.out.print(String.format("%12d"+ "|",depth));
				System.out.print(String.format("%12d"+ "|",expandedNodes));
				System.out.print(String.format("%12d"+ "|",queueSize));
				System.out.print(String.format("%12d"+ "|",maxQueueSize));
				System.out.println(String.format("%12d",t2 - t1));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
