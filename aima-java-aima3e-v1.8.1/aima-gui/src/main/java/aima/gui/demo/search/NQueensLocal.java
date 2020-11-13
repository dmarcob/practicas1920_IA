package aima.gui.demo.search;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.SearchAgent;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

/**
 * 
 * @author Diego Marco, 755232
 *
 */
public class NQueensLocal {
	private static final int _boardSize = 8;
	//Parámetros simulated annealing
	private static int _k = 11;
	private static Double _lam = 0.285;
	private static int _T = 1000;
	//Porcentaje éxito experimentos simmulated annealing
	private static Double _porcentaje_exito = 0.00;
	//Algoritmo genetico
	private static final int _poblacion_inicial = 100;
	private static final Double _prob_mutacion = 0.15;

	public static void main(String[] args) {
		int numExperiments = 1000;
		nQueensHillClimbingSearch_Statistics(numExperiments);
		System.out.println("\n\n");
		nQueensRandomRestartHillClimbing();
		System.out.println("\n\n");
		//setAnnealingParameters(); //Para experimentar cuales son los mejores parámetros
		nQueensSimulatedAnnealing_Statistics(numExperiments); //Tarda 20 segundos (debido a T=1000)
		nQueensSimulatedAnnealingRestart();
		nQueensGeneticAlgorithmSearch();
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
		if (num_fallos > 0) System.out.format("Coste medio fallos: %.2f\n", (pasos_acum_fallos /(double)num_fallos));
		else  System.out.println("Coste medio fallos: 0.00");
		//Porcentaje aciertos
		System.out.format("Exitos: %.2f%%\n", (num_exitos / (double)numExperiments) * 100);
		//Media de pasos al acertar
		if (num_exitos > 0) System.out.format("Coste medio exitos: %.2f\n", (pasos_acum_exitos /(double)num_exitos));
		else  System.out.println("Coste medio exitos: 0.00");


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
			System.out.println("Coste exito:" + agent.getActions().size());
			System.out.format("Coste medio exito: %.2f", ((double)agent.getActions().size()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void nQueensSimulatedAnnealing_Statistics (int numExperiments) {
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
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),new Scheduler(_k, _lam, _T));
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
		System.out.println("Parámetros Scheduler: Scheduler (" + _k + "," + _lam + "," + _T + ")");
		//Porcentaje fallos
		System.out.format("Fallos: %.2f%%\n", (num_fallos / (double)numExperiments) * 100);
		//Media de pasos al fallar
		if (num_fallos > 0) System.out.format("Coste medio fallos: %.2f\n", (pasos_acum_fallos /(double)num_fallos));
		else  System.out.println("Coste medio fallos: 0.00");
		//Porcentaje aciertos
		_porcentaje_exito = (num_exitos / (double)numExperiments) * 100;
		System.out.format("Exitos: %.2f%%\n", _porcentaje_exito);
		//Media de pasos al acertar
		if (num_exitos > 0) System.out.format("Coste medio exitos: %.2f\n", (pasos_acum_exitos /(double)num_exitos));
		else  System.out.println("Coste medio exitos: 0.00");
	}
	
	
	public static void nQueensSimulatedAnnealingRestart() {
		String resultado = "FAILURE"; //FAILURE o SOLUTION_FOUND
		int intentos = 0;
		int pasos_acum_fallos = 0;
		SimulatedAnnealingSearch search = null;
		SearchAgent agent = null;
		//do {
		try {
			while (resultado.contentEquals("FAILURE")) {
				Problem problem = new Problem(generateRandomNqueensBoard(8),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),new Scheduler(_k, _lam, _T));
				agent = new SearchAgent(problem, search);

				intentos++;
				pasos_acum_fallos += agent.getActions().size();

				resultado = search.getOutcome().toString();
			}
			
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			System.out.println("Numero de intentos:" + intentos);
			System.out.println("Fallos:" + (intentos - 1));
			System.out.println("Coste exito:" + agent.getActions().size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nGeneticAlgorithm");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < _poblacion_inicial; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					_prob_mutacion);

			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);
			System.out.println("Parámetros iniciales:		Poblacion: " + _poblacion_inicial +	
					", Probabilidad mutación: " + _prob_mutacion);
			System.out.println("Mejor individuo=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Tamaño tablero  	= " + _boardSize);
			System.out.println("Fitness         	= "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Es objetivo     	= "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Tamaño de población	= " + ga.getPopulationSize());
			System.out.println("Iteraciones       	= " + ga.getIterations());
			System.out.println("Tiempo            	= "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Esta función se ha utilizado para observar cuales son los mejores parámetros
	//para la búsqueda de enfriamiento simulado
	//CONCLUSIÓN: Contra más grande sea T mejores resultados.
	public static void setAnnealingParameters() {
		_porcentaje_exito = 0.00;
		_T = 2500;
		_lam = 0.285;
		int best_k = -1;
		Double best_porcentaje_exito = _porcentaje_exito;
		for (_k = 10; _k < 20; _k++) {
			nQueensSimulatedAnnealing_Statistics(50);
			if (_porcentaje_exito > best_porcentaje_exito) {
				best_porcentaje_exito = _porcentaje_exito;
				best_k = _k;
			}
		}
		System.out.println("\n--------------");
		System.out.println("MEJOR PORCENTAJE EXITO: " +best_porcentaje_exito);
		System.out.println("MEJOR k: " + best_k);

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
