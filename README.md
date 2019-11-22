simulation
====

This project implements a cellular automata simulator.

Names: Shreya Hurli, Sumer Vardhan, Ha Nguyen
sgh19, sv110, hn47

### Timeline

Start Date: 9/12/19

Finish Date: 9/24/19

Hours Spent: ~80-90 hours collectively

### Primary Roles
Ha Nguyen: Primarily implemented the Predator and Prey simulation, and pair-coded with Sumer Vardhan to implement the 
Game of Life Simulation. Configured and implemented the initial framework for the Game, Simulation, Visualization, 
and Main classes. Mainly implemented JavaFX scene transitions, timelines, etc. Implemented error handling and XML file 
validation; implemented some XML configurations in the XMLParser class and subclasses. 

Sumer Vardhan: Primarily implemented the Segregation Simulation, and pair-coded with Ha Nguyen to implemented the Game
of Life simulation. Configured and implemented the initial framework for Cell and Grid class. Implemented neighbors 
checking algorithm for square as well as triangular and hexagonal grid, and primarily worked on Visualization in terms
of displaying the grid. Also implemented the algorithm for many button functionality (e.g., Play, Stop, etc.) in the 
Game class. 

Shreya Hurli: Worked on coming up with design for triangle cell neighbors, 
wrote Percolation and Spreading of Fire Simulation as well as worked on the Simulation super class
and did some front-end work for additional feature implementation
as well as some work on xml parsing and input files. Did some refactoring by adding comments to public methods and 
replacing hardcoded text with properties keys to make the code readable, dynamic, and flexible


### Resources Used
* [Course webpage](https://www2.cs.duke.edu/courses/compsci308/current/)
* [Robert Duvall's Browswer example](https://coursework.cs.duke.edu/compsci308_2019fall/lab_browser)
* [Robert Duvall's Spike example](https://coursework.cs.duke.edu/compsci308_2019fall/spike_simulation)


### Running the Program

Main class: The main class is the Main class inside the game package. This is the class with the start 
method and the main method.

###Data files needed: 

GameProperties.properties -> Contains the text displayed on buttons, the text for selecting a simulation, shapes and 
colors of cells, and error-handling messages.

background.jpg -> From /Resources/images, provides the background for the splash screen
of the game, also is the background for the buttons

GameConfig.xml -> Defines what buttons exist in the CA

GameConfig.xsd -> Defines the tags used in GameConfig.xml, provides a framework for what elements can exist in
this xml file

GameOfLifeConfig.xsd -> Defines the schema for the Game of Life XML Configuration file, which is checked against to make
sure that all necessary elements needed to run a Game of Life Simulation is present.

PercolationConfig.xsd -> Defines the schema for the Percolation XML Configuration file, which is checked against to make
sure that all necessary elements needed to run a Game of Life Simulation is present.

PredatorPreyConfig.xsd -> Defines the schema for the Predator and Prey XML Configuration file, which is checked against to make
                          sure that all necessary elements needed to run a Game of Life Simulation is present.
                          
SegregationConfig.xsd -> Defines the schema for the Segregation XML Configuration file, which is checked against to make
                         sure that all necessary elements needed to run a Segregation Simulation is present.

SpreadingOfFireConfig -> Defines the schema for the Spreading of Fire XML Configuration file, which is checked against to make
                         sure that all necessary elements needed to run a Spreading of Fire Simulation is present.

###Interesting data files:
Most interesting simulation is PredatorPrey - the requirements of the simulation mean that unlike in GameOfLife, Percolation,
and SpreadingOfFire, the cell must not only keep track of its current and next states, but also how many state changes it 
has gone through, so the shark and fish know how many times they have moved.

####Features implemented:

* 2D grid represents simulation, with smaller neighborhoods for edge cells
than inner cells
* Each cell has state updates based on the states of its neighbors
* Implemented simulations for Game of Life, Segregation, Predator-Prey, Fire, and
Percolation
* Initial simulation settings come from XML configuration files 
* XML files contain simulation type, configuration parameters for grid and cells, 
and the initial grid
* XML files checked against XSD file to make sure that schema are valid
* 2D array of cells are animated, and as simulation continues and cells change states,
they change colors 
* User can play, pause, step through, speed up, slow down, load a new simulation,
go back to the splash screen
* Text displayed in the CA simulation is in the properties file of the project, within the src folder
* Hexagonal and triangle cells have been implemented
* Error handling for null, non-XML, and wrongly formatted XML files; plus error handling for invalid grids or 
inconsistent specifications within the configuration (e.g., specifying a certain number of rows and columns but providing
a grid with a different number of rows and columns)

###Assumptions or Simplifications:
The neighbor configuration in the xml file is the correct one for the current cell shape.


###Known Bugs:
* Set neighbors algorithm does not function properly for triangular neighborhoods.
* Save XML feature currently not finished; only allows a hard-coded file to be saved rather than an actual file of the
current state of the simulation. 

###Extra credit:

N/A

### Notes

Shreya: My IntelliJ interacted poorly with Git. I will be redownloading IntelliJ and potentially also redownloading JDK
to get everything together for the next project, so less time will be spent trying to get projects from Git to clone
properly, and more time will be spent actually getting to contribute to the project. 

### Impressions

Shreya: Very challenging project, required us to devise ways of interrelating classes we never thought of/have never 
really seen before. That said, I feel like I'm coming away understanding for the first time, what abstraction means, and
what interfaces are, as well as understanding data structures better (i.e. understanding the capabilities of a 2D array).

Ha: Working in a team on a project was definitely a new experience, and comes with both really great learning opportunities 
as well as challenges. For example, I think one thing we did very well on was brainstorming the design together during
the beginning stages and pair-coding the initial set-up of the project. I think that my teammates contributed a lot of
incredible ideas, and raised a lot of issues that I would have never thought of on my own. However, it was still challenging
to work on a project in a team, and it's an area that I'm (and I think all of us) are still trying to navigate. From things
like coding conventions to follow, to trying to make sure that my work is not invasive in that a change I make will
heavily impact my group mate's work, to deciding whether to all work on an issue that we're all struggling with or 
assigning one person to work on it while the rest moves on to another problem, are easy to determine in hindsight, but I
think that in the moment, it was hard to determine which was the right path. 

Sumer: I really enjoyed this project. It was cool to be able to implement so many different simulations with our code just
by providing different parameters and a couple of different methods. I also enjoyed working in a team and found it quite challenging.
It is a skill I am grateful to have a chance to be working on.
