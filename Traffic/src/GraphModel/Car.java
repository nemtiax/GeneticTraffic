package GraphModel;

import java.util.LinkedList;

public class Car {
	private static long uniqueIDCounter = 1;
	
	private final long id;
	private Node source, destination;
	LinkedList<Node> myRoute = new LinkedList<>();
	
	public Car(Node source, Node destination) {
		this.id = uniqueIDCounter++;
		this.source = source;
		this.destination = destination;
		System.out.println("Making car from " + source + " to " + destination);
	}
	public void generateInitialRoute(Graph g) {
		Node intermediate = g.getRandomNode();
		while(!g.reachable(source,intermediate) || !g.reachable(intermediate,destination)) {
			intermediate = g.getRandomNode();
		}
		Route firstLeg = g.getRoute(source,intermediate);
		Route secondLeg = g.getRoute(intermediate, destination);
		
		Node current = firstLeg.getStart();
		while(current!=null) {
			myRoute.add(current);
			current = firstLeg.getNext(current);
		}
		current = secondLeg.getStart();
		current = secondLeg.getNext(current); //skip shared instance of intermediate between legs
		while(current!=null) {
			myRoute.add(current);
			current = secondLeg.getNext(current);
		}
		
	}
	public String routeString() {
		StringBuilder result = new StringBuilder();
		for(Node n : myRoute) {
			result.append(n + "=>");
		}
		return result.toString();
	}
	public boolean equals(Object o) {
		if(o instanceof Car) {
			return id == ((Car)o).id;
		}
		return false;
	}
	public int hashCode() {
		return (int)id;
	}
}
