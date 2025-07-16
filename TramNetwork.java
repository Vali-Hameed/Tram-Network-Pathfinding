import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
public class TramNetwork {
    private Map<String, List<edge>> graph;
    private Map<String,Double> delays;
    private LinkedHashMap<String,String> nodes;
    public TramNetwork(Map<String,Double> delays){
        graph=new HashMap<>();
        // creates a hash map to store all of the sations as a graph for quick lookup
        this.delays=delays;
        // initialises the delays hashmap
    }
    public void add_edge(String from,String to, Double time,String line){
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());
        graph.get(from).add(new edge(time, to, line));
        graph.get(to).add(new edge(time, from,line));
        // adds all the stations as a bidirectional graph if they dont exist
    }
    public String dijkstras(String start, String end){
        this.nodes = new LinkedHashMap<>();
        Map<String, Double> distances = new HashMap<>();
        Map<String, List<String>> paths = new HashMap<>();
        // stores the path, distances to stations and nodes map used for generating graph
        Map<String, Map<String, Double>> bestDistanceByLine = new HashMap<>();
        // tracks best distances for each station-line combination 
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.get_time()));
        // Priority queue to store stations in order of increasing travel time
        for (String station : graph.keySet()) {
            distances.put(station, Double.MAX_VALUE);
            paths.put(station, new ArrayList<>());
        }
        // Initialize all stations with maximum distance and their own path
        distances.put(start, 0.0);
        paths.get(start).add("Start at " + start);
        pq.offer(new Node(start, null, 0.0, paths.get(start), 0));
        this.nodes.put(start, null);
         // Set up start station
        while (!pq.isEmpty()) {
            // while there are still nodes to be processed
            Node current = pq.poll();
            String currentStation = current.get_name();
            String currentLine = current.get_line();
            double currentTime = current.get_time();
            // gets the first node from pq and stores its attributes as variables
            if (currentStation.equals(end) && currentTime <= distances.get(end)) {
                return generatePath(current);
            }
            // if found destination returns path
            if (bestDistanceByLine.containsKey(currentStation) && bestDistanceByLine.get(currentStation).containsKey(currentLine) && bestDistanceByLine.get(currentStation).get(currentLine) < currentTime) {
                continue;
            }
            // Skip if we've already found a better path to this station via this line
            bestDistanceByLine.computeIfAbsent(currentStation, k -> new HashMap<>()).put(currentLine != null ? currentLine : "none", currentTime);
            // Update best distance for this station-line combination
            for (edge edge : graph.getOrDefault(currentStation, new ArrayList<>())) {
                // Process all adjacent stations
                String destination = edge.get_destination();
                String destinationLine = edge.get_line();
                // Calculate time for this segment
                double edgeTime = edge.get_time();
                double newDist = currentTime + edgeTime;
                // Check for line changes
                int newChanges = current.get_lineChanges();
                boolean lineChange = currentLine != null && !currentLine.equals(destinationLine);
                // Apply 2-minute penalty for changing lines
                if (lineChange) {
                    newDist += 2;
                    newChanges++;
                }
                // Apply any station delays
                if (delays != null && delays.containsKey(destination)) {
                    double delay = delays.get(destination);
                    newDist += delay;
                    edgeTime+=delay;
                }
                // Only update if a shorter path 
                if (newDist < distances.get(destination)) {
                    // Update distance
                    distances.put(destination, newDist);
                    // Create new path by copying current path
                    List<String> newPath = new ArrayList<>(current.get_path());
                    // Add line change information if applicable
                    if (lineChange) {
                        newPath.add("Change from line " + currentLine + " to " + destinationLine + " at " + currentStation + " (+2 mins)");
                    }
                    // Add this segment to the path
                    newPath.add("Take " + destinationLine + " line to " + destination + " (" + edgeTime + " mins)");
                    // Update path in map
                    paths.put(destination, newPath);
                    // Add to priority queue
                    pq.offer(new Node(destination, destinationLine, newDist, newPath, newChanges));
                } 
                else if (!bestDistanceByLine.containsKey(destination) || !bestDistanceByLine.get(destination).containsKey(destinationLine) || newDist < bestDistanceByLine.get(destination).get(destinationLine)) {
                    //  need to consider cases where a route via a different line might be better even if the total distance is slightly higher
                    List<String> newPath = new ArrayList<>(current.get_path());
                    // Create new path by copying current path
                    if (lineChange) {
                        newPath.add("Change from line " + currentLine + " to " + destinationLine + " at " + currentStation + " (+2 mins)");
                    }
                    // Add this segment to the path
                    newPath.add("Take " + destinationLine + " line to " + destination + " (" + edgeTime + " mins)");
                    // Add to priority queue with this alternative route
                    pq.offer(new Node(destination, destinationLine, newDist, newPath, newChanges));
                }
            }
        }
        return "No path found from " + start + " to " + end;
    }
    public String leastLineChanges(String start, String end) {
        this.nodes= new LinkedHashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.get_lineChanges()));
        // sets a priority queue to sort nodes by fewest lines changed
        Map<String, Integer> visited = new HashMap<>();
       // hashmap tracks the stations visited to each station
        Map<String,Double> distance= new HashMap<>(); 
        // hashmap to track the distance to each station
        pq.offer(new Node(start, null, 0.0, new ArrayList<>(List.of("Start at " + start)),0));
        distance.put(start, 0.0);
        // ads start node to pq
        this.nodes.put(start, null);
        // adds start node to nodes list
        for(String station : graph.keySet()){
            distance.put(station,Double.MAX_VALUE);
            // sets each station distance to max value
        }
        // sets start node distance to 0
        while (!pq.isEmpty()) {
            // while there are still stations
            Node current = pq.poll();
            // current is station with fewest lines chnages
            String key = current.get_name() + "|" + current.get_line();
            // stores a key to identify the station name and current line
            if (visited.containsKey(key) && visited.get(key) <= current.get_lineChanges()) continue;
            // If we've already visited this station with fewer or equal changes, skip it if not program crashes
            visited.put(key, current.get_lineChanges());
            // set the station and line combo as visted
            if (current.get_name().equals(end)) {
                return generatePath(current);  
                // returns the path as a string
            }
            for (edge edge : graph.getOrDefault(current.get_name(), new ArrayList<>())) {
                // checks all neighbours
                Double time=edge.get_time();
                ArrayList<String> newPath=new ArrayList<>(current.get_path());
                int newChanges=current.get_lineChanges();
                // initalises the new path and gets the distance and the amount of line changes
                Double newDist = current.get_time() + edge.get_time();
                if (delays!=null&&delays.containsKey(edge.get_destination())) {
                    time+=delays.get(edge.get_destination());
                    newDist+=delays.get(edge.get_destination());
                    // checks if the station has a delay and then updates the time with the delay
                }
                if (current.get_line() != null && !current.get_line().equals(edge.get_line())) {
                    // checks if changing lines
                    newChanges++;
                    // increments new chnanges
                    newDist+=2;
                    // adds 2 minute penalty for chnaging lines
                    newPath.add("*** Change from " + current.get_line() + " to " + edge.get_line() + " at " + current.get_name()+" ***");
                    // adds the chnaging lines message to the path  
                }
                newPath.add("Take " + edge.get_line() + " line to " + edge.get_destination() + " (" + time + " mins)");
                // adds a new step in the path
                distance.put(edge.get_destination(),newDist);
                //updates distance to destination
                pq.offer(new Node(edge.get_destination(), edge.get_line(),newDist, newPath,newChanges));
                // adds station to pq
            }
        }
        return "No path found from " + start + " to " + end;
        // if no path found returns no path found
    }
    public String generatePath(Node current){
        for (String step : current.get_path()) {
            if (step.startsWith("Take")) {
                // Extract line and destination
                String[] parts = step.split(" ");
                String line = parts[1];
                int startIndex = step.indexOf("to")+3;
                int endIndex = step.indexOf(" (");
                String destination = step.substring(startIndex, endIndex).trim();
                this.nodes.put(destination, line);
                // splits up each line of the route with a direction to store the station and line colour in a linked hashmap
                //helps to create a visual representation of the route using named circles and coloured lines
            }
        }
        StringBuilder route=new StringBuilder();
        // uses a string builder ass less memory required if large amount of concatinating
        route.append("Route:").append(System.lineSeparator());
        for (String step : current.get_path()) {
            route.append(step).append(System.lineSeparator());
            // adds all of the steps from the path list
        }
        route.append("Total line changes: ").append(current.get_lineChanges()).append(System.lineSeparator());
        route.append("Overall journey time: ").append(current.get_time()).append(" minutes").append(System.lineSeparator());
        // adds the journey time and line chnages
        return route.toString();
        // creates a string with the route you have to take and then returns it as a string
    }
    public LinkedHashMap<String,String> get_nodes(){
        return this.nodes;
    }
    // get method for nodes attribute
}
