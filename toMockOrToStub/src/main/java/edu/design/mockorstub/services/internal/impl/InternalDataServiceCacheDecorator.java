package edu.design.mockorstub.services.internal.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.design.mockorstub.services.internal.Cache;
import edu.design.mockorstub.services.internal.DomainObject;
import edu.design.mockorstub.services.internal.InternalDataService;

public class InternalDataServiceCacheDecorator implements InternalDataService {

	private InternalDataService internalDataService;

	private Cache<String, DomainObject> localCache;

	public InternalDataServiceCacheDecorator(InternalDataService internalDataService,
			Cache<String, DomainObject> localCache) {
		this.internalDataService = internalDataService;
		this.localCache = localCache;
	}

	/**
	 * <ol>
	 * <li>Get the cached elements</li>
	 * <li>Filter ids, remove the ids of all cached elements</li>
	 * <li>Find uncached elements</li>
	 * <li>Merge cached and loaded elements</li>
	 * </ol>
	 */
	public List<DomainObject> loadDomainObjects(List<String> ids) {
		Map<String, DomainObject> cachedDomainObjects = ids.stream().filter(id -> localCache.getObject(id) != null)
				.collect(Collectors.toMap(id -> id, id -> localCache.getObject(id)));

		List<String> idsToRequest = ids.stream().filter(id -> !cachedDomainObjects.keySet().contains(id))
				.collect(Collectors.toList());
		
		List<DomainObject> requestedDomainObjects = internalDataService.loadDomainObjects(idsToRequest);
		storeIntoCache(requestedDomainObjects);

		return Stream.concat(cachedDomainObjects.values().stream(), requestedDomainObjects.stream())
				.collect(Collectors.toList());
	}
	
	private void storeIntoCache(List<DomainObject> requestedDomainObjects) {
		for (DomainObject domainObject : requestedDomainObjects) {
			localCache.addObject(domainObject.getId(), domainObject);
		}
	}

}