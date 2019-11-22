####Team: 
Sumer Vardhan (sv110), Ha Nguyen (hn47), Shreya Hurli (sgh19)

####High Level design for RPS:

At the high level, we believe the best way to keep the program modular and easily expandable (in terms of adding
more weapons) is to separate the code into three classes:

1. Game Class 
2. Weapon Class 
3. Player Class 

The config file will contain information about each weapon in the following format:

Name_of_Weapon1 {<list of weapons I can beat>}

Name_of_weapon2 {<list of weapons I can beat>}
etc ...

This way, the game class can read the config file to determine what weapons players have access to. It
coordinates the passes of the game - it creates the players, allows them to choose weapons, creates those
weapons from the Weapon class and then runs a comparison contained in the weapon class to decide who wins.
It then updates scores of the player objects through a method contained in the Player class.

#####Inheritance

We discussed how the concept of inheritance could be applied to the game of RPS and came to the conclusion that it was not
the best approach in this case.

####Cell Society High Level Design

We are thinking of breaking our code into the following classes:

1. simulation
2. Cell
3. Visualization

####How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?

Each Cell object contains a collection of its neighbours (who is defined as a neighbour is determined by the config file)
The simulation class executes two passes for each round of cell updates. The first pass allows each cell
to analyse the state of its neighbours. The second pass applies the rules (gleaned from the config file) to the cells
and updates them. At this point, the visualization class is called from the simlation class to output the updated
grid of cells.

####What relationship exists between a Cell and a simulation's rules?

Each Cell must initialise itself with the simulations rules. It gets these from the config file.

####What is the grid? Does it have any behaviors? Who needs to know about it?

The grid is a 2D array of Cell objects. It exists within the simulation Class. The visualization class needs it
to display the current version of the grid.

####What information about a simulation needs to be the configuration file?

The config file should contain the number of rows and columns of cells, the initial states of the cells and the rules
that determine whether a cell is updated (these rules include who is considered a neighobur and what states of the neighbours prompt
an update in the calls)

How is the graphical view of the simulation updated after all the cells have been updated?
The simulation class passes the 2D array of Cells to the Visualization class which then outputs the updated 
configuration of the cells.

