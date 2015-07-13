package frontend;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Front extends JFrame {

	public Front() {

		initUI();
	}

	private void initUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Amazing Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel emptyLabel = new JLabel("");
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.BLACK);
		// g2.drawString("Hello world", 30, 30);

		addNode(10, 10, g2);
		addNode(30, 30, g2);
		addNode(60, 60, g2);
		addNode(90, 90, g2);
		addNode(110, 110, g2);
		addNode(300, 300, g2);

		int maxX = 1000;
		int maxY = 1000;

		RectDraw drawing = new RectDraw(image, maxX, maxY);

		frame.getContentPane().add(drawing);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addNode(int x, int y, Graphics2D graphic) {
		// RectDraw newrect = new RectDraw(x, y);
		// drawing.add(newrect);
		graphic.setColor(Color.RED);
		graphic.drawRect(x, y, 10, 10);
		graphic.fillRect(x, y, 10, 10);
		graphic.setColor(Color.BLACK);

	}

	private static class RectDraw extends JPanel {
		private BufferedImage image;
		int x;
		int y;

		public RectDraw(BufferedImage m, int x, int y) {
			this.image = m;
			this.x = x;
			this.y = y;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}

		public Dimension getPreferredSize() {
			return new Dimension(x, y); // appropriate constants
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Front f = new Front();
			}
		});
	}

}
