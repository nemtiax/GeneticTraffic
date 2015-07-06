package GraphModel;

public class Car {
	private static long uniqueIDCounter = 1;
	
	private final long id;
	
	public Car() {
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
