package GraphModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
	private Set<Node> allNodes;
	private Set<Edge> allEdges;
	private HashMap<Node, HashMap<Node, Route>> shortestRoutes;
	public Graph() {
		allNodes = new HashSet<Node>();
		allEdges = new HashSet<Edge>();
		shortestRoutes = new HashMap<>();  
	}
	public void addNode(Node n) {
		allNodes.add(n);
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
	public Set<Node> getAllNodes()
	{
		return allNodes;
	}
	public Set<Edge> getAllEdges()
	{
		return allEdges;
	}
	
	public void generateShortestPaths() {
		//Floyd-Warshall
		
		double[][] distances = new double[allNodes.size()][allNodes.size()];
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
			for(Node end : allNodes) {
				System.out.println("Distance from " + start + " to " + end + " is " + distances[start.getID()][end.getID()]);
				if(distances[start.getID()][end.getID()]!=Double.POSITIVE_INFINITY) {
					Route startToEnd = new Route(start);
					Node current = start;
					while(!current.equals(end)) {
						current = child[current.getID()][end.getID()];
						startToEnd.setNext(current);
					}
					System.out.println(startToEnd);
				}
			}
		}
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
