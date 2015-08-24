package GraphModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import Util.RekeyableHeap;

public class Simulation {
	
	Graph graph;
	
	HashMap<Itinerary,HashSet<Car>> allTrips;
	RekeyableHeap<Car,Double> heap;
	double initPhaseLength;
	Random r = new Random();
	
	public Simulation(Graph graph, double initPhaseLength) {
		this.graph = graph;
		this.initPhaseLength = initPhaseLength;
		allTrips = new HashMap<Itinerary,HashSet<Car>>();
		heap = new RekeyableHeap<>();
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
	public void initializeCars() {
		for(Itinerary itin: allTrips.keySet()) {
			for(Car c : allTrips.get(itin)) {
				c.setWaitingToSpawn(true);
				double time = r.nextDouble()*initPhaseLength;
				heap.insert(c, time);
			}
		}
	}
	public void run() {
		while(!heap.isEmpty()) {
			step();
		}
	
	}
	private void step() {
		
		RekeyableHeap<Car,Double>.HeapItem<Car,Double> nextEvent = heap.deleteMin();
		Car nextCar = nextEvent.getItem();
		Double eventTime = nextEvent.getKey();
		if(nextCar.isWaitingToSpawn()) {
			Node start = nextCar.getStartingNode();
			Node nextDestination = nextCar.getRoute().getNext(start);
			Edge nextEdge = start.getOutgoingEdge(nextDestination);
			addCarToEdge(nextCar,nextEdge,eventTime);
			System.out.println(nextCar + " spawns at " + start);
		} else {
			Edge currentEdge = nextCar.getCurrentEdge();
			Node arrival = currentEdge.getEnd();
			System.out.println(nextCar + " arrives at " + arrival);
			if(arrival.equals(nextCar.getEndingNode())) {
				//respawn
			} else {
				removeCarFromEdge(nextCar,nextCar.getCurrentEdge(),eventTime);
				Node nextDestination = nextCar.getRoute().getNext(arrival);
				Edge nextEdge = arrival.getOutgoingEdge(nextDestination);
				addCarToEdge(nextCar,nextEdge,eventTime);
			}
		}
	}
	private void removeCarFromEdge(Car currentCar, Edge nextEdge, double eventTime) {
		nextEdge.removeTravellingCar(currentCar);
		if(nextEdge.hasWaitingCar()) {
			Car waiter = nextEdge.removeNextWaitingCar();
			addCarToEdge(waiter,nextEdge,eventTime);
		}
	}
	private void addCarToEdge(Car currentCar, Edge nextEdge, double eventTime) {
		if(nextEdge.isFull()) {
			nextEdge.addWaitingCar(currentCar);
		} else {
			nextEdge.addTravellingCar(currentCar);
			double timeTaken = nextEdge.getLength();
			heap.insert(currentCar, eventTime+timeTaken);
		}
	}
	
	
}
