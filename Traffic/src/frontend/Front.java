package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		Set<Edge> allEdges = graph.getAllEdges();
		maxX += 20;
		maxY /= 2;
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
		g2.setColor(Color.BLACK);
		// g2.drawString("Hello world", 30, 30);

		for (Node n : allNodes) {
			Point2D pos = n.getPosition();
			addNode(n, g2);
		}
		for (Edge e: allEdges)
		{
			Point2D startPos = e.getStart().getPosition();
			Point2D endPos = e.getEnd().getPosition();
			//drawArrow(g2, (int) startPos.getX(),(int)startPos.getY()/2,100,100);
			//drawArrow(g2, 30,300,300,190);
			drawArrow(g2,e);
		}
		return image;
	}
//Luka version that works, but undirected arrows
	private void drawArrow(Graphics2D g2, Edge e) {
		Point2D startPos = e.getStart().getPosition();
		Point2D endPos = e.getEnd().getPosition();
		g2.drawLine((int)startPos.getX(), (int)startPos.getY() / 2, (int)endPos.getX(), (int)endPos.getY() / 2);
	
	}
	//stackoverflow version that almost works
	  void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
         // Graphics2D g = (Graphics2D) g1.create();

          double dx = x2 - x1, dy = y2 - y1;
          double angle = Math.atan2(dy, dx);
          int len = (int) Math.sqrt(dx*dx + dy*dy);
          AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
          at.concatenate(AffineTransform.getRotateInstance(angle));
          g.transform(at);

          // Draw horizontal arrow starting in (0, 0)
          g.drawLine(0, 0, len, 0);
          g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                        new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
      }

	private void addNode(Node n, Graphics2D graphic) {
		graphic.setColor(Color.RED);
		//graphic.drawRect((int)n.getPosition().getX(), (int)n.getPosition().getY(), 10, 10);
		graphic.fillRect((int)n.getPosition().getX(), (int)n.getPosition().getY()/2, 10, 10);
		graphic.setColor(Color.BLACK);
		graphic.drawString(n.getLabel(),(int)n.getPosition().getX(), (int)n.getPosition().getY()/2);

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
					g = Graph.readFromFile(new File("Graphs/SimpleDiamond.txt"),false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//g.generateShortestPaths();
				Front f = new Front(g);
				g.toString();

			}
		});
	}

}
