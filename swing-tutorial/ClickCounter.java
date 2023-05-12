import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClickCounter
{
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Click Counter");
                frame.setDefaultCloseOperation(
                                         JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new CounterPanel());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}

class CounterPanel extends JPanel
{
    private int count;
    private JLabel label;
    private JButton button;

    CounterPanel()      // no need to make public
    {
        super();
        count = 0;
        label = new JLabel("Clicks: 0");
        // Make the label a little bit longer
        label.setPreferredSize(new Dimension(80, 10));
        button = new JButton("Click here!");
        button.addActionListener(new ButtonListener());

        add(label);
        add(button);
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            count += 1;
            label.setText("Clicks: " + count);
        }
    }
}
