# State_Search
Finds a path to a goal state

The class Search contains 3 differnet public search methods, each taking one State as an argument. There
should be one (or multiple) classes that implement State. Each of these represent a different state of a
problem. The methods that they must implement are called by the Search algorithm to find sub-states until
a goal state is reached. Each State object the user creates shuold store an array of actions that explain
how to reach that state from the start state. When a state is identified as a goal state, it will contain
all the actions required to reach that state from the start state: the path.

One example for using this state search is to find a path of a robot on a grid to a perticulare square of
the grid. The state class that implements this state class will contain the position of the robot. When
the state is expaned the sub-states show the robot in each of the adjacent squares. When the robot is in
the square it is trying to reach, an array of actions (either up, down, left, or right) will be returned.
