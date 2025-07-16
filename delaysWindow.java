import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class delaysWindow implements ActionListener{
    private Map<String,Double> delays;
    private JTextField station;
    private JTextField time;
    private JFrame frame;
    private CSVreader MetroLinkTimes;
    public delaysWindow(JFrame frame,CSVreader MetroLinkTimes){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame=frame;
        this.MetroLinkTimes=MetroLinkTimes;
        this.delays=new HashMap<>();
        station=new JTextField(15);
        station.setBounds((int)(screenSize.getWidth()/2)-100,100,200,50);
        JLabel stationLabel=new JLabel("Stop");
        stationLabel.setBounds((int)(screenSize.getWidth()/2)-25,50,50,50);
        time=new JTextField(15);
        time.setBounds((int)(screenSize.getWidth()/2)-100,200,200,50);
        JLabel timeLabel=new JLabel("Time");
        timeLabel.setBounds((int)(screenSize.getWidth()/2)-25,150,50,50);
        JButton addDelay=new JButton("Add Delay");
        addDelay.setBounds((int)(screenSize.getWidth()/2)-75,300,150,50);
        addDelay.addActionListener(this);
        JButton home=new JButton("Home");
        home.setBounds((int)(screenSize.getWidth()/2)-75,400,150,50);
        home.addActionListener(this);
        this.frame.add(home);
        this.frame.add(addDelay);
        this.frame.add(timeLabel);
        this.frame.add(time);
        this.frame.add(stationLabel);
        this.frame.add(station);
        this.frame.revalidate();
        this.frame.repaint();
        // initialises ands adds all of the necessary objects to the delay screen allowing user to enter delay at a tram stop
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Delay")) {
            String delayText=station.getText();
            // gets the station in the text field
            try{
                Double timeText= Double.valueOf(time.getText());
                // casts the time into a double
                if (this.MetroLinkTimes.get_from().contains(delayText)) {
                    this.delays.putIfAbsent(delayText, timeText);
                    // checks if valid station and then puts it in the delays hashmap if not already in
                    station.setText("");
                    time.setText("");
                }
            } catch (NumberFormatException error) {
                System.err.println(error);
                // says error if unable to do the code in the try block
            }
        }
        if (e.getActionCommand().equals("Home")) {
            this.frame.getContentPane().removeAll();
            new MainWindow(this.frame, this.delays);
            // if home is pressed the home screen is displayed
        }
    }
    public Map<String,Double> get_delays(){
        return this.delays;
        // get method for the delays
    }
}
