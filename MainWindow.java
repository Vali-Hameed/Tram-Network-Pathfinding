import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
//imports needed libraries 
public class MainWindow implements ActionListener {
    private JLabel start;
    private JLabel end;
    private JTextField startText;
    private JTextField endText;
    private JFrame frame;
    private JButton leastLineChanges;
    private JButton shortestRoute;
    private JButton delaysButton;
    private Map<String,Double> delays;
    // defines variables needed in more than one function
    public MainWindow(JFrame frame,Map<String,Double> delays){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame=frame;
        this.delays=delays;
        start=new JLabel("Start");
        // creates a label that says start showing the user where to input their starting destination
        start.setBounds((int)(screenSize.getWidth()/2)-25, 50, 50, 50);
        // sets the coordinates and the size of the start label
        end=new JLabel("End");
        // creates a label to show the user where to input the end destination
        end.setBounds((int)(screenSize.getWidth()/2)-25, 250, 50, 50);
        // sets the coordinates and size of the end label
        startText=new JTextField(15);
        // creates a new textfield for the user to input their starting destination
        startText.setBounds((int)(screenSize.getWidth()/2)-100, 100, 200, 50);
        // sets the coordintes and the size of the textfield
        endText=new JTextField(15);
        // creates a new textfield for the user to input the end destination
        endText.setBounds((int)(screenSize.getWidth()/2)-100, 300, 200, 50);
        // sets the coordinates and the size of the textfield
        leastLineChanges=new JButton("Least Line Changes");
        // creates a button that allows the user to calcuate the route of their journey
        leastLineChanges.setBounds((int)(screenSize.getWidth()/2)+50, 380, 200, 50);
        // sets the coordinates and the size of the button
        leastLineChanges.addActionListener(this);
        // adds the action to the button when clicked from the action performed button
        delaysButton=new JButton("Add Delays");
        delaysButton.setBounds((int)(screenSize.getWidth()/2)-75,470,150,50);
        delaysButton.addActionListener(this);
        // creates delays button
        shortestRoute=new JButton("Shortest Route");
        shortestRoute.setBounds((int)(screenSize.getWidth()/2)-225, 380, 200, 50);
        shortestRoute.addActionListener(this);
        // creates shortest route button
        this.frame.add(start);
        this.frame.add(end);
        this.frame.add(leastLineChanges);
        this.frame.add(startText);
        this.frame.add(endText);
        this.frame.add(shortestRoute);
        this.frame.add(delaysButton);
        this.frame.revalidate();
        this.frame.repaint();
        this.frame.setVisible(true);
        // adds all of the necessary elements to the main jframe
        // and then validates and paints them
    }
    @Override
    // adds my own action to the button
    public void actionPerformed(ActionEvent e){
        int FromCorrect=0;
        int ToCorrect=0;
        // to store whether the from and to values are correct
        TramNetwork TramNetwork;
        String startStop=startText.getText();
        String endStop=endText.getText();
        // stores the values in the textfields as string variable
        CSVreader MetroLinkTimes = new CSVreader("Metrolink_times_linecolour.csv");
        // creates an instance of the csv reader with the metrolink times
        if(MetroLinkTimes.get_from().contains(startStop)){
            FromCorrect=1;
            // checks if the start destination is correct and then updates the fromcorrect variable accordingly
        }
        if(MetroLinkTimes.get_to().contains(endStop)){
            ToCorrect=1;
            // same as above if statement but with the end destination
        }
        if (e.getActionCommand().equals("Add Delays")) {
            this.frame.getContentPane().removeAll();
            new delaysWindow(this.frame,MetroLinkTimes);
            this.frame.revalidate();
            this.frame.repaint();
        }
        // outputs delay menu if add delays is pressed
        if(ToCorrect==1 && FromCorrect==1 ){
            // checks if both values are correct and then outputs correct. 
            String route="";
            // initialises the add edges 
            if (this.delays==null) {
                TramNetwork=addEdges.CreateTramNetwork(MetroLinkTimes,null);
            }else{
                TramNetwork=addEdges.CreateTramNetwork(MetroLinkTimes,this.delays);
            }
            // stores the tram network made by the add edges
            if (e.getActionCommand().equals("Shortest Route")) {
                route=TramNetwork.dijkstras(startStop, endStop);
                // perfrms dijkstras if they clcik shortest route
            }
            if (e.getActionCommand().equals("Least Line Changes")) {
                route=TramNetwork.leastLineChanges(startStop, endStop);
                // performs the least line changes method if they click the button for least line changes
            }
            this.frame.getContentPane().removeAll();
            // removes everything in the fram
            new resultWindow(route, this.frame,TramNetwork);
            this.frame.revalidate();
            this.frame.repaint();
            // outputs the result window with the correct route
        }else{
            JLabel Tryagain=new JLabel("Incorrect names try again");
            Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
            Tryagain.setBounds((int)(screenSize.getWidth()/2)-150, 20, 300, 30);
            this.frame.add(Tryagain);
            this.frame.revalidate();
            // updates the layout 
            this.frame.repaint();
            // this then redraws the gui with the new label
            // if one of the stops are incorrect a message saying incorrect names try again will display to the user and they will have to enter correct stations in order to progress
        }
    }
}
