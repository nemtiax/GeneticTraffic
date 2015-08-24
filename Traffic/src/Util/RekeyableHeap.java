package Util;

import java.util.ArrayList;
import java.util.HashMap;

public class RekeyableHeap<T,K extends Comparable<K>> {
	private ArrayList<HeapItem<T,K>> heap;
	private HashMap<T,Integer> lookupTable;
	public RekeyableHeap() {
		heap = new ArrayList<>();
		lookupTable = new HashMap<>();
	}
	
	public boolean isEmpty() {
		
		return heap.isEmpty();
	}
	private void swap(T first, T second) {
		int firstIndex = lookupTable.get(first);
		int secondIndex = lookupTable.get(second);
		
		HeapItem<T,K> firstItem = heap.get(firstIndex);
		HeapItem<T,K> secondItem = heap.get(secondIndex);
		
		heap.set(firstIndex, secondItem);
		heap.set(secondIndex, firstItem);
		lookupTable.put(first, secondIndex);
		lookupTable.put(second, firstIndex);
	}
	private void swap(int firstIndex, int secondIndex) {
		swap(heap.get(firstIndex).getItem(), heap.get(secondIndex).getItem());
	}
	public HeapItem<T,K> deleteMin() {
		swap(0,heap.size()-1);
		HeapItem<T,K> result = heap.remove(heap.size()-1);
		lookupTable.remove(result.getItem());
		if(!isEmpty())
		percDown(0);
		return result;
	}
	
	public T getNext() {
		return deleteMin().getItem();
	}
	
	public void insert(T item, K key) {
		HeapItem<T,K> newItem = new HeapItem<>(item,key);
		heap.add(newItem);
		lookupTable.put(item, heap.size()-1);
		percUp(heap.size()-1);
	}
	public void delete(T item) {
		HeapItem<T,K> heapItem = heap.get(lookupTable.get(item));
		heapItem.setAbsoluteMin(true);
		percUp(lookupTable.get(item));
		deleteMin();
	}
	
	private int getLeftChildIndex(int index) {
		return 2*index + 1;
	}
	private int getRightChildIndex(int index) {
		return 2*index + 2;
	}
	private int getParentIndex(int index) {
		return (index-1)/2;
	}
	
	private void percUp(int index) {
		HeapItem<T,K> current = heap.get(index);
		HeapItem<T,K> parent = null;
		int parentIndex = getParentIndex(index);
		if(parentIndex<0) {
			return;
		}
		parent = heap.get(parentIndex);
		if(parent.compareTo(current)>0) {
			swap(index,parentIndex);
			percUp(parentIndex);
		}
	}
	private void percDown(int index) {
		HeapItem<T,K> current = heap.get(index);
		HeapItem<T,K> leftChild = null;
		HeapItem<T,K> rightChild = null;
		int leftChildIndex = getLeftChildIndex(index);
		if(leftChildIndex>=heap.size()) { //neither child exists
			return;
		}
		leftChild = heap.get(leftChildIndex);
		int rightChildIndex = getRightChildIndex(index);
		if(rightChildIndex>=heap.size()) { //only left child exists
			if(current.compareTo(leftChild)>0) {
				swap(index,leftChildIndex);
				percDown(leftChildIndex);
			}
			return;
		}
		rightChild = heap.get(rightChildIndex);
		//both children exist
		if(rightChild.compareTo(leftChild)<=0 && rightChild.compareTo(current)<0) {
			swap(index,rightChildIndex);
			percDown(rightChildIndex);
		} else if(leftChild.compareTo(rightChild)<=0 && leftChild.compareTo(current)<0) {
			swap(index,leftChildIndex);
			percDown(leftChildIndex);
		}
	}
	
	
	
	public class HeapItem<T,K extends Comparable<K>> implements Comparable<HeapItem<T,K>> {
		
		private T item;
		private K key;
		boolean isMin;
		
		public HeapItem(T item, K key) {
			this.item = item;
			this.key = key;
			this.isMin = false;
		}
		public void setAbsoluteMin(boolean isMin) {
			this.isMin = isMin;
		}
		public K getKey() {
			return key;
		}
		public T getItem() {
			return item;
		}
		private void setKey(K newKey) {
			key = newKey;
		}
		public String toString() {
			return item + " : " + key;
		}
		public int compareTo(HeapItem<T,K> o) {
			if(o.isMin && isMin) {
				return 0;
			} else if(isMin) {
				return -1;
			} else if(o.isMin) {
				return 1;
			}
			return key.compareTo(o.key);
		}
	}
	
	public static void main(String[] args) {
		RekeyableHeap<String,Integer> test = new RekeyableHeap<>();
		test.insert("Eight", 8);
		test.insert("One", 1);
		test.insert("Five", 5);
		test.insert("Three", 3);
		test.insert("Two", 2);
		test.delete("Five");
		System.out.println(test.deleteMin());
		System.out.println(test.deleteMin());
		System.out.println(test.deleteMin());
		System.out.println(test.deleteMin());
		
	}
	
}
