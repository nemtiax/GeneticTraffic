package GraphModel;

import java.util.HashMap;

public class Route {
	private HashMap<Node, Node> next;
	private Node last;
	public Route(Node start) {
		next = new HashMap<Node,Node>();
		last = start;
	}
	public void setNext(Node nextNode) {
		next.put(last,nextNode);
		last = nextNode;
	}
	public Node getNext(Node someNode) {
		return next.get(someNode);
	}
}
