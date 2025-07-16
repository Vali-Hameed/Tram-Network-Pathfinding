# Tram-Network-Pathfinding
**Tram-Network-Pathfinding** is a Java-based application that finds the optimal route between two tram stops on a network. It provides a graphical user interface (GUI) for users to input their start and end destinations and choose between finding the shortest route or the route with the fewest line changes. The application also allows for the simulation of delays on the network.

## Features

* **Shortest Route Calculation**: Utilizes Dijkstra's algorithm to find the route with the minimum travel time between two stations. A 2-minute penalty is added for each line change to provide a more realistic journey time.
* **Fewest Line Changes**: Implements a custom algorithm to find the path with the minimum number of line changes, which is ideal for passengers who prefer a simpler journey.
* **Delay Simulation**: Users can add custom delays to specific stations to see how they impact journey times and routes.
* **Graphical User Interface**: The program features a user-friendly GUI built with Java Swing. It includes windows for inputting journey details, viewing results, and adding delays.
* **Visual Route Representation**: Displays a visual representation of the calculated route, showing the stations and the lines connecting them.

## How It Works

The application's core is a graph representation of the tram network. Each station is a node, and the connections between stations are edges with associated travel times and line colors.

1.  **Data Loading**: The application reads the tram network data from a CSV file named `Metrolink_times_linecolour.csv`. This file contains the connections between stations, the tram line for each connection, and the travel time in minutes.
2.  **Graph Creation**: An adjacency list, implemented using a `HashMap`, stores the tram network. The keys are the station names, and the values are lists of `edge` objects, each representing a connection to another station.
3.  **Pathfinding Algorithms**:
    * **Dijkstra's Algorithm**: For the "Shortest Route" feature, the application uses a priority queue to implement Dijkstra's algorithm, always exploring the path with the current shortest travel time.
    * **Least Line Changes Algorithm**: A variation of a shortest path algorithm that prioritizes the number of line changes over the travel time. A priority queue is used to explore paths with fewer line changes first.

## File Overview

* **`Driver.java`**: The main entry point of the application. It creates the main application window (`JFrame`) and initializes the `MainWindow`.
* **`MainWindow.java`**: Manages the primary GUI, handling user inputs for start and end destinations and initiating the route calculation based on the user's choice.
* **`CSVreader.java`**: A utility class dedicated to reading and parsing the `Metrolink_times_linecolour.csv` file to load the tram network data.
* **`TramNetwork.java`**: The core class representing the tram network as a graph. It contains the implementation of the pathfinding algorithms (Dijkstra's and least line changes).
* **`addEdges.java`**: A helper class that populates the `TramNetwork` graph with the data parsed by the `CSVreader`.
* **`Node.java`**: A data class used by the pathfinding algorithms to keep track of a position in a potential path, storing information like the station name, current line, time, and path history.
* **`edge.java`**: A data class that represents a connection between two stations, holding the destination, travel time, and line color.
* **`delaysWindow.java`**: The GUI window that allows users to input and manage simulated delays at specific stations.
* **`resultWindow.java`**: The GUI window that displays the calculated route details to the user.
* **`graphWindow.java`**: The GUI window that acts as a container for the visual representation of the route.
* **`createGraph.java`**: A `JPanel` component responsible for drawing the visual graph of the calculated route.
* **`Metrolink_times_linecolour.csv`**: The data file containing all the tram station connections, lines, and travel times.

## Data Source

The tram network data is stored in `Metrolink_times_linecolour.csv`. The file has four columns:

* **From**: The starting station of a segment.
* **To**: The destination station of a segment.
* **Line**: The color of the tram line for that segment.
* **Time (mins)**: The travel time for that segment in minutes.

The application can work with any CSV file that follows this format, allowing for different network names and distances to be used.

## Graphical User Interface

The GUI consists of several windows:

* **Main Window**: The initial window where users can enter their start and end stations and select the desired pathfinding method ("Shortest Route" or "Least Line Changes"). There is also an option to navigate to the delays window.
* **Result Window**: Displays the calculated route as a text-based list of directions, including the total journey time and the number of line changes. From here, users can view a graphical representation of the route or return to the main window.
* **Delays Window**: Allows users to input a station name and a delay time in minutes. This delay is then factored into future route calculations.
* **Graph Window**: Presents a visual graph of the calculated route, with stations represented as nodes and connections as colored lines corresponding to the tram lines.

## How to Run the Program

1.  **Prerequisites**:
    * Java Development Kit (JDK) installed.
2.  **Compilation and Execution**:
    * Ensure all the `.java` files and the `Metrolink_times_linecolour.csv` file are in the same directory.
    * Compile the Java source files using a Java compiler. For example, in the terminal:
        ```bash
        javac *.java
        ```
    * Run the main driver class:
        ```bash
        java Driver
        ```
    * The main application window will appear, allowing you to start finding tram routes.
