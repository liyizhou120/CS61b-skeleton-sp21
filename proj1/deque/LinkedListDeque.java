package deque;

public class LinkedListDeque <T> implements Deque<T> {
	private TNode firstNode; 
	private int size; 
	
	public class TNode{
		public T item; 
		public TNode next; 
		public TNode prev; 
		
		public TNode(T i, TNode n, TNode p){
			item = i; 
			next = n;
			prev = p; 
		}
	}
	
	
	public LinkedListDeque() {
		firstNode = new TNode(null, null,null);
		firstNode.next = firstNode; 
		firstNode.prev = firstNode; 
		size = 0; 
	}
	
	public boolean isEmpty() {
		return size == 0; 
	}

	
	public void addFirst(T item) {
		// Circular loop linked list 
		firstNode.next = new TNode(item, firstNode.next, firstNode);
		firstNode.next.next.prev = firstNode.next; 
		size++; 
	}
	
	public void addLast(T item) {
		firstNode.prev = new TNode(item, firstNode, firstNode.prev);
		firstNode.prev.prev.next = firstNode.prev;
		size++;
	}

	
	public T removeFirst() {
		if(firstNode == firstNode.next) {
			return null;
		}
		TNode temp = firstNode.next;
		firstNode.next.next.prev = firstNode;
		firstNode.next = firstNode.next.next;
		size--;
		
		return temp.item;
	}
	
	public T removeLast() {
		if(firstNode == firstNode.prev) {
			return null;
		}
		
		TNode temp = firstNode.prev;
		firstNode.prev.prev.next = firstNode;
		firstNode.prev = firstNode.prev.prev;
		size--;
		
		return temp.item;
	}
	
	
	public T get(int pos) {
		if(pos >= size || pos < 0) {
			return null; 
		} else {
			TNode currNode = firstNode.next; 
			while(pos >= 0) {
				currNode = currNode.next;
				pos--;
			}
			return currNode.item;
		}
		
	}
	
	public int size() {
		return size; 
	}
	
	public void printDeque() {
		TNode printNode = firstNode.next;
		for(int i = 0; i < size - 1; i++) {
			System.out.println(printNode.item + " ");
			printNode = printNode.next;
		}
		System.out.println(printNode.item);
	}
	
}
