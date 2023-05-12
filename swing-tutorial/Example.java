import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Example
{
    public static void main(String[] args)
    {
        // Create a frame with title "Example"
        JFrame frame = new JFrame("Example");

        // Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label and add it to the frame
        JLabel label = new JLabel("Hello World!");
        label.setPreferredSize(new Dimension(200, 50));
        frame.getContentPane().add(label);

        // Size the frame, automatically
        frame.pack();

        // Show the frame
        frame.setVisible(true);
    }
}
