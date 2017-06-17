package com.jcdeck.search;

/**
 * 
 * Represents a possible state of the world. The current state is expanded
 * to all possible sub-states, then those are expanded and the process
 * continues until a state is a goal state is reached. Once a goal state
 * is reached, the program will know the exact {@link Actions} taken to
 * get to that state. 
 * 
 * @author James C Decker
 *
 */
public interface State {
	
	/**
	 * Creates all new state objects that are possible to get
	 * to from this state. The path in these new states should reflect
	 * how to get to them from the start state. If this is a goal state
	 * the this should return null. If there are no children states and
	 * it is not a goal state an empty array should be returned.
	 * 
	 * <p>
	 * 
	 * This should also make sure each child state has recorded the actions
	 * taken to reach it so that {@link getPath} can return an accurate path.
	 * 
	 * @return all possible child states reachable from this state.
	 */
	public State[] expand();
	
	/**
	 * Predicts the cost to get from this state to the goal state.
	 * This will help the search algorithm choose which state to expand.
	 * This must be less than or equal to the actual cost of getting to
	 * the goal state to make it admissible.
	 * 
	 * @return the predicted cost to the goal state
	 */
	public double getHuristic();
	
	/**
	 * This will return a list of objects that have implemented Action.
	 * These objects represent the path to get to this state form the start state.
	 * 
	 * @return The Path to get to this state.
	 */
	public Action[] getPath();
	
	/**
	 * This calculates the cost to get to this state
	 * The search algorithm find the path with the smallest cost
	 * 
	 * @return The cost of getting to this state
	 */
	public double getCost();
	
	
	/**
	 * This should have the same effect as the equals(Object other) methods
	 * in java.lang.object. It insures that the search algorithm will not
	 * go in circles.
	 * 
	 * @param other another state to test equivalence
	 * @return true if this state is the same as {@code other}
	 */
	public boolean equalsState(State other);
	
}