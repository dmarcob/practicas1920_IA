package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

/**
 * @author Diego Marco, 755232
 * 
 */
public class EightPuzzlePract1 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		//Cabecera de las métricas
		System.out.println("Problema|Profundidad|      Expand|      Q.Size|       MaxQS|      tiempo");
		//Algoritmos de búsqueda no informada con estado inicial: boardWithThreeMoveSolution
		busquedaTableroInicial(boardWithThreeMoveSolution, 3); 
		//Algoritmos de búsqueda no informada con estado inicial: random1
		busquedaTableroInicial(random1, 9);
		//Algoritmos de búsqueda no informada con estado inicial: extreme
		busquedaTableroInicial(extreme, 30);
		//(3,9,30 es la distancia a la que está el resultado según el estado inicial)
	}
	
	public static void busquedaTableroInicial(EightPuzzleBoard initialState, int pasos) {
		//***********************************
		//Búsqueda primero en anchura (BFS)
		//***********************************
		System.out.print(String.format("%8s","BFS-G-" + pasos)); //En grafo
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), initialState);
		System.out.print(String.format("%8s","BFS-T-" + pasos)); //En árbol
		if (pasos == 3 || pasos == 9) {
			//Para los estados iniciales boardWithThreeMoveSolution y random1
			//es capaz de llegar a un objetivo
			eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), initialState);
		}
		else {
			//Para el estado inicial extreme, no tiene suficiente memoria para llegar al objetivo
			for (int i = 0; i < 4; i++) {
				System.out.print("         ---|"); 
			}
				System.out.println("         (2)"); 
		}
		
		//**************************************
		//Búsqueda primero en profundidad (DFS)
		//**************************************
		System.out.print(String.format("%8s","DFS-G-" + pasos)); //En grafo
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), initialState);
		System.out.print(String.format("%8s","DFS-T-" + pasos)); //En árbol
		//eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), initialState);
		//No es capaz de resolver en tiempo razonable con ninguno de los tres estados iniciales		
		for (int i = 0; i < 4; i++) {
			System.out.print("         ---|"); 
		}
		System.out.println("         (1)");
		
		
		//**************************************
		//Búsqueda en profundidad limitada (DLS)
		//**************************************
		System.out.print(String.format("%8s","DLS-9-" + pasos)); //Límite 9
		eightPuzzleSearch(new DepthLimitedSearch(9), initialState);
		System.out.print(String.format("%8s","DLS-3-" + pasos)); //Límite 3
		eightPuzzleSearch(new DepthLimitedSearch(3), initialState);
		
		
		//***************************************
		//Búsqueda en profundidad iterativa (IDS)
		//***************************************
		System.out.print(String.format("%8s","IDS-" + pasos)); 
		if(pasos == 3 || pasos == 9) {
			//Para los estados iniciales boardWithThreeMoveSolution y random1
			//es capaz de llegar a un objetivo
			eightPuzzleSearch(new IterativeDeepeningSearch(), initialState);
		}
		else {
			//Para el estado inicial extreme no es capaz de llegar al objetivo en un tiempo razonable
			for (int i = 0; i < 4; i++) {
				System.out.print("         ---|"); 
			}
				System.out.println("         (1)"); 
			
		}
		
		
		//*************************************
		//Búsqueda con coste uniforme (UCS)
		//*************************************
		System.out.print(String.format("%8s","UCS-G-" + pasos));
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), initialState);
		System.out.print(String.format("%8s","UCS-T-" + pasos));
		if(pasos == 3 || pasos == 9) {
			//Para los estados iniciales boardWithThreeMoveSolution y random1
			//es capaz de llegar a un objetivo
			eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), initialState);
		}
		else {
			//Para el estado inicial extreme no es capaz de llegar al objetivo en un tiempo razonable
			for (int i = 0; i < 4; i++) {
				System.out.print("         ---|"); 
			}
				System.out.println("         (1)"); 
			
		}
		
		//***************************************
		//Búsqueda A*
		//***************************************
		//System.out.print(String.format("%8s","A*-G-" + pasos));
		//eightPuzzleSearch(new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction()), initialState);
		
		//***************************************
		//Búsqueda voraz
		//***************************************
		//System.out.print(String.format("%8s","GS-G-" + pasos));
		//eightPuzzleSearch(new GreedyBestFirstSearch(new GraphSearch(),new MisplacedTilleHeuristicFunction()), initialState);
		

		
	}
	
	private static void eightPuzzleSearch(Search search,EightPuzzleBoard initialState) {
		try {
		Problem problem = new Problem(initialState, EightPuzzleFunctionFactory
				.getActionsFunction(), EightPuzzleFunctionFactory
				.getResultFunction(), new EightPuzzleGoalTest());
		
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
	
	
	
	
	//No se usa, la comento
	/*	
	public static void executeActions(List<Action> actions, Problem problem) {
        Object initialState = problem.getInitialState();
	    ResultFunction resultFunction = problem.getResultFunction();
	        
	    Object state = initialState;
	    System.out.println("INITIAL STATE");
	    System.out.println("state");
	        
	    for(Action action : actions) {
	         System.out.println(action.toString());
	         state = resultFunction.result(state, action);
	         System.out.println(state);
	         System.out.println("- - -");
	        }
	    }


		
	}*/
