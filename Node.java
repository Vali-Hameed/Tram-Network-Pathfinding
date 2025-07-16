import java.util.ArrayList;
import java.util.List;
public class Node {
    private String name;
    private String line;
    private List<String> path;
    private Double time;
    private int lineChanges;
    public Node(String name, String line,Double time,List<String> path,int lineChanges){
        this.name=name;
        this.line=line;
        this.time=time;
        this.path=new ArrayList<>(path);
        this.lineChanges=lineChanges;
        //  sets the values of the attributes
    }
    public Double get_time(){
        return this.time;
    }
    public String get_name(){
        return this.name;
    }
    public List<String> get_path(){
        return this.path;
    }
    public String get_line(){
        return this.line;
    }
    public int get_lineChanges(){
        return this.lineChanges;
    }
    // the get methods for each attribute
}