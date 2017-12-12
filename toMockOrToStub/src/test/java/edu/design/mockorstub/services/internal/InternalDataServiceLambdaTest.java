package edu.design.mockorstub.services.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.design.mockorstub.services.internal.impl.DomainObjectBuilderImpl;
import edu.design.mockorstub.services.internal.impl.DomainObjectImpl;
import edu.design.mockorstub.services.internal.impl.InternalDataServiceImpl;

public class InternalDataServiceLambdaTest {

	private InternalDataService internalDataService;
	
	private DomainObjectBuilder domainObjectBuilder = new DomainObjectBuilderImpl();

	@Before
	public void setUp() {
		//internalDataService = new InternalDataServiceImpl(ids -> Arrays.asList("42:Douglas Adams", "1984:George Orwell"), domainObjectBuilder);
		internalDataService = new InternalDataServiceImpl(this::callStubExternalService, domainObjectBuilder);
	}
	
	@Test
	public void testLoadData() {
		List<String> ids = Arrays.asList("42", "1984");
		
		//Exercise
		List<DomainObject> loadedObjects = internalDataService.loadDomainObjects(ids);
		
		//Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"), new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
	}
	
	private List<String> callStubExternalService(Collection<String> ids) {
		return Arrays.asList("42:Douglas Adams", "1984:George Orwell");
		
	}
}
