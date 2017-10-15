package edu.design.mockorstub.services.internal;

public interface Cache<K, V> {

	void addObject(K chacheKey, V value);
	
	V getObject(K chacheKey);
	
	void clear();
}
