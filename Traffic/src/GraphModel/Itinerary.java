package GraphModel;

public class Itinerary {
	private Node source, destination;
	public Itinerary(Node s, Node d) {
		source = s;
		destination = d;
	}
	public Node getSource() {
		return source;
	}
	public Node getDestination() {
		return destination;
	}
	public boolean equals(Object o) {
		if(o instanceof Itinerary) {
			Itinerary other = (Itinerary)o;
			return source.equals(other.source) && destination.equals(other.destination);
		}
		return false;
	}
	public int hashCode() {
		return source.hashCode() + 257*destination.hashCode();
	}
	
}
