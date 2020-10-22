package aima.gui.demo.search;

import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.fichas.FichasBoard;
import aima.core.environment.fichas.FichasFunctionFactory;
import aima.core.environment.fichas.FichasGoalTest;
import aima.core.environment.fichas.FichasStepCostFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;
/**
 * @author Diego Marco, 755232
 * 
 */
public class FichasPract1 {

	 static FichasBoard initial = new FichasBoard(new char[] {'B','B','B',' ','V','V','V'});

	public static void main(String[] args) {
		//***********************************
		//Búsqueda primero en anchura (BFS)
		//***********************************
		System.out.println("Fichas BFS_grafo-->"); //En grafo
		FichasSearch(new BreadthFirstSearch(new GraphSearch()), initial);
		System.out.println("\n\nFichas BFS_arbol-->"); //En árbol
		FichasSearch(new BreadthFirstSearch(new TreeSearch()), initial);
		
		//**************************************
		//Búsqueda primero en profundidad (DFS)
		//**************************************
		System.out.println("\n\nFichas DFS_grafo-->"); //En grafo
		FichasSearch(new DepthFirstSearch(new GraphSearch()), initial);
		System.out.println("\n\nFichas DFS_arbol-->"); //En árbol
		//No es capaz de encontrar la solución en un tiempo razonable
		System.out.println("........(1)");
		
		//**************************************
		//Búsqueda en profundidad limitada (DLS)
		//**************************************
		System.out.println("\n\nFichas DLS-->"); //Límite 11
		FichasSearch(new DepthLimitedSearch(8), initial);
		
		//***************************************
		//Búsqueda en profundidad iterativa (IDS)
		//***************************************
		System.out.println("\n\nFichas IDS-->"); 
		FichasSearch(new IterativeDeepeningSearch(), initial);
		
		//*************************************
		//Búsqueda con coste uniforme (UCS)
		//*************************************
		System.out.println("\n\nFichas UCS_grafo-->");
		FichasSearch(new UniformCostSearch(new GraphSearch()), initial);
		System.out.println("\n\nFichas UCS-arbol-->");
		//En árbol tiene un coste excesivo en memoria
		System.out.println("........(2)");
	}
	
	private static void FichasSearch(Search search, FichasBoard initialState) {
		try {	
			Problem problem = new Problem(initialState, FichasFunctionFactory
					.getActionsFunction(), FichasFunctionFactory
					.getResultFunction(), new FichasGoalTest(), new FichasStepCostFunction());
			
			long t1 = System.currentTimeMillis();
			SearchAgent agent = new SearchAgent(problem, search);
			long t2= System.currentTimeMillis();
			
			double depth;
			int expandedNodes, queueSize, maxQueueSize;
			
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
			System.out.println("pathCost : " + depth);
			System.out.println("nodesExpanded : " + expandedNodes);
			System.out.println("queueSize : " + queueSize);
			System.out.println("maxQueueSize: " + maxQueueSize);
			System.out.println(String.format("Tiempo : %dmls",t2 - t1));
			
			System.out.println("\nSOLUCION:");
			System.out.println("GOAL STATE");
			char [] objetivo = {'V','V','V',' ','B','B','B'};
			System.out.println(new FichasBoard(objetivo));
			System.out.println("CAMINO ENCONTRADO");
			
			//Imprimir pasos hasta solucion
			executeActions(agent.getActions(), problem);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void executeActions(List<Action> actions, Problem problem) {
        Object initialState = problem.getInitialState();
	    ResultFunction resultFunction = problem.getResultFunction();
	        
	    Object state = initialState;
	    System.out.print("         INITIAL STATE   ");
	    System.out.println(state);
	        
	    for(Action action : actions) {
	         System.out.print(action.toString() + "   ");
	         state = resultFunction.result(state, action);
	         System.out.println(state);
	        }
	    }

	
}
