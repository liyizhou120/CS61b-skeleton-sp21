package deque;
import java.util.*;

public class ArrayDeque<T> implements Deque<T>{

	private int firstIndex;
	private int lastIndex;
	private T[] items;
	private int size;
	
	private static final int START_SIZE = 8;

	public ArrayDeque() {
		items = (T[]) new Object[START_SIZE];
		firstIndex = 0;
		lastIndex = 0;
		size = 0;
	}
	
	public void addFirst(T item) {
		if(size == 0) {
			firstIndex = 0; 
			lastIndex = 0; 
			items[0] = item; 
			size++; 
		}
		if(size == items.length) {
			resizeUp();
		}
		
		if(firstIndex == 0) {
			firstIndex = items.length - 1; 
		}else {
			firstIndex--; 	
		}
		items[firstIndex] = item;
		size++; 
	}
	
	
	public void addLast(T item) {
		if(size == 0) {
			firstIndex = 0; 
			lastIndex = 0; 
			items[0] = item; 
			size++;
		}
		if(size == items.length) {
			resizeUp();
		}
		if(lastIndex == items.length - 1) {
			lastIndex = 0; 
		}else {
			lastIndex++;
		}
		items[lastIndex] = item; 
		size++;
	}
	
	public boolean isEmpty() {
		return size == 0; 
	}
	
	public T removeFirst() {
		if(size == 0) {
			return null; 
		}
		T removedNode = items[firstIndex];
		items[firstIndex]= null;
		
		if(firstIndex == items.length - 1) {
			firstIndex = 0;
		}else {
			firstIndex++;
		}
		size--;
		
		if(size == 0) {
			firstIndex = 0;
			lastIndex = 0; 
		}
		
		if(size < items.length/4) {
			resizeDown();
		}
		
		return removedNode;
	}
	
	public T removeLast() {
		if(size == 0) {
			return null; 
		}
		T removedNode = items[lastIndex];
		items[lastIndex] = null; 
		if(lastIndex == 0) {
			lastIndex = items.length - 1;
		}else {
			lastIndex--;
		}
		
		size--; 
		
		if(size == 0) {
			firstIndex = 0;
			lastIndex = 0;
		}
		
		if(size < items.length / 4) {
			resizeDown();
		}
		
		return removedNode;
		
	}
	
	public T get(int pos) {
		if(pos >= size || pos < 0) {
			return null;
		}
		return items[pos];
		//return items[(firstIndex + pos) % items.length];
	}
	
	
	public int size() {
		if(size <= 0) {
			return 0;
		}
		return size; 
	}
	
	public void printDeque() {
		for(int i = 0; i < items.length - 1; i++) {
			System.out.println(items[i] + " ");
		}
	}
	
	
	private void resizeUp() {
		T[] resizedArray = (T[]) new Object[items.length * 2];
		int sizeOfFirstCopy = items.length - firstIndex; 
		System.arraycopy(items, firstIndex, resizedArray, 0, sizeOfFirstCopy);
		System.arraycopy(items, 0, resizedArray, sizeOfFirstCopy, size - sizeOfFirstCopy);
		items = resizedArray;
		
		firstIndex = 0;
		lastIndex = size - 1; 
	}
	
	private void resizeDown() {
		T[] resizedArray = (T[]) new Object[items.length / 2];
		
		if(lastIndex < firstIndex) {
			int sizeOfFirstCopy = items.length - firstIndex; 
			System.arraycopy(items, firstIndex, resizedArray, 0, sizeOfFirstCopy);
			System.arraycopy(items, 0, resizedArray, sizeOfFirstCopy, size - sizeOfFirstCopy);
		}else {
			System.arraycopy(items, firstIndex, resizedArray, 0, size);
		}
		items = resizedArray;
		firstIndex = 0; 
		lastIndex = size - 1; 
		
	}
	
}
