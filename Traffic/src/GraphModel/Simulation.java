package GraphModel;

import java.util.HashMap;
import java.util.HashSet;

public class Simulation {
	
	Graph graph;
	
	HashMap<Itinerary,HashSet<Car>> allTrips;
	
	public Simulation(Graph graph) {
		this.graph = graph;
		allTrips = new HashMap<Itinerary,HashSet<Car>>();
	}
	public void addRoute(Node start, Node end, int numCars) {
		Itinerary route = new Itinerary(start,end);
		if(!allTrips.containsKey(route)) {
			allTrips.put(route,new HashSet<Car>());
		}
		for(int i = 0;i<numCars;i++) {
			Car nextCar = new Car(start,end);
			allTrips.get(route).add(nextCar);
		}
	}
}
