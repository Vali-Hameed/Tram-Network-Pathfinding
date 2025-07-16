import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
// imports necessary libraries
public class resultWindow implements ActionListener {
    // uses action listener to add a button
    private JTextArea route;
    private JFrame frame;
    private TramNetwork TramNetwork;
    // sets attributes
    public resultWindow(String route, JFrame frame,TramNetwork TramNetwork){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.TramNetwork=TramNetwork;
        this.frame=frame;
        this.route=new JTextArea(route);
        // creates a text area with the route they need to take
        this.route.setBounds((int)(screenSize.getWidth()/2)-225,30,450,(int)(screenSize.getHeight()));
        JButton button=new JButton("Home");
        button.setBounds((int)(screenSize.getWidth()/2)-210, (int)screenSize.getHeight()-200, 150, 50);
        button.addActionListener(this);
        this.frame.add(button);
        // creates a button
        JButton gButton=new JButton("Graph");
        gButton.setBounds((int)(screenSize.getWidth()/2)+40, (int)screenSize.getHeight()-200, 150, 50);
        gButton.addActionListener(this);
        this.frame.add(gButton);
        this.frame.add(this.route);
        this.frame.revalidate();
        this.frame.repaint();
        // validates and paints it onto the frame
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Home")) {
            this.frame.getContentPane().removeAll();
            new MainWindow(this.frame,null);
            // makes the button remove all of the content and then display the main window
        }
        if (e.getActionCommand().equals("Graph")) {
            this.frame.getContentPane().removeAll();
            new graphWindow(this.frame, this.TramNetwork.get_nodes());
            // if graph button is pressed the graph window is displayed
        }
    }
}
