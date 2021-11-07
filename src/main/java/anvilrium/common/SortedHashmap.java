package anvilrium.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class SortedHashmap<K, V> extends HashMap<K, V> implements SortedMap<K, V>{

	private static final long serialVersionUID = 8235664929626586361L;
	
	private Comparator<? super K> comparator = null;


    public SortedHashmap() {
    	super();
    }
    
    public SortedHashmap(Comparator<? super K> comparator) {
    	super();
    	this.comparator = comparator;
    }
	
	public SortedHashmap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
    }
	
	public SortedHashmap(int initialCapacity, float loadFactor, Comparator<? super K> comparator) {
		super(initialCapacity, loadFactor);
    }
	
    public SortedHashmap(int initialCapacity) {
    	super(initialCapacity);
    }
	
    public SortedHashmap(int initialCapacity, Comparator<? super K> comparator) {
    	super(initialCapacity);
    }
    
    public SortedHashmap(Map<? extends K, ? extends V> m) {
    	super(m);
    }

	@Override
	public Comparator<? super K> comparator() {
		return comparator;
	}

	@Override
	public SortedMap<K, V> subMap(K fromKey, K toKey) {
		return null;
	}

	@Override
	public SortedMap<K, V> headMap(K toKey) {
		return null;
	}

	@Override
	public SortedMap<K, V> tailMap(K fromKey) {
		return null;
	}

	@Override
	public K firstKey() {
		return null;
	}

	@Override
	public K lastKey() {
		return null;
	}

}
