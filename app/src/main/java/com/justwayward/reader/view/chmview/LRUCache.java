package com.justwayward.reader.view.chmview;

import java.util.Map;
import java.util.TreeMap;

public class LRUCache<K extends Comparable<K>, V> {
	
	Map<K, Item> cacheMap = new TreeMap<K, Item>();
	
	private int capacity;
	
	public LRUCache(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("capacity must be positive integer");
		this.capacity = capacity;
	}
	
	public synchronized V get(K key) {
		Item item = cacheMap.get(key);
		if (item == null)
			return null;
		
		for(Item i: cacheMap.values()) {
			i.hits --;
		}
		
		item.hits += 2;
		return item.value;
	}
	
	public synchronized V prune() {
		if (cacheMap.size() >= capacity) {
			Item kick = null;
			for (Item item: cacheMap.values()) {
				if (kick == null || kick.hits > item.hits) {
					kick = item;
				}
			}
			cacheMap.remove(kick.key);
			return kick.value;
		}
		return null;
	}
	
	public synchronized void put(K key, V val) {
		if (cacheMap.containsKey(key)) { // just refresh the value
			cacheMap.put(key, new Item(key, val));
			return;
		}
		prune();
		cacheMap.put(key, new Item(key, val));
	}

	public synchronized void clear() {
		cacheMap.clear();
	}
	
	public int size() {
		return cacheMap.size();
	}
	
	public String toString() {
		return "LRUCache " + size() + "/" + capacity + ": " + cacheMap.toString();
	}
	
	class Item {
		K key;
		V value;
		int hits;
		
		public Item(K key, V value) {
			this.key = key;
			this.value = value;
			this.hits = 1;
		}
		
		public String toString() {
			return "(" + hits + ")";
		}
	}
}
