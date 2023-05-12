Swing basics
============

[What are Swing and AWT?][]

A *[frame][]* is basically a window, and to create one in Swing we use
[`JFrame`][].  The example below creates a `JFrame`, puts a `JLabel` with
text "Hello World" in it, and displays it.

<!-- Example.java -->

```java
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
```

A *[panel][]* is a *container* like a frame.  We use a [`JPanel`][] to
contain and organize *components* such as [`JLabel`][], [`JButton`][],
or even other `JPanel`s!

A `JPanel` may also use _layout managers_, which are set by the
[`setLayout()`][] method like so:

```java
JPanel panel = new JPanel();
panel.setLayout(new FlowLayout());
```

Layout managers determine the size and position of components in a container.
There are many layout managers listed [here][layout managers]; `JPanel`
uses `FlowLayout` by default.

Sometimes it can be convenient to subclass `JPanel` to group related
components and variables.  So rather than writing

```java
JPanel panel = new JPanel();
JLabel label = new JLabel();
JButton button = new Button();
panel.add(label);
panel.add(button);
frame.getContentPane().add(panel);
```

in the main method, we could create a subclass:

```java
public class MyPanel extends JPanel
{
    private JLabel label;
    private JButton button;

    public MyPanel()
    {
        super();
        label = new JLabel();
        button = new Button();
        add(label);
        add(button);
    }
}
```

and then write

```java
frame.getContentPane().add(new MyPanel());
```

in the main method.  I will be doing it in the subclass way in the example
that follows.


Example: click counter
======================

An example I gave in class was a simple GUI program containing a label and
a button.  The program starts out with a window like this:


    /============================\
    |            +-------------+ |
    | Clicks: 0  | Click here! | |
    |            +-------------+ |
    \============================/

where "Clicks: 0" is a `JLabel` displaying the number of times the user has
pressed the button and "Click here!" is a `JButton`.  Each time the button
is pressed, the number is increased and the text on `JLabel` is changed.

<!-- ClickCounter.java -->

```java
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
```

In this example we added an `ActionListener` object to the button so that
every time the button is pressed down the `actionPerformed()` method of
that object is invoked.  `ButtonListener` implements `ActionListener` so that
it would increment the count by $1$ and change the label text correspondingly.

In addition, `SwingUtilities.invokeLater()` is used so that the GUI will be
executed asynchronously on the AWT event dispatch thread for thread safety,
as pointed out [here](https://stackoverflow.com/a/3551578).

For brevity on line $40$ an [anonymous class][] may be used instead of
declaring an inner class (the `ButtonListener` here):

```java
button.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            count += 1;
            label.setText("Clicks: " + count);
        }
    });
```


Websites I _strongly_ recommend reading
=======================================

Some of the helpful links I found on getting started with Swing:

*  [Java GUI Components][gui-comp]: provides a clear explanation of
   what _components_, _events_, and _listeners_ are and includes examples

*  [CSE 331 Section 7 "Java Swing"][uw-swing]: talks about class hierarchy
   of Swing & AWT classes and lists several common classes (I mentioned this
   in class)

*  [The Swing Tutorial][java-tut]: a part of the Java tutorials; can be
   useful to read through (although I won't deny it may be hard to
   understand)

There is definitely more out there so I encourage you to try to find what
suits your needs... at least, well, for me that's plenty of information
already.

You can also consider checking out the [NetBeans IDE][netbeans gui] which
might be easier to work with than the pure programming method...

(And when in doubt, look it up in [Stack Overflow][] or the
[documentation][javax.swing]!)


[what are swing and awt?]: https://stackoverflow.com/q/408820
[frame]: https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
[panel]: https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
[layout managers]: https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
[anonymous class]: https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
[netbeans ide]: https://netbeans.apache.org/
[netbeans gui]: https://netbeans.apache.org/kb/docs/java/quickstart-gui.html
[stack overflow]: https://stackoverflow.com/
[javax.swing]: https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/package-summary.html

[uw-swing]: https://courses.cs.washington.edu/courses/cse331/10sp/sections/section7.html
[gui-comp]: https://www.cs.usfca.edu/~apjoshi/cs112/notes/java_gui_components.pdf
[java-tut]: https://docs.oracle.com/javase/tutorial/uiswing/index.html

[`JFrame`]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html
[`JPanel`]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JPanel.html
[`JLabel`]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JLabel.html
[`JButton`]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JButton.html

[`setPreferredSize()`]: https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html#setPreferredSize-java.awt.Dimension-
[`setLayout()`]: https://docs.oracle.com/javase/8/docs/api/java/awt/Container.html#setLayout-java.awt.LayoutManager-
