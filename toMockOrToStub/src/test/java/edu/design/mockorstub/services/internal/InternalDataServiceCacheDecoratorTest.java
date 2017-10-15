package edu.design.mockorstub.services.internal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.design.mockorstub.services.internal.impl.DomainObjectImpl;
import edu.design.mockorstub.services.internal.impl.InternalDataServiceCacheDecorator;
import edu.design.mockorstub.services.internal.impl.LocalCache;

public class InternalDataServiceCacheDecoratorTest {

	private InternalDataServiceCacheDecorator internalDataServiceCacheDecorator;

	@Mock
	private InternalDataService mockDataService;

	private Cache<String, DomainObject> localCache = new LocalCache();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		internalDataServiceCacheDecorator = new InternalDataServiceCacheDecorator(mockDataService, localCache);
	}

	@Test
	public void testLoadData() {
		List<String> ids = Arrays.asList("42", "1984");

		// expectations
		when(mockDataService.loadDomainObjects(any())).thenReturn(Arrays
				.asList(new DomainObjectImpl("42", "Douglas Adams"), new DomainObjectImpl("1984", "George Orwell")));

		// Exercise
		List<DomainObject> loadedObjects = internalDataServiceCacheDecorator.loadDomainObjects(ids);

		// Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"),
				new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
		Assert.assertNotNull(localCache.getObject("42"));
		Assert.assertNotNull(localCache.getObject("1984"));
	}

	@Test
	public void testLoadDataWithOneElementCached() {
		List<String> ids = Arrays.asList("42", "1984");

		//Data setup
		localCache.addObject("42", new DomainObjectImpl("42", "Douglas Adams"));
		
		// Expectations
		when(mockDataService.loadDomainObjects(Arrays.asList("1984")))
				.thenReturn(Arrays.asList(new DomainObjectImpl("1984", "George Orwell")));
		
		// Exercise
		List<DomainObject> loadedObjects = internalDataServiceCacheDecorator.loadDomainObjects(ids);

		// Verify expectations
		verify(mockDataService, times(1)).loadDomainObjects(Arrays.asList("1984"));

		// Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"),
				new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
		Assert.assertNotNull(localCache.getObject("42"));
		Assert.assertNotNull(localCache.getObject("1984"));
	}
	
	@Test
	public void testLoadDataWithAllElementCached() {
		List<String> ids = Arrays.asList("42", "1984");

		//Data setup
		localCache.addObject("1984", new DomainObjectImpl("1984", "George Orwell"));
		localCache.addObject("42", new DomainObjectImpl("42", "Douglas Adams"));
			
		// Expectations
		when(mockDataService.loadDomainObjects(Arrays.asList("1984")))
				.thenReturn(null);
		
		// Exercise
		List<DomainObject> loadedObjects = internalDataServiceCacheDecorator.loadDomainObjects(ids);

		// Verify expectations
		verify(mockDataService, times(0)).loadDomainObjects(Arrays.asList("1984"));

		// Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"),
				new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertTrue(expectedResult.containsAll(loadedObjects));
		Assert.assertNotNull(localCache.getObject("42"));
		Assert.assertNotNull(localCache.getObject("1984"));
	}

	
	@After
	public void tearDown() {
		localCache.clear();
	}
}
