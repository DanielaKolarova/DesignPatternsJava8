package edu.design.mockorstub.services.internal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.design.mockorstub.services.external.ExternalDataService;
import edu.design.mockorstub.services.internal.impl.DomainObjectBuilderImpl;
import edu.design.mockorstub.services.internal.impl.DomainObjectImpl;
import edu.design.mockorstub.services.internal.impl.InternalDataServiceImpl;

public class InternalDataServiceStubTest {

	private InternalDataService internalDataService;
	
	@Mock
	private ExternalDataService mockDataService;

	private DomainObjectBuilder domainObjectBuilder = new DomainObjectBuilderImpl();

	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		internalDataService = new InternalDataServiceImpl(mockDataService, domainObjectBuilder);
	}
	
	@Test
	public void testLoadData() {
		List<String> ids = Arrays.asList("42", "1984");
		
		//expectations
		when(mockDataService.loadData(any())).thenReturn(Arrays.asList("42:Douglas Adams", "1984:George Orwell"));
		
		
		//Exercise
		List<DomainObject> loadedObjects = internalDataService.loadDomainObjects(ids);
		
		//Verify expectations
		//verify(mockDataService, times(1)).loadData(any());
		
		//Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"), new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
	}
}
