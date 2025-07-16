import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
public class Driver {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Summer Project");
        // creates an instance of Jframe with a title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        // sets the close operation to exit on close
        frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        //sets the width and height of the frame
        frame.setVisible(false);
        // makes the frame visible
        new MainWindow(frame,null);
        // outputs the main window
    }
}