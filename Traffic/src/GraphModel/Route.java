package GraphModel;

import java.util.HashMap;

public class Route {
	private HashMap<Node, Node> next;
	private Node last;
	private StringBuilder toString;
	public Route(Node start) {
		next = new HashMap<Node,Node>();
		last = start;
		toString = new StringBuilder();
		toString.append(start.toString());
	}
	public void setNext(Node nextNode) {
		next.put(last,nextNode);
		last = nextNode;
		toString.append(" => " + nextNode);
	}
	public Node getNext(Node someNode) {
		return next.get(someNode);
	}
	public String toString() {
		return toString.toString();
	}
	
}
