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
	
	public void generateShortestPaths() {
		
		
		double[][] distances = new double[allNodes.size()][allNodes.size()];
		Node[][] parent = new Node[allNodes.size()][allNodes.size()];
		for(int i = 0;i<allNodes.size();i++) {
			for(int j = 0;j<allNodes.size();j++) {
				distances[i][j] = Double.POSITIVE_INFINITY;
				parent[i][j] = null;
			}
		}
		
		for(Edge e : allEdges) {
			int startID = (int)e.getStart().getID();
			int endID = (int)e.getEnd().getID();
			distances[startID][endID] = e.getLength();
		}
		
		for(Node destination : allNodes) {
			
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
