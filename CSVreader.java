import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// imports necessary libraries
public class CSVreader {
    private List<String> from=new ArrayList<>();
    private List<String> to=new ArrayList<>();
    private List<String> line=new ArrayList<>();
    private List<Double> time=new ArrayList<>();
    // creates separate lists for each column
    public CSVreader(String file){
        int count =0;
        // used to keep track of which column the program is on
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // makes sure the program doesnt crash if there are errors
            String line;
            // stores one line of the csv file
            String delimiter = ",";
            // allows to split the objects on the line when there is a , since it is a csv file
            while((line = br.readLine()) != null) {
                // reads each line until there is no more to read
                String[] values = line.split(delimiter);
                // splits the string into an array with each index being the column
                for (String value : values) {
                    //loopps through the whole array 
                    if(count>3){
                        // allows to skip the initial title cells
                        if(count==4){
                            this.from.add(value);  
                        }
                        if(count==5){
                            this.to.add(value);    
                        }
                        if(count==6){
                            this.line.add(value);   
                        }
                        if(count==7){
                            this.time.add(Double.parseDouble(value));
                            count=3;
                            // this then adds the corresponding value to the right list depending on the column and then resets count back to three
                        }
                    }
                    count++;
                    // increments count by 1
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            // if there is an error this will print out what is wrong
        }
    }
    public List<String> get_to(){
        return this.to;
    }
    public List<String> get_from(){
        return this.from;
    }
    public List<String> get_line(){
        return this.line;
    }
    public List<Double> get_time(){
        return this.time;
    }
    // get methods to retrieve the values of each column
}
