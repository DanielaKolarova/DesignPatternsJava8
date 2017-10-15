package edu.design.mockorstub.services.external;

import java.util.Collection;
import java.util.List;

public interface ExternalDataService {
	
	List<String> loadData(Collection<String> ids);

}
