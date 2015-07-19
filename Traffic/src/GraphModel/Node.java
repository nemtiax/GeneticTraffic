package GraphModel;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Node {
	
	private static int uniqueIDCounter = 0;
	
	private Set<Edge> outgoingEdges;
	private Set<Edge> incomingEdges;
	private final int id;
	private Point2D position;
	
	public Node() {
		id = uniqueIDCounter++;
		outgoingEdges = new HashSet<Edge>();
		incomingEdges = new HashSet<Edge>();
	}
	
	public void setPosition(Point2D pos)
	{
		position = pos;
	}
	
	public Point2D getPosition()
	{
		return position;
	}
	
	public int getID() {
		return id;
	}
	
	public String toString() {
		return "Node " + id;
	}
	
	public void addOutgoingEdge(Edge e) {
		outgoingEdges.add(e);
	}
	public void addIncomingEdge(Edge e) {
		incomingEdges.add(e);
	}
	public int hashCode() {
		return id;
	}
	public boolean equals(Object o) {
		if(o instanceof Node) {
			return id == ((Node)o).id;
		}
		return false;
	}
}
