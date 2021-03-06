package GraphModel;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Graph {
	private Set<Node> allNodes;
	private Set<Edge> allEdges;
	private Map<String, Node> nodesByLabels;
	
	private HashMap<Node, HashMap<Node, Route>> shortestRoutes;
	private double[][] distances;
	private Random r = new Random();
	public Graph() {
		allNodes = new HashSet<Node>();
		allEdges = new HashSet<Edge>();
		shortestRoutes = new HashMap<>(); 
		nodesByLabels = new HashMap<>();
		
	}
	
	
	
	public void addNode(Node n) {
		if(nodesByLabels.containsKey(n.getLabel()) && !nodesByLabels.get(n.getLabel()).equals(n)) {
			throw new IllegalArgumentException("Could not add node: " + n + " because graph already contains a node which that label: " + nodesByLabels.get(n.getLabel()));
		}
		allNodes.add(n);
		nodesByLabels.put(n.getLabel(), n);
	}
	public Node getNodeByLabel(String label) {
		return nodesByLabels.get(label);
	}
	public void addEdge(Edge e) {
		allEdges.add(e);
		e.getStart().addOutgoingEdge(e);
		e.getEnd().addIncomingEdge(e);
	}
	public boolean containsEdge(Edge e) {
		return allEdges.contains(e);
	}
	public int getNumNodes() {
		return allNodes.size();
	}
	public int getNumEdges() {
		return allEdges.size();
	}
	public Set<Edge> getAllEdges() {
		return allEdges;
	}
	public Set<Node> getAllNodes()
	{
		return allNodes;
	}
	public Node getRandomNode() {
		ArrayList<Node> nodeList = new ArrayList<Node>(allNodes);
		return nodeList.get(r.nextInt(nodeList.size()));
	}
	
	public boolean reachable(Node source, Node dest) {
		//System.out.println(source + " " + dest);
		return distances[source.getID()][dest.getID()]!=Double.POSITIVE_INFINITY;
	}
	public Route getRoute(Node start, Node end) {
		return shortestRoutes.get(start).get(end);
	}
	public void generateShortestPaths() {
		//Floyd-Warshall
		
		distances = new double[allNodes.size()][allNodes.size()];
		Node[][] child = new Node[allNodes.size()][allNodes.size()];
		for(int i = 0;i<allNodes.size();i++) {
			for(int j = 0;j<allNodes.size();j++) {
				distances[i][j] = Double.POSITIVE_INFINITY;
				child[i][j] = null;
				if(i==j) {
					distances[i][j] = 0;
				}
			}
		}
		
		for(Edge e : allEdges) {
			int startID = e.getStart().getID();
			int endID = e.getEnd().getID();
			distances[startID][endID] = e.getLength();
			child[startID][endID] = e.getEnd();
		}
		
		for(Node k : allNodes) {
			for(Node j : allNodes) {
				for(Node i : allNodes) {
					if(distances[i.getID()][k.getID()] + distances[k.getID()][j.getID()] < distances[i.getID()][j.getID()]) {
						distances[i.getID()][j.getID()] = distances[i.getID()][k.getID()] + distances[k.getID()][j.getID()];
						child[i.getID()][j.getID()] = child[i.getID()][k.getID()];
					}
				}
			}
		}
		for(Node start : allNodes) {
			shortestRoutes.put(start,new HashMap<Node, Route>());
			for(Node end : allNodes) {
				//System.out.println("Distance from " + start + " to " + end + " is " + distances[start.getID()][end.getID()]);
				if(distances[start.getID()][end.getID()]!=Double.POSITIVE_INFINITY) {
					Route startToEnd = new Route(start);
					Node current = start;
					while(!current.equals(end)) {
						current = child[current.getID()][end.getID()];
						startToEnd.setNext(current);
					}
					//System.out.println(startToEnd);
					shortestRoutes.get(start).put(end,startToEnd);
				}
			}
		}
	}
	
	public static Graph readFromFile(File input, boolean includesLengths) throws IOException {
		Scanner s = new Scanner(input);
		Graph result = new Graph();
		HashMap<String,Node> nodesByID = new HashMap<String,Node>();
		int numNodes = s.nextInt();
		int numEdges = s.nextInt();
		for(int n = 0;n<numNodes;n++) {
			String id = s.next();
			double x = s.nextDouble();
			double y = s.nextDouble();
			Node node = new Node(id);
			node.setPosition(new Point2D.Double(x,y));
			nodesByID.put(id,node);
			result.addNode(node);
		}
		for(int e = 0;e<numEdges;e++) {
			String firstID = s.next();
			String secondID = s.next();
			Node start = nodesByID.get(firstID);
			Node end = nodesByID.get(secondID);
			double length;
			int capacity;
			if(includesLengths) {
				 length = s.nextDouble();
				 capacity = s.nextInt();
			} else {
				length = getLengthFromNodes(start,end);
				capacity = getCapacityFromLength(length);
			}
			Edge edge = new Edge(start,end,length,capacity);
			result.addEdge(edge);
		}
		s.close();
		
		return result;
	}
	private static double getLengthFromNodes(Node s, Node e) {
		Point2D start = s.getPosition();
		Point2D end = e.getPosition();
		return Math.sqrt((start.getX()-end.getX())*(start.getX()-end.getX()) + (start.getY()-end.getY())*(start.getY()-end.getY()));
	}
	
	private static int getCapacityFromLength(double length) {
		return Math.max(1,(int)(length/25));
	}
	
	public void writeToFile(File output) throws IOException {
		FileWriter out = new FileWriter(output);
		
		out.write(getNumNodes() + " " + getNumEdges() + "\n");
		for(Node node : allNodes) {
			out.write(node.getPosition().getX() + " " + node.getPosition().getY() + "\n");
		}
		
		for(Edge edge : allEdges) {
			out.write(edge.getStart().getID() + " " + edge.getEnd().getID() + " " + edge.getLength() + " " + edge.getCapacity() + "\n");
		}
		
		out.flush();
		out.close();
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Nodes:\n");
		for(Node n : allNodes) {
			result.append("\t" + n + "\n");
		}
		result.append("Edges:\n");
		for(Edge e : allEdges) {
			result.append("\t" + e + "\n");
		}
		return result.toString();
	}
}

