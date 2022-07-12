package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
public class BSTMap <K extends Comparable<K>, V> implements Map61B<K,V> {
		private BSTNode root;
		private int size; 
		
		public class BSTNode{
			public K key; 
			public V val; 
			
			public BSTNode leftNode; 
			public BSTNode rightNode; 
			
			public BSTNode(K key, V val){
				this.key = key;
				this.val = val;
			}		
		}
	
	
	 	public void clear() {
	 		root = null; 
	 		size = 0;
	 	}

	    /* Returns true if this map contains a mapping for the specified key. */
	    public boolean containsKey(K key) {
	    	if(key == null) {
	    		throw new IllegalArgumentException("argument to contain is null");  
	    	}
	    	return get(key)!= null;
	    }

	    
	    /* Returns the value to which the specified key is mapped, or null if this
	     * map contains no mapping for the key.
	     */
	    public V get(K key) {
	    	if(key == null) {
	    		throw new IllegalArgumentException("input key is null");
	    	}
	    	
	    	return get(root, key);
	    }

	    
	    public V get(BSTNode node, K key) {
	    	if(node == null) {
	    		return null; 
	    	}
	    	int cmp = key.compareTo(node.key);
	    	if(cmp > 0) {
	    		return get(node.rightNode, key);
	    	}else if(cmp < 0){
	    		return get(node.leftNode,key);
	    	}
	    	return node.val;
	    }
	    
	    /* Returns the number of key-value mappings in this map. */
	    public int size() {
	    	return size; 
	    }

	    /* Associates the specified value with the specified key in this map. */
	    public void put(K key, V value) {
	    	root = put(root, key, value);
	    	size += 1; 
	    }
	    
	    public BSTNode put(BSTNode node, K key, V value) {
	    	if(node == null) {
	    		return new BSTNode(key,value);
	    	}
	    	int cmp = key.compareTo(node.key);
	    	if(cmp > 0) {
	    		node.rightNode = put(node.rightNode, key, value);
	    	}else if(cmp < 0) {
	    		node.leftNode = put(node.leftNode, key, value);
	    	}else {
	    		node.val = value; 
	    	}
	    	
	    	return node;
	    }
	    
	    public void printInOrder(BSTNode node){ 
	    	if(node == null) {
	    		return; 
	    	}
	    	printInOrder(node.leftNode);
	    	System.out.println(node.key.toString() + " -> " + node.val.toString());
	    	printInOrder(node.rightNode);
	    }
	    
	    public Set<K> keySet(){
	    	HashSet<K> set = new HashSet<>();
	    	addKeys(root, set);
	    	return set; 
	    }
	    
	    public void addKeys(BSTNode node, Set<K> set) {
	    	
	    }
	   
	    
	    public Iterator<K> iterator() {
	        return keySet().iterator();
	    }

	    /* Removes the mapping for the specified key from this map if present.
	     * Not required for Lab 7. If you don't implement this, throw an
	     * UnsupportedOperationException. */
	    public V remove(K key) {
	    	if(containsKey(key)) {
	    		V targetValue = get(key);
	    		root = remove(root, key);
	    		size -= 1;
	    		return targetValue;
	    	}
	    	return null; 	
	    }
	    
	    private BSTNode remove(BSTNode node, K key) {
	    	if(node == null) {
	    		return null; 
	    	}
	    	int cmp = key.compareTo(node.key);
	    	if(cmp < 0) {
	    		node.leftNode = remove(node.leftNode, key);
	    	}else if(cmp > 0) {
	    		node.rightNode = remove(node.rightNode, key);
	    	}else {
	    		if(node.leftNode == null) {
	    			return node.rightNode;
	    		}
	    		if(node.rightNode == null) {
	    			return node.leftNode;
	    		}
	    		BSTNode originalNode = node; 
	    		node = getMinChild(node.rightNode);
	    		node.leftNode = originalNode.leftNode;
	    		node.rightNode = remove(originalNode.rightNode,node.key);
	    	}
	    	return node; 
	    }
	    
	    
	    
	    private BSTNode getMinChild(BSTNode node) { 
	    	if(node.leftNode == null) {
	    		return node; 
	    	}
	    	return getMinChild(node.leftNode);
	    }

	    /* Removes the entry for the specified key only if it is currently mapped to
	     * the specified value. Not required for Lab 7. If you don't implement this,
	     * throw an UnsupportedOperationException.*/
	    public V remove(K key, V value) {
	    	if(containsKey(key)) {
	    		V targetValue = get(key);
	    		if(targetValue == value) {
	    			root = remove(root, key);
	    			size -= 1;
	    			return targetValue; 
	    		}
	    	}
	    	return null;	
	    }

	 
}
