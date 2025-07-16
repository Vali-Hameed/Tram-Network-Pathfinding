public class edge {
    private Double time;
    private String destination;
    private String line;
    public edge(Double time, String destination, String line){
        this.time=time;
        this.destination=destination;
        this.line=line;
        // sets the values of the attributes 
    }
    public Double get_time(){
        return this.time;
    }
    public String get_line(){
        return this.line;
    }
    public String get_destination(){
        return this.destination;
    }
    // the get methods for each attribute
}
