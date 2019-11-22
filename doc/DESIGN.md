**What are the project's design goals, specifically what kinds of new features did you want to make easy to add?**

Our main goal was to organise the functionality of our project into the following individual roles: 

* Cells that are capable of having a state and a next state
* Grid structure that can hold cells and return any one cell on request
* Simulation class that applies the rules for updating cells to each cell based on its neighbors' states
* Visualization class to display the current state of the grid of cells

Our goal was to make it easy to add a new simulation. To accomplish this, we made it so that to create a new simulation, one can create a Simulation subclass that defines how to 'analyzeCells()'. This new simulation is immediately compatible with the exisitng code now and the only other adjustment to be made is to make a corresponding method in the visualisation class to supplent Visualisation.displayGrid() in displaying the cells based on their shape.

In addition, the front end and back end are clearly separated in terms of functionality, and information is passed back and forth via the Grid object. This makes it very easy to change most aspects of the front end without affecting any of the existing backend code


**Describe the high-level design of your project, focusing on the purpose and interaction of the core classes**

On a high level, our project contains four parts: configuration, elements, game, and simulation. 

**Configuration:** This package is responsible for handling all inputs and outputs related to the simulations, and specifically related to XML files. Examples include when the user chooses an XML to load, or when the user chooses to save an XML file. All configurations for the project is meant to be done through XML files. Even user-input specifications specified using the user interface (not yet implemented) will generate and XML file through which the rest of the classes can read information from by using classes in this package. The package provides the data for the rest of the classes; it mostly depends on files from the resources folder (mainly the XSD files that provide the schema for the game and the simulations), but does not depend on most other classes otherwise.

**Elements:** The Elements package component holds the Cell and the Grid, which are the two primary structures for the Simulation.
These elements are the foundation to which the Simulation class runs. The elements package depends on the Config package
mainly to set up and initialize the grid and cells in it; it provides the ground and initial state through which Simulations
can run on and update. 

**Game:** The game package manages elements that deal with JavaFX, and tie the back-end structure of the Elements, Config,
and Simulation together with each other as well as with the front-end and the JavaFX processes in Visualization. Therefore, the Game package depends on and brings together information from all the other packages to run the simulation. However, it does not have
access to all the internal workings and implementations of the different processes in these different packages.

**Simulation:** The simulation package manages the processes of analyzing and updating cells based on the rules of each of
the simulation. These processes are called by the Game class. The Simulation class, thus, depends on the Game class to
pass to it the Grid object of Cells in order to perform the analysis and updating process on. The Simulation subclasses
also depend on the configuration package and the XML file parsed by classes in that package for information about the rules,
such as the threshold for changing states depending on the cell's neighbors. 


**What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features?**

* A *simulation* consists of a **m x n grid of cells** that can be of any **one** shape. 

* All the cells in a given simulation  calculate their neighbors in an identical fashion*

##### *This means while you can specify a neighbor pattern in the config file for a simulation, each cell in that simulation will then use that arrangement. This caused a problem in our triangular cells simulation because upwards and downwards triangles have inverted neighbor patterns.




**Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline**

Rock, Paper, Scissors would be implemented
as a subclass of Simulation, with its rules being implemented in a modified analyzeCells
method. The only other thing that would change would be the number of cells in the XML simulation file would equal the product of the dimensions of the grid. 

A live graph
would be easy to add because of how methods are broken up. Because analyzeCells and
updateCells are two different methods, one called before the other, a state counter can be
utilized within the analyze method to get accurate counts of cell states before they change.
Because analyzeCells is called every frame, the counts will constantly update.
Methods can be added to the Visualization class to create a graph visualization for the user,
and these components can be added to the GUI in Game. Also it would be fairly easy
because code for this feature was already in the works. 

Finally, it would be really easy to add
the feature where the user could click on a cell to change its state - all that would have to change
would be that an eventHandler would have to be added for a click in the Game class, and the text for
an exception in the eventHandler would have to be added to the GameProperties to file.
There would also have to be a change in the updateCells method of the Simulation class
so that a cell's state could be changed upon a click. This flexibility
exists because the code in the Game class is well-encapsulated, where adding an additional
method to check for clicks on cells wouldn't have to affect other methods, and could simply
be called in the Visualization class where the color of a clicked cell could change
and because the code for updating cells is also well encapsulated in that the updating of the cells
can be changed just in the Simulation class updateCells method, which will be called in the Game class,
changing little else in the code, as other methods do not depend on how updateCells, updates cells,
but rather depend just on the fact that the cells are in some way updated so the
CA simulation can progress. However, the user could only click on a cell when the simulation was paused, otherwise they might change a cell in the analysis step (meaning the updates of its neighbors would be affected by its update, instead of by its state before it was changed).

Allowing users to specify the initial configurations for the simulation, such as the number of rows, columns, thresholds, the number of cells to have certain states, etc. will mostly leverage the XMLGenerator. Once the users put in their preferences, these input will be sent to the XMLGenerator, which will generate an XML document that is used to configure the simulation. Then, the procedure for initializing the Grid and the Simulations will be exactly the same as it would be had the user load in a an XML file. This way, rather than making if statements for every place that an input is used to handle one case where the input is an XML file and another case where the input is the user's choice, everything just assumes that the information comes from an XML file. The only change to make would be to set a file for the XMLParser when user inputs are given rather than a file is chosen. 


