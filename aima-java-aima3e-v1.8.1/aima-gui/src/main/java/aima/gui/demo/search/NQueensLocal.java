package aima.gui.demo.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.SearchAgent;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

public class NQueensLocal {
	private static final int _boardSize = 8;

	
	public static void main(String[] args) {
		int numExperiments = 1000;
		
		
		nQueensHillClimbingSearch_Statistics(numExperiments);
		System.out.println("\n\n");
		nQueensRandomRestartHillClimbing();
		System.out.println("\n\n");
		nQueensSimulatedAnnealing_Statistics(numExperiments);
		
	}
	
	public static void nQueensHillClimbingSearch_Statistics(int numExperiments) {
		int num_fallos = 0;
		int num_exitos = 0;
		int pasos_acum_fallos = 0;
		int pasos_acum_exitos = 0;
		
		Iterator<NQueensBoard> randomBoard = generateSetNqueensBoard(8, numExperiments).iterator();
		for (int i = 0; i < numExperiments; i++) {
			try {
				Problem problem = new Problem(randomBoard.next(),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);

				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					num_exitos++;
					pasos_acum_exitos += agent.getActions().size();
				} else {
					//solución no encontrada
					num_fallos++;
					pasos_acum_fallos += agent.getActions().size();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		System.out.println("\nNQueensDemo HillClimbing con " + numExperiments + 
				" estados iniciales diferentes-->");
		//Porcentaje fallos
		System.out.format("Fallos: %.2f%%\n", (num_fallos / (double)numExperiments) * 100);
		//Media de pasos al fallar
		System.out.format("Coste medio fallos: %.2f\n", (pasos_acum_fallos /(double)num_fallos));
		//Porcentaje aciertos
		System.out.format("Exitos: %.2f%%\n", (num_exitos / (double)numExperiments) * 100);
		//Media de pasos al acertar
		System.out.format("Coste medio exitos: %.2f\n", (pasos_acum_exitos /(double)num_exitos));

	}
	
	public static void nQueensRandomRestartHillClimbing() {
		String resultado = "FAILURE"; //FAILURE o SOLUTION_FOUND
		int intentos = 0;
		int pasos_acum_fallos = 0;
		HillClimbingSearch search = null;
		SearchAgent agent = null;
		//do {
		try {
			while (resultado.contentEquals("FAILURE")) {
				Problem problem = new Problem(generateRandomNqueensBoard(8),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				agent = new SearchAgent(problem, search);
				intentos++;
				pasos_acum_fallos += agent.getActions().size();

				resultado = search.getOutcome().toString();
			}
			
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			System.out.println("Numero de intentos:" + intentos);
			System.out.println("Fallos:" + (intentos - 1));
			if (intentos > 1) {
				System.out.format("Coste medio fallos: %.2f\n", (pasos_acum_fallos /(double)(intentos - 1)));
			} else {
				//No hay fallos
				System.out.println("Coste medio fallos: 0.00");
			}
			System.out.println("Coste exitos:" + agent.getActions().size());
			System.out.format("Coste medio exitos: %.2f", ((double)agent.getActions().size()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void nQueensSimulatedAnnealing_Statistics (int numExperiments) {
		int num_fallos = 0;
		int num_exitos = 0;
		int pasos_acum_fallos = 0;
		int pasos_acum_exitos = 0;
		//Parámetros del enfriamiento simulado
		int k = 15;
		double lam = 0.003;
		int T = 1000;
		
		Iterator<NQueensBoard> randomBoard = generateSetNqueensBoard(8, numExperiments).iterator();
		for (int i = 0; i < numExperiments; i++) {
			try {
				Problem problem = new Problem(randomBoard.next(),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);

				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					num_exitos++;
					pasos_acum_exitos += agent.getActions().size();
				} else {
					//solución no encontrada
					num_fallos++;
					pasos_acum_fallos += agent.getActions().size();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		System.out.println("NQueensDemo Simulated Annealing con " + numExperiments + " estados iniciales diferentes --->");
		System.out.println("Parámetros Scheduler: Scheduler (" + k + "," + lam + "," + T + ")");
		//Porcentaje fallos
		System.out.format("Fallos: %.2f%%\n", (num_fallos / (double)numExperiments) * 100);
		//Media de pasos al fallar
		System.out.format("Coste medio fallos: %.2f\n", (pasos_acum_fallos /(double)num_fallos));
		//Porcentaje aciertos
		System.out.format("Exitos: %.2f%%\n", (num_exitos / (double)numExperiments) * 100);
		//Media de pasos al acertar
		System.out.format("Coste medio exitos: %.2f\n", (pasos_acum_exitos /(double)num_exitos));

	}
	
	public static Set<NQueensBoard> generateSetNqueensBoard(int boardSize, int populationSize){
		Set<NQueensBoard> setGeneratedNQueens = new HashSet<NQueensBoard>();

		while(setGeneratedNQueens.size()< populationSize){
			setGeneratedNQueens.add(generateRandomNqueensBoard(_boardSize)); 
		}
		
		return setGeneratedNQueens;
	}
	
	public static NQueensBoard generateRandomNqueensBoard(int boardSize) {
		NQueensBoard board = new NQueensBoard(boardSize);
		for (int i = 0; i < boardSize; i++) {
			board.addQueenAt(new XYLocation(i, new Random().nextInt(boardSize)));
		}
		return board;
	}

	
	
	
	
	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}
	
	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}
