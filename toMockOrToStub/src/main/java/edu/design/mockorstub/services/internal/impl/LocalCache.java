package edu.design.mockorstub.services.internal.impl;

import java.util.HashMap;
import java.util.Map;

import edu.design.mockorstub.services.internal.Cache;
import edu.design.mockorstub.services.internal.DomainObject;

public class LocalCache implements Cache<String, DomainObject> {

	private Map<String, DomainObject> chachedValues = new HashMap<String, DomainObject>();

	@Override
	public void addObject(String cacheKey, DomainObject value) {
		chachedValues.put(cacheKey, value);

	}

	@Override
	public DomainObject getObject(String chacheKey) {
		return chachedValues.get(chacheKey);
	}
	
	@Override
	public void clear() {
		chachedValues.clear();
	}

}
