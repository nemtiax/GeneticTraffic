package frontend;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GraphModel.Edge;
import GraphModel.Graph;
import GraphModel.GraphGenerator;
import GraphModel.Node;

public class Front extends JFrame {
	final static float dash1[] = {5.0f};
	final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	Graph graph;
	private final int ARR_SIZE = 4;

	public Front(Graph g) {
		this.graph = g;
		initUI();
	}

	private void initUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Amazing Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		Set<Edge> allEdges = graph.getAllEdges();
		maxX += 20;
		maxY /= 2;
		// maxX /= 2;
		maxY += 20;
		BufferedImage image = createImage(allEdges, allNodes, maxX, maxY);

		RectDraw drawing = new RectDraw(image, maxX, maxY);

		frame.getContentPane().add(drawing);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private BufferedImage createImage(Set<Edge> allEdges, Set<Node> allNodes, int x, int y) {

		BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		// g2.drawString("Hello world", 30, 30);

		drawEdges(allEdges, g2);
		drawNodes(allNodes, g2);
		return image;
	}

	private void drawNodes(Set<Node> allNodes, Graphics2D g2) {

		for (Node n : allNodes) {
			// Point2D pos = n.getPosition();
			addNode(n, g2);
		}
	}

	private void drawEdges(Set<Edge> allEdges, Graphics2D g2) {
		for (Edge e : allEdges) {
			// drawArrow(g2, (int)
			// startPos.getX(),(int)startPos.getY()/2,100,100);
			// drawArrow(g2, 30,300,300,190);
			drawRoad(g2, e);
		}
	}

	// Luka version that works, but undirected arrows
	private void drawRoad(Graphics2D g2, Edge e) {
		Point2D startPos = e.getStart().getPosition();
		Point2D endPos = e.getEnd().getPosition();
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.gray);
		g2.drawLine((int) startPos.getX(), (int) startPos.getY() / 2, (int) endPos.getX(), (int) endPos.getY() / 2);
		g2.setColor(Color.white);
		g2.setStroke(dashed);
		g2.drawLine((int) startPos.getX(),(int) startPos.getY() / 2 , (int) endPos.getX(), (int) endPos.getY() / 2);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.black);

	}

	private void addNode(Node n, Graphics2D graphic) {
		graphic.setColor(Color.GRAY);
		// graphic.drawRect((int)n.getPosition().getX(),
		// (int)n.getPosition().getY(), 10, 10);
		// graphic.fillRect((int)n.getPosition().getX(),
		// (int)n.getPosition().getY()/2, 10, 10);
		graphic.fillOval((int) n.getPosition().getX() - 10, (int) n.getPosition().getY() / 2 - 10, 20, 20);
		graphic.setColor(Color.BLACK);
		graphic.drawString(n.getLabel(), (int) n.getPosition().getX(), (int) n.getPosition().getY() / 2);

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
				Graph g = null;
				try {
					g = Graph.readFromFile(new File("Graphs/SimpleDiamond.txt"), false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// g.generateShortestPaths();
				Front f = new Front(g);
				g.toString();

			}
		});
	}

}
