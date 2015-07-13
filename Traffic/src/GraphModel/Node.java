package GraphModel;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Node {
	
	private static long uniqueIDCounter = 1;
	
	private Set<Edge> outgoingEdges;
	private Set<Edge> incomingEdges;
	private final long id;
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
	
	public long getID() {
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
	public boolean equals(Object o) {
		if(o instanceof Node) {
			return id == ((Node)o).id;
		}
		return false;
	}
}
