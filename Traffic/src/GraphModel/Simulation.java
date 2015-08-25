package GraphModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Util.RekeyableHeap;

public class Simulation {
	
	Graph graph;
	
	HashMap<Itinerary,ArrayList<Car>> allTrips;
	RekeyableHeap<Car,Double> heap;
	double initPhaseLength;
	Random r = new Random();
	
	public Simulation(Graph graph, double initPhaseLength) {
		this.graph = graph;
		this.initPhaseLength = initPhaseLength;
		allTrips = new HashMap<Itinerary,ArrayList<Car>>();
		heap = new RekeyableHeap<>();
	}
	public void addRoute(Node start, Node end, int numCars) {
		Itinerary route = new Itinerary(start,end);
		if(!allTrips.containsKey(route)) {
			allTrips.put(route,new ArrayList<Car>());
		}
		for(int i = 0;i<numCars;i++) {
			Car nextCar = new Car(start,end);
			nextCar.generateInitialRoute(graph);
			//System.out.println(nextCar.routeString());
			nextCar.setWaitingToSpawn();
			allTrips.get(route).add(nextCar);
		}
	}
	public void initializeCars() {
		for(Itinerary itin: allTrips.keySet()) {
			for(Car c : allTrips.get(itin)) {
				c.setWaitingToSpawn();
				double time = r.nextDouble()*initPhaseLength;
				heap.insert(c, time);
			}
		}
		
	}
	public void run() {
		int count = 0;
		while(!heap.isEmpty() && count<100000) {
			if(count%10000==0) {
				System.out.println("Statistics for step " + count);
				printStatistics();
			}
			step();
			count++;
			
			
		}
	
	}
	
	public void printStatistics() {
		for(Itinerary itin : allTrips.keySet()) {
			System.out.println("Routes for " + itin.getSource() + "=>" + itin.getDestination());
			System.out.println("=============");
			HashMap<String,Integer> routeCounts = new HashMap<String,Integer>();
			
			for(Car c : allTrips.get(itin)) {
				String routeString = c.routeString();
				if(!routeCounts.containsKey(routeString)) {
					routeCounts.put(routeString, 0);
				}
				routeCounts.put(routeString,routeCounts.get(routeString)+1);
			}
			
			ArrayList<String> allRouteStrings = new ArrayList<>(routeCounts.keySet());
			Collections.sort(allRouteStrings,new RouteSorter(routeCounts));
			for(String route : allRouteStrings) {
				System.out.println(route + " " + routeCounts.get(route));
			}
			
			
		}
	}
	private class RouteSorter implements Comparator<String> {
		private Map<String,Integer> counts;
		public RouteSorter(Map<String,Integer> counts) {
			this.counts = counts;
		}
		
		@Override
		public int compare(String arg0, String arg1) {
			return -1*counts.get(arg0).compareTo(counts.get(arg1));//multiply by -1 to reverse results so largest is first
		}
		
	}
	
	private void step() {
		
		RekeyableHeap<Car,Double>.HeapItem<Car,Double> nextEvent = heap.deleteMin();
		Car nextCar = nextEvent.getItem();
		Double eventTime = nextEvent.getKey();
		//System.out.println("Event for " + nextCar + " at " + eventTime);
		if(nextCar.isWaitingToSpawn()) {
			Node start = nextCar.getStartingNode();
			Node nextDestination = nextCar.getRoute().getNext(start);
			Edge nextEdge = start.getOutgoingEdge(nextDestination);
			addCarToEdge(nextCar,nextEdge,eventTime);
			//System.out.println(nextCar + " spawns at " + start);
		} else {
			Edge currentEdge = nextCar.getCurrentEdge();
			Node arrival = currentEdge.getEnd();
		//	System.out.println(nextCar + " arrives at " + arrival);
			removeCarFromEdge(nextCar,nextCar.getCurrentEdge(),eventTime);
			if(arrival.equals(nextCar.getEndingNode())) {
				nextCar.setWaitingToSpawn();
				heap.insert(nextCar, eventTime + r.nextDouble()*initPhaseLength);
				
				//select and kill random car on same route
				killRandomSiblingAndClone(nextCar,eventTime);
				
				//replace with clone
				
				
				
			} else {
				Node nextDestination = nextCar.getRoute().getNext(arrival);
				Edge nextEdge = arrival.getOutgoingEdge(nextDestination);
				addCarToEdge(nextCar,nextEdge,eventTime);
			}
		}
	}
	
	private void killRandomSiblingAndClone(Car c, double time) {
		ArrayList<Car> siblings = allTrips.get(new Itinerary(c.getStartingNode(),c.getEndingNode()));
		Car randomSibling = siblings.remove(r.nextInt(siblings.size()));
		if(heap.contains(randomSibling)) {
			heap.delete(randomSibling);
		}
		if(randomSibling.isWaitingForEdge()) {
			removeWaitingCarFromEdge(randomSibling,randomSibling.getCurrentEdge());
		} else if(randomSibling.isTravellingOnEdge()) {
			removeCarFromEdge(randomSibling,randomSibling.getCurrentEdge(),time);
		}
		Car clone = c.clone();
		heap.insert(clone,time + r.nextDouble()*initPhaseLength);
		siblings.add(clone);
	}
	
	private void removeWaitingCarFromEdge(Car car, Edge edge) {
		edge.removeWaitingCar(car);
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
			currentCar.setWaitingForEdge(nextEdge);
		} else {
			nextEdge.addTravellingCar(currentCar);
			double timeTaken = nextEdge.getLength();
			heap.insert(currentCar, eventTime+timeTaken);
			currentCar.setTravellingOnEdge(nextEdge);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Graph g = Graph.readFromFile(new File("Graphs/SimpleDiamond.txt"), false);
		g.generateShortestPaths();
		Simulation sim = new Simulation(g,100);
		sim.addRoute(g.getNodeByLabel("1"), g.getNodeByLabel("3"), 50);
		sim.addRoute(g.getNodeByLabel("2"), g.getNodeByLabel("4"), 50);
		sim.initializeCars();
		sim.run();
	}
	
}
