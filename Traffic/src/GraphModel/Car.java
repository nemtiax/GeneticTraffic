package GraphModel;

public class Car {
	private static long uniqueIDCounter = 1;
	
	private final long id;
	private Node source, destination;
	
	public Car(Node source, Node destination) {
		this.id = uniqueIDCounter++;
		
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
