package GraphModel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Edge {
	private Node start,end;
	private LinkedList<Car> waiting; //TODO: use a different data structure so that removing an arbitrary car is faster
	private Set<Car> travelling;
	
	private final double length;
	private final int capacity;
	
	public Edge(Node start,Node end, double length, int capacity) {
		this.start = start;
		this.end = end;
		
		this.length = length;
		this.capacity = capacity;
		
		waiting = new LinkedList<>();
		travelling = new HashSet<Car>();
	}
	
	public double getLength() {
		return length;
	}
	public int getCapacity() {
		return capacity;
	}
	
	public void addWaitingCar(Car c) {
		waiting.add(c);
	}
	public void addTravellingCar(Car c) {
		travelling.add(c);
	}
	public void removeTravellingCar(Car c) {
		travelling.remove(c);
	}
	public boolean hasWaitingCar() {
		
		return !waiting.isEmpty();
	}
	public Car removeNextWaitingCar() {
		return waiting.removeFirst();
	}
	public void removeWaitingCar(Car c) {
		waiting.remove(c);
	}
	
	public Node getStart() {
		return start;
	}
	public Node getEnd() {
		return end;
	}
	public boolean isFull() {
		return travelling.size() == capacity;
	}
	
	
	public int hashCode() {
		return (int)(start.getID() + 257 * end.getID());
	}
	
	public boolean equals(Object o) {
		if(o instanceof Edge) {
			Edge otherEdge = (Edge)o;
			return start.equals(otherEdge.start) && end.equals(otherEdge.end);
		}
		return false;
	}
	
	public String toString() {
		return start + " --> " + end + "; length=" + length + "; capacity=" + capacity;
	}
}
