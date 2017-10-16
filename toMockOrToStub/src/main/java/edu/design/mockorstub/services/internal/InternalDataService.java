package edu.design.mockorstub.services.internal;

import java.util.List;

/**
 * Loads data stored into application's domain objects.
 */
public interface InternalDataService {

	/**
	 * Loads a list of domain objects based an a list of identifiers passed.
	 * 
	 * @param ids
	 *            List of object identifiers to be serached for
	 * @return List of domain objects corresponding to the identifiers passed for
	 *         search
	 */
	List<DomainObject> loadDomainObjects(List<String> ids);

}
