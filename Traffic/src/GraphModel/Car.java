package GraphModel;

import java.util.Iterator;
import java.util.LinkedList;

public class Car {
	private static long uniqueIDCounter = 1;
	
	private final long id;
	private Node source, destination;
	private LinkedList<Node> genome = new LinkedList<>();
	private Route myRoute;
	private boolean isWaitingToSpawn;
	private boolean isWaitingForEdge;
	private boolean isTravellingOnEdge;
	private Edge currentEdge;
	
	public Car(Node source, Node destination) {
		this.id = uniqueIDCounter++;
		this.source = source;
		this.destination = destination;
		
		this.isWaitingToSpawn = true;
		this.isWaitingForEdge = false;
		this.isTravellingOnEdge = false;
		this.currentEdge = null;
	}
	public void setCurrentEdge(Edge e) {
		currentEdge = e;
	}
	public Edge getCurrentEdge() {
		return currentEdge;
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
			genome.add(current);
			current = firstLeg.getNext(current);
		}
		current = secondLeg.getStart();
		current = secondLeg.getNext(current); //skip shared instance of intermediate between legs
		while(current!=null) {
			genome.add(current);
			current = secondLeg.getNext(current);
		}
		buildRouteFromGenome();
	}
	public Car clone() {
		Car result = new Car(source,destination);
		result.genome = new LinkedList<>(this.genome);
		result.myRoute = this.myRoute;
		
		return result;
	}
	public Route getRoute() {
		return myRoute;
	}
	private void buildRouteFromGenome() {
		
		Iterator<Node> iter = genome.iterator();
		myRoute = new Route(iter.next());
		while(iter.hasNext()) {
			myRoute.setNext(iter.next());
		}
	}
	
	public Node getStartingNode() {
		return genome.getFirst();
	}
	public Node getEndingNode() {
		return genome.getLast();
	}
	public boolean isWaitingForEdge() {
		return isWaitingForEdge;
	}
	public boolean isTravellingOnEdge() {
		return isTravellingOnEdge;
	}
	public boolean isWaitingToSpawn() {
		return isWaitingToSpawn;
	}
	//ensure that only one of these is ever set
	public void setWaitingToSpawn() {
		isWaitingToSpawn = true;
		currentEdge = null;
		isTravellingOnEdge = false;
		isWaitingForEdge = false;
	}
	public void setTravellingOnEdge(Edge e) {
		isWaitingToSpawn = false;
		isTravellingOnEdge = true;
		currentEdge = e;
		isWaitingForEdge = false;
		
	}
	public void setWaitingForEdge(Edge e) {
		isWaitingToSpawn = false;
		isTravellingOnEdge = false;
		isWaitingForEdge = true;
		currentEdge = e;
	}
	public String toString() {
		return "[Car route: " + routeString() + "]";
	}
	
	public String routeString() {
		StringBuilder result = new StringBuilder();
		for(Node n : genome) {
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
