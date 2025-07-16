import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
public class graphWindow implements ActionListener {
    // for the home button
    private JFrame frame;
    private LinkedHashMap<String,String> TramNetwork;
    // attributes
    public graphWindow(JFrame frame, LinkedHashMap<String,String> TramNetwork){
        this.TramNetwork=TramNetwork;
        this.frame=frame;
        createGraph graph=new createGraph(this.TramNetwork);
        // creates the graph
        JScrollPane scrollPane = new JScrollPane(graph);
        // allows the graph to scroll
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //gets the size of the screen
        scrollPane.setBounds(100,(int)(screenSize.getHeight()/2)-400,(int) screenSize.getWidth()-200,400);
        // allows the scroll pane to be the full width of the screen
        JButton button=new JButton("Home");
        button.addActionListener(this);
        // creates home button
        button.setBounds((int)(screenSize.getWidth()/2)-150,(int)screenSize.getHeight()-200,150,50);
        this.frame.add(scrollPane);
        this.frame.add(button);
        this.frame.revalidate();
        this.frame.repaint();
        // adds all items and then validates and paints them onto the jframe
        }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.getContentPane().removeAll();
        new MainWindow(this.frame,null);
        this.frame.revalidate();
        this.frame.repaint();
        // oututs the home menu when the home button is pressed
    }
}
