package edu.design.mockorstub.services.internal;

import java.util.List;

public interface InternalDataService {
	
	List<DomainObject> loadDomainObjects(List<String> ids);

}
