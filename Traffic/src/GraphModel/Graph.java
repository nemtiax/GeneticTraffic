package GraphModel;

import java.util.HashSet;
import java.util.Set;

public class Graph {
	private Set<Node> allNodes;
	private Set<Edge> allEdges;
	public Graph() {
		allNodes = new HashSet<Node>();
		allEdges = new HashSet<Edge>();
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
