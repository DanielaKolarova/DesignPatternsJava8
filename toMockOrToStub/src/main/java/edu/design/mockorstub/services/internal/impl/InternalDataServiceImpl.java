package edu.design.mockorstub.services.internal.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.design.mockorstub.services.external.ExternalDataService;
import edu.design.mockorstub.services.internal.DomainObject;
import edu.design.mockorstub.services.internal.DomainObjectBuilder;
import edu.design.mockorstub.services.internal.InternalDataService;

public class InternalDataServiceImpl implements InternalDataService {

	private ExternalDataService dataService;

	private DomainObjectBuilder domainObjectBuilder;

	public InternalDataServiceImpl(ExternalDataService dataService, DomainObjectBuilder domainObjectBuilder) {
		this.dataService = dataService;
		this.domainObjectBuilder = domainObjectBuilder;
	}

	public List<DomainObject> loadDomainObjects(List<String> ids) {
		List<String> objectsAsString = dataService.loadData(ids);
		List<DomainObject> domainObjects = objectsAsString.stream().map(domainObjectBuilder::build)
				.collect(Collectors.toList());

		return domainObjects;
	}
	
//	public List<DomainObject> loadDomainObjects(List<String> ids) {
//		Set<String>  uniqueIds = new HashSet<>(ids);
//		List<String> objectsAsString = dataService.loadData(uniqueIds);
//		List<DomainObject> domainObjects = objectsAsString.stream().map(domainObjectBuilder::build)
//				.collect(Collectors.toList());
//
//		return domainObjects;
//	}


}