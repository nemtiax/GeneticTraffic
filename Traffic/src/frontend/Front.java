package frontend;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Front extends JFrame {
	JPanel drawing;

	public Front() {
		this.drawing = new JPanel();
		initUI();
	}

	private void initUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Amazing Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel emptyLabel = new JLabel("");
		//emptyLabel.setPreferredSize(new Dimension(175, 100));
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		drawing.setPreferredSize(new Dimension(1000, 500));
		frame.getContentPane().add(drawing);
		//addNode(100, 80);
		//addNode(80, 80);
		//addNode(30, 80);
		//addNode(0,0);

		BufferedImage image = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics(); //also tried image.createGraphics();
		g2.drawString("Hello world", 20, 20);
		
		
		drawing.getGraphics().drawImage(image, 10, 10, null);
		
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addNode(int x, int y, RectDraw panel) {
		//RectDraw newrect = new RectDraw(x, y);
		//drawing.add(newrect);
		

	}

	private static class RectDraw extends JPanel {
		private BufferedImage image;
		public RectDraw(BufferedImage m) {
			this.image = m;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}

		public Dimension getPreferredSize() {
			return new Dimension(200, 200); // appropriate constants
		}
	}

	public static void main(String[] args) {

		// Front ex = new Front();
		// ex.setVisible(true);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Front f = new Front();
			}
		});
	}

}
