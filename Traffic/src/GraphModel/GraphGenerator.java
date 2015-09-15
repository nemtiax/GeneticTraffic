package GraphModel;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GraphGenerator {
	private int numNodes, numEdges;
	public GraphGenerator(int numNodes, int numEdges) {
		this.numNodes = numNodes;
		this.numEdges = numEdges;
	}
	public Graph generate() {
		Random r = new Random();
		Graph result = new Graph();
		HashMap<Point2D.Double, Node> nodes = new HashMap<>();
		for(int i = 0;i<numNodes;i++) {
			Point2D.Double randomPoint = new Point2D.Double(r.nextDouble()*1000,r.nextDouble()*1000);
			Node n = new Node();
			n.setPosition(randomPoint);
			nodes.put(randomPoint,n);
			result.addNode(n);
		}
		
		ArrayList<Point2D.Double> pointsToSample = new ArrayList<>(nodes.keySet());
		
		
		
		while(result.getNumEdges()<numEdges) {
			int randomStartIndex = r.nextInt(numNodes);
			int randomEndIndex = r.nextInt(numNodes);
			while(randomStartIndex == randomEndIndex) {
				randomStartIndex = r.nextInt(numNodes);
				randomEndIndex = r.nextInt(numNodes);
			}
			Point2D.Double start = pointsToSample.get(randomStartIndex);
			Point2D.Double end = pointsToSample.get(randomEndIndex);
			
			double length = Math.sqrt((start.x-end.x)*(start.x-end.x) + (start.y-end.y)*(start.y-end.y));
			
			Edge e = new Edge(nodes.get(start),nodes.get(end),length,Math.max(1,(int)(length/25)));
			if(!result.containsEdge(e)) {
				result.addEdge(e);
			}
		}
		return result;
		
		
		
	}
	public static void main(String[] args) {
		GraphGenerator gen = new GraphGenerator(10,35);
		Graph result = gen.generate();
		System.out.println(result);
		result.generateShortestPaths();
		Car c = new Car(result.getRandomNode(),result.getRandomNode());
		c.generateInitialRoute(result);

	}
}
