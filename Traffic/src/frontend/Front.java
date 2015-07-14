package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GraphModel.Graph;
import GraphModel.GraphGenerator;
import GraphModel.Node;

public class Front extends JFrame {
	Graph graph;

	public Front(Graph g) {
		this.graph = g;
		initUI();
	}

	private void initUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Amazing Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel emptyLabel = new JLabel("Hello");
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		int maxX = 0;
		int maxY = 0;
		Set<Node> allNodes = graph.getAllNodes();
		for (Node n : allNodes) {
			int x = (int) n.getPosition().getX();
			int y = (int) n.getPosition().getY();
			if (x > maxX) {
				maxX = x;
			}
			if (y > maxY) {
				maxY = y;
			}
		}
		maxX += 20;
		maxY /= 2;
		maxY += 20;
		BufferedImage image = createImage(allNodes, maxX, maxY);

		RectDraw drawing = new RectDraw(image, maxX, maxY);

		frame.getContentPane().add(drawing);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private BufferedImage createImage(Set<Node> allNodes, int x, int y) {

		BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.BLACK);
		// g2.drawString("Hello world", 30, 30);

		for (Node n : allNodes) {
			Point2D pos = n.getPosition();
			addNode((int) pos.getX(), (int) pos.getY() /2, g2);
		}
		return image;
	}

	private void addNode(int x, int y, Graphics2D graphic) {
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
				GraphGenerator gen = new GraphGenerator(10, 10);
				Graph g = gen.generate();
				Front f = new Front(g);
				g.toString();

			}
		});
	}

}
