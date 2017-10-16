package edu.design.mockorstub.services.external;

import java.util.Collection;
import java.util.List;

/**
 * Loads data from a remote data repository.
 */
public interface ExternalDataService {

	/**
	 * Loads data from a remote repository based on a list of identifiers and
	 * returns the data serialized into text values.
	 * 
	 * @param ids
	 *            A list of data identifiers
	 * 
	 * @return List of objects represented as textual values.
	 */
	List<String> loadData(Collection<String> ids);

}
