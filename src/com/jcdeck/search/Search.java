package com.jcdeck.search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that contains static methods for searching through {@link State}s to
 * find the best path to an end state. There is a private constructor to prevent
 * other instances of the class being constructed.
 * 
 * @author James C Decker
 *
 */
public final class Search {
	
	/**
	 * Prevents other projects from constructing a {@code Search} object. All
	 * methods are static so there is no need to create an object.
	 */
	private Search(){
		
	}
	
	
	//Fields
	
	/**
	 * The fringe holds all the states that have not been expanded.
	 */
	private static ArrayList<State> fringe;
	
	/**
	 * This holds all the states that have already been expanded. It ensures
	 * that there are no duplicit states added to the fringe.
	 */
	private static ArrayList<State> visited;
	
	
	//Search Methods
	
	/**
	 * Performs a Depth-First-Search on the state passed to it. It stops
	 * search at the first state that is a goal state. It will return
	 * the actions that must be taken to reach that goal state in order.
	 * All steps between states are considered the same cost.
	 * 
	 * @param startState The initial state of the search
	 * @return An array of actions that lead to the goal state.
	 */
	public static Action[] dfsG(State startState){
		
		Search.reset(startState);
		
		//the number of nodes that have been expanded
		int count = 0;
		
		//repeat until a goal state is reached
		while(true){
			
			//debug
			printNewExpansion(count);
			count++;
			
			//expand the last element of the fringe
			//get all sub-nodes
			State[] nodes = fringe.get(fringe.size()-1).expand();

			//if nodes = null that the state expanded (index = fringe.size()-1) is a goal state
			if(nodes == null)
				return fringe.get(fringe.size()-1).getPath();
			
			//debug
			System.out.println("num of nodes: "+nodes.length);
			
			//add the state that was expanded to "visited" and remove it from the fringe
			visited.add(fringe.remove(fringe.size()-1));
			
			//debug
			printFringe();
			
			//for each new child node - add it to the fringe if is has not been visited before
			for(State n : nodes){
				if(!hasVisited(n))
					fringe.add(n);
			}
			
		}
		
		
	}

	/**
	 * Performs a Breadth-First-Search on the state passed to it. It stops
	 * search at the first state that is a goal state. It will return
	 * If all costs between states are the same, the path returned will be optimal.
	 * 
	 * @param startState The initial state to start the search.
	 * @return The path of actions in order to reach the goal state
	 */
	public static Action[] bfsG(State startState){
		
		Search.reset(startState);
		
		//the number of nodes that have been expanded
		int count = 0;
		
		//repeat until a goal state is reached
		while(true){
			
			//debug
			printNewExpansion(count);
			count++;
			
			//find state with shortest path
			int curMin = fringe.get(0).getPath().length;
			int minI = 0;
			for(int j = 0; j<fringe.size(); j++){
				if(fringe.get(j).getPath().length<curMin){
					curMin = fringe.get(j).getPath().length;
					minI = j;
				}
			}
			//System.out.println("minI: "+minI);
			State[] nodes = fringe.get(minI).expand();
			
			//if nodes = null that the state expanded (index = fringe.size()-1) is a goal state
			if(nodes == null)
				return fringe.get(minI).getPath();
			
			
			//add the state that was expanded to "visited" and remove it from the fringe
			visited.add(fringe.remove(minI));
			
			//for each new child node - add it to the fringe if is has not been visited before
			for(State n : nodes){
				if(!hasVisited(n))
					fringe.add(n);
			}
			
		}
		
	}
	
	/**
	 * Performs an A-Star search on the state passed. The path to a goal state
	 * will be optimal. It can find the optimal path with different costs between
	 * states. This search also uses a heuristic to find the goal state.
	 * 
	 * @param startState The initial state to start the search.
	 * @return The optimal path to the goal state.
	 */
	public static Action[] aStar(State startState){
		
		Search.reset(startState);
		
		//the number of nodes that have been expanded
		int count = 0;
		
		//repeat until a goal state is reached
		while(true){
			
			//debug
			printNewExpansion(count);
			count++;
			
			//debug
			System.out.println("Finding lowest cost/huristic:");
			
			
			//find state with shortest path:
			double curMin = fringe.get(0).getCost()+fringe.get(0).getHuristic();
			int minI = 0;//index of the minimum value
			for(int j = 0; j<fringe.size(); j++){
				
				State s = fringe.get(j);
				
				//System.out.println(s+" = "+s.getCost()+"/"+s.getHuristic());
				
				if(s.getCost()+s.getHuristic() < curMin){
					curMin = s.getCost() + s.getHuristic();
					minI = j;
				}
				
			}
			
			//debug
			System.out.println("Expanding State with Path: "+Arrays.toString(fringe.get(minI).getPath()));
			
			//expand the state at minI
			State[] nodes = fringe.get(minI).expand();
			
			//if null is returned then it is a goal state
			if(nodes == null)
				return fringe.get(minI).getPath();
			
			//debug
			System.out.println("num of child nodes: "+nodes.length);
			
			//add to visited
			visited.add(fringe.remove(minI));
			
			//for each new child node - add it to the fringe if is has not been visited before
			for(State n : nodes){
				if(!hasVisited(n))
					fringe.add(n);
			}
			
		}
		
	}
	
	
	
	
	//Private methods
	
	/**
	 * Uses the equals function of the states to check if 'checkState' occurs
	 * in 'this.visited'.
	 * 
	 * @param checkState The state to check
	 * @return true if 'checkState' occurs in 'this.visited'; false otherwise.
	 */
	private static boolean hasVisited(State checkState){
		
		for(State s : visited)
			if(s.equals(checkState))
				return true;
		
		return false;
		
	}
	
	/**
	 * clears the fringe and the lit of visited state. Adds {@code startState}
	 * to the fringe. Should be call at the beginning of all search methods.
	 * 
	 * @param startState the state given to the search function to start from
	 */
	private static void reset(State startState){
		
		//reset the fringe and the list of visited states
		fringe = new ArrayList<State>();
		visited = new ArrayList<State>();
		
		//add the start state to the fringe - it is the only known state and therefore
		//the only one that needs to be expanded
		fringe.add(startState);
		
	}
	
	
	
	
	//debugging
	
	/**
	 * Prints the number of nodes that have been expanded, {@code i}. Then calls
	 * {@link printFringe()}. Then prints the number of states have been visited.
	 * 
	 * @param i the number of nodes that were just expanded
	 */
	private static void printNewExpansion(int i){
		System.out.println();
		System.out.println("Nodes Expanded: "+i);
		printFringe();
		System.out.println("visited: "+visited.size());
	}
	
	/**
	 * Prints out the number of objects in the {@link fringe} and each object
	 * in the {@code fringe}. {@code toString()} is called for each item in
	 * the fringe.
	 */
	public static void printFringe(){
		
		System.out.print("fringe:  "+fringe.size()+": ");
		
		for(State s : fringe){
			System.out.print(s+", ");
		}
		
		System.out.println();
	
	}
	
	
	/**
	 * Returns the number of nodes that need to be expanded.
	 * 
	 * @return size of the fringe
	 */
	@Override
	public String toString(){
		return "fringe size: "+fringe.size();
	}
	
	
	
}
