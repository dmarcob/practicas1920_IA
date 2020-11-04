package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.math.Biseccion;

public class EightPuzzlePract2 {
	static int MAX_ROW = 23;
	static int MAX_COL = 4;
	static long [][] generatedN = new long[MAX_ROW][MAX_COL];
	static double [][] effectiveB = new double[MAX_ROW][MAX_COL];
	static int _generatedNodes = 0; 
	static Double _fr_efectivo = 0.0;
	

	
	public static void main (String[] args) {	
		int ini_depth = 2; //Profunfidad minima
		int end_depth = 24; //Profundidad_maxima
		int num_experimentos = 100;
		
		generarResultados(ini_depth, end_depth, num_experimentos);
		mostrarResultados(ini_depth, end_depth);
	}
	
	
	public static void generarResultados(int ini_depth, int end_depth, int num_experimentos) {
		try {
			int depth;
			for (depth = ini_depth; depth <= end_depth; depth++ ) {
				int experiment = 1;		
				do {
					//Genero estado inicial y estado final aleatorio
					EightPuzzleBoard init = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.randomIni());
					EightPuzzleBoard end = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.random(depth, init));
					//Compruebo que la solucion optima es de coste "depth"
					if (solucion_optima(depth,init,end)) {
						//caso no existen caminos a menos profundidad que "depth" para llegar de "init" a "end"
						//BFS
						experimento(init, end,  new BreadthFirstSearch(new GraphSearch()), depth);
						add_experimento(depth - 2, 0);
						
						if (depth <= 10) {
							//IDS
							experimento(init, end,  new IterativeDeepeningSearch(), depth);
							add_experimento(depth - 2, 1);
						}
						
						//A*h(1)
						experimento(init, end, new AStarSearch(new GraphSearch(),
								new MisplacedTilleHeuristicFunction2()), depth);
						add_experimento(depth - 2, 2);

						//A*h(2)
						experimento(init, end, new AStarSearch(new GraphSearch(),
								new ManhattanHeuristicFunction2()), depth);
						add_experimento(depth - 2, 3);
						
						experiment++;

					} 
				}while (experiment < num_experimentos);
				//Media de los "num_experimentos" experimentos a profundiad "depth"
				media_experimentos(depth - 2, 0, num_experimentos); //BFS
				if (depth <= 10) {
					media_experimentos(depth - 2, 1, num_experimentos); //IDS
				}
				media_experimentos(depth - 2, 2, num_experimentos); //A*h(1)
				media_experimentos(depth - 2, 3, num_experimentos); //A*h(2)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void mostrarResultados(int ini_depth, int end_depth) {
		//CABECERA
		for(int i = 1; i<91; i++) System.out.print("-"); System.out.println();
		System.out.format("||    || %20s%17s || %19s%19s||\n","Nodos Generados", " ","b*", " ");	
		for(int i = 1; i<91; i++) System.out.print("-"); System.out.println();
		System.out.format("|| %3s||","d");	
		System.out.format(" %6s  |", "BFS");
		System.out.format(" %6s  |", "IDS");
		System.out.format(" %6s  |", "A*h(1)");
		System.out.format(" %6s  ||", "A*h(2)");
		System.out.format(" %6s  |", "BFS");
		System.out.format(" %6s  |", "IDS");
		System.out.format(" %6s  |", "A*h(1)");
		System.out.format(" %6s  ||\n", "A*h(2)");
		for(int i = 1; i<91; i++) System.out.print("-"); System.out.println();
		for(int i = 1; i<91; i++) System.out.print("-"); System.out.println();
		//CUERPO
		for(int columna = 0; columna <= end_depth - ini_depth; columna++) {
			System.out.format("|| %3s||", ini_depth + columna);	
			System.out.format(" %6d  |", generatedN[columna][0]);
			if (generatedN[columna][1] == 0) {
				System.out.format(" %6s  |", "---");

			} else {
			System.out.format(" %6d  |", generatedN[columna][1]);
			}
			System.out.format(" %6d  |", generatedN[columna][2]);
			System.out.format(" %6d  ||", generatedN[columna][3]);
			
			System.out.format(" %6.2f  |", effectiveB[columna][0]);
			if (generatedN[columna][1] == 0) {
				System.out.format(" %6s  |", "---");
			}else {
			System.out.format(" %6.2f  |", effectiveB[columna][1]);
			}
			System.out.format(" %6.2f  |", effectiveB[columna][2]);
			System.out.format(" %6.2f  ||\n", effectiveB[columna][3]);
		}
	}
	
	/**
	 * Añade un experimento a las tablas generatedN y effectiveB
	 * @param columna
	 * @param fila
	 */
	public static void add_experimento(int columna, int fila) {
		generatedN[columna][fila] += _generatedNodes;
		effectiveB[columna][fila] += _fr_efectivo;	
	}
	
	/**
	 * Calcula la media del experimento correspondiente a la fila "fila" y a la columna "columna"
	 * en las tablas generatedN y effectiveB para "num_experimentos" experimentos
	 * @param columna
	 * @param fila
	 * @param num_experimentos
	 */
	public static void media_experimentos(int columna, int fila, int num_experimentos) {
		generatedN[columna][fila] = generatedN[columna][fila] / num_experimentos;
		effectiveB[columna][fila] = effectiveB[columna][fila] / num_experimentos;
	}
	
	/**
	 * Si la solucion optima es de coste "depth", devuelve true
	 * @param depth 
	 * @param init	
	 * @param end	
	 * @return
	 * @throws Exception 
	 */
	public static Boolean solucion_optima(int depth, EightPuzzleBoard init, EightPuzzleBoard end) throws Exception {
		int optim_depth; //Profundidad optima
		//Ejecuto una búsqueda A* que me garantiza encontrar el camino óptimo.
		Problem problem = new Problem(init, EightPuzzleFunctionFactory
				.getActionsFunction(),EightPuzzleFunctionFactory
				.getResultFunction(), new EightPuzzleGoalTest(end));
		Search search = new AStarSearch(new GraphSearch(),
				new ManhattanHeuristicFunction2());
		SearchAgent agent = new SearchAgent(problem, search);
		//Obtengo el coste del camino
		String pathcostM = agent.getInstrumentation().getProperty("pathCost");
		if (pathcostM!=null) optim_depth = (int)Float.parseFloat(pathcostM);
		else optim_depth = 0;	
			
		return depth == optim_depth;
	}
	
	/**
	 * Calcula los nodos generados y el factor de ramificación efectivo de una búsqueda "search"
	 * con solución a profundiad "depth", desde el estado inicial "init" hasta el estado final "end"
	 * @param init
	 * @param end
	 * @param search
	 * @param depth
	 * @throws Exception
	 */
	public static void experimento(EightPuzzleBoard init, EightPuzzleBoard end, Search search, int depth) throws Exception {
		_generatedNodes = 0;
		_fr_efectivo = 0.0;
		Problem problem = new Problem(init, EightPuzzleFunctionFactory
				.getActionsFunction(),EightPuzzleFunctionFactory
				.getResultFunction(), new EightPuzzleGoalTest(end));
		SearchAgent agent = new SearchAgent(problem, search);

		if(agent.getInstrumentation().getProperty("nodesGenerated") == null) _generatedNodes = 0; // NODOS GENERADOS
		else _generatedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
		
		Biseccion b = new Biseccion();
		b.setDepth(depth);
		b.setGeneratedNodes(_generatedNodes);
		_fr_efectivo =  b.metodoDeBiseccion(1.000000000001, 4.0, 1E-12);
	}
}
