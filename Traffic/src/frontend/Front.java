package frontend;

import java.awt.*;

import javax.swing.*;
public class Front extends JFrame
{
	public Front() {

        initUI();
    }

    private static void initUI() {
    	//Create and set up the window.
        JFrame frame = new JFrame("Amazing Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        JPanel drawing = new JPanel();
        drawing.setPreferredSize(new Dimension(300,300));
        frame.getContentPane().add(drawing);
        RectDraw newrect = new RectDraw();
        newrect.getPreferredSize();
        drawing.add(newrect);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {

		 //Front ex = new Front();
         //ex.setVisible(true);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initUI();
            }
        });
	}
	private static class RectDraw extends JPanel {
		  protected void paintComponent(Graphics g) {
		    super.paintComponent(g);  
		    g.drawRect(230,80,10,10);  
		    g.setColor(Color.RED);  
		    g.fillRect(230,80,10,10);  
		  }

		  public Dimension getPreferredSize() {
		    return new Dimension(200, 200); // appropriate constants
		  }
		}
}