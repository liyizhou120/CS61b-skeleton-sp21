package hashmap;

import java.util.*;


/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
    
    private static final int INITIAL_SIZE = 16; 
    private static final double LOAD_FACTOR = 0.75;
    private final double maxLoadFactor; 
    private int size = 0; 
    
    /* Instance Variables */
    private Collection<Node>[] buckets;
    

    /** Constructors */
    public MyHashMap() {
    	this(INITIAL_SIZE,LOAD_FACTOR);
    	
    }

    public MyHashMap(int initialSize) { 
    	this(initialSize, LOAD_FACTOR);
    	
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) { 
    		buckets = createTable(initialSize);
    		maxLoadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
    	
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
    		
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    @SuppressWarnings("unchecked")
	private Collection<Node>[] createTable(int tableSize) {
    	Collection<Node>[] hashTable = new Collection[tableSize];
    	
    	for(int i = 0; i < tableSize; i++) {
    		hashTable[i] = createBucket();
    	}
    	
        return hashTable;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    
    
    /** Removes all of the mappings from this map. */
    public void clear() {;
    	buckets = createTable(INITIAL_SIZE);
    	size = 0; 
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {;
    	return getNode(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {;
    	Node node = getNode(key);
    	if(node == null) {
    		return null;
    	}
    	return node.value;
    }
    
    
    private Node getNode(K key) {
    	int bucketIndex = getIndex(key);
    	return getNode(bucketIndex, key);	
    }
    
    private Node getNode(int bucketIndex, K key) {
    	for(Node node : buckets[bucketIndex]) {
    		if(node.key.equals(key)) {
    			return node;
    		}
    	}
    	return null; 
    }
    
    private int getIndex(K key) {
    	return getIndex (key, buckets);
    }
    
    private int getIndex(K key, Collection<Node>[] table) {
    	int keyHashTable = key.hashCode();
    	
    	//solve the problem of overflowing negative result 
    	//return N%M value 
    	return Math.floorMod(keyHashTable, table.length);
    }
    
  
    /** Returns the number of key-value mappings in this map. */
    public int size() {;
    	return size; 
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {;
    	int bucketIndex = getIndex(key);
    	Node node = getNode(bucketIndex, key);
    	if(node != null) {
    		node.value = value; 
    		return; 
    	}
    	
    	node = createNode(key, value);
    	buckets[bucketIndex].add(node);
    	size += 1; 
    	if(hasReachedMaxLoad()) {
    		resize(buckets.length * 2);
    	}
    	
    }
    
    public boolean hasReachedMaxLoad() {
    	double curLoadFactor = size / buckets.length;
    	if(curLoadFactor <= LOAD_FACTOR) {
    		return true; 
    	}
    	return false; 
    	
    }
    
    public void resize(int capacity){
    	Collection<Node>[] newBucket = createTable(capacity);
    	Iterator<Node> nodeIterator = new HashMapNodeIterator();
    	while(nodeIterator.hasNext()) {
    		Node node = nodeIterator.next();
    		int bucketIndex = getIndex(node.key,newBucket);
    		newBucket[bucketIndex].add(node);
    	}
    	buckets = newBucket;
    	
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){;
    	HashSet<K> set = new HashSet<>(); 
    	for(K key : this) {
    		set.add(key);
    	}
    	return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    
    public Iterator<K> iterator(){
    	return new HashMapIterator();
    }
    
    
    public V remove(K key) {;
    	int bucketIndex = getIndex(key);
    	Node node = getNode(bucketIndex,key);
    	if(node == null) {
    		return null; 
    	}
    	size -= 1;
    	buckets[bucketIndex].remove(node);
    	return node.value;
    
    }
    

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {;
	   	int bucketIndex = getIndex(key);
		Node node = getNode(bucketIndex,key);
		if(node == null || !node.value.equals(value)) {
			return null; 
		}
		size -= 1;
		buckets[bucketIndex].remove(node);
		return node.value;
    }
    
    
    private class HashMapIterator implements Iterator<K> {
    	private Iterator<Node> iterator = new HashMapNodeIterator();
    	
    	public boolean hasNext() {
    		return iterator.hasNext();
    	}
    	
    	public K next() {
    		return iterator.next().key;
    	}
    	
    }
    
    private class HashMapNodeIterator implements Iterator<Node>{
    	//
    	private final Iterator<Collection<Node>> bucketIterator = Arrays.stream(buckets).iterator();
    	private Iterator<Node> currBucketIterator; 
    	private int nodesLeft = size; 
    	
    	public boolean hasNext() {
    		return nodesLeft > 0; 
    	}
    	
    	public Node next() {
    		if(currBucketIterator == null || !currBucketIterator.hasNext()) {
    			Collection<Node> currentBucket = bucketIterator.next();
    			while(currentBucket.size() == 0) {
    				currentBucket = bucketIterator.next();
    			}
    			currBucketIterator = currentBucket.iterator();
    		}
    		nodesLeft -= 1 ; 
    		
    		return currBucketIterator.next();
    	}
    }

}
