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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.design.mockorstub.services.internal.impl.DomainObjectImpl;
import edu.design.mockorstub.services.internal.impl.InternalDataServiceCacheDecorator;

public class InternalDataServiceCacheDecoratorMockTest {

	private InternalDataServiceCacheDecorator internalDataServiceCacheDecorator;

	@Mock
	private InternalDataService mockDataService;

	@Mock
	private Cache<String, DomainObject> mockLocalCache;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		internalDataServiceCacheDecorator = new InternalDataServiceCacheDecorator(mockDataService, mockLocalCache);
	}

	@Test
	public void testLoadData() {
		List<String> ids = Arrays.asList("42", "1984");

		// expectations
		when(mockDataService.loadDomainObjects(any())).thenReturn(Arrays
				.asList(new DomainObjectImpl("42", "Douglas Adams"), new DomainObjectImpl("1984", "George Orwell")));

		// Exercise
		List<DomainObject> loadedObjects = internalDataServiceCacheDecorator.loadDomainObjects(ids);

		// Verify expectations
		verify(mockDataService, times(1)).loadDomainObjects(any());
		verify(mockLocalCache, times(1)).addObject("42", new DomainObjectImpl("42", "Douglas Adams"));
		verify(mockLocalCache, times(1)).addObject("1984", new DomainObjectImpl("1984", "George Orwell"));

		// Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"),
				new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
	}

	@Test
	public void testLoadDataWithOneElementCached() {
		List<String> ids = Arrays.asList("42", "1984");

		// expectations
		when(mockDataService.loadDomainObjects(Arrays.asList("1984")))
				.thenReturn(Arrays.asList(new DomainObjectImpl("1984", "George Orwell")));
		when(mockLocalCache.getObject("42")).thenReturn(new DomainObjectImpl("42", "Douglas Adams"));
		when(mockLocalCache.getObject("1984")).thenReturn(null);

		// Exercise
		List<DomainObject> loadedObjects = internalDataServiceCacheDecorator.loadDomainObjects(ids);

		// Verify expectations
		verify(mockDataService, times(1)).loadDomainObjects(Arrays.asList("1984"));
		verify(mockLocalCache, times(1)).addObject("1984", new DomainObjectImpl("1984", "George Orwell"));

		// Verify state
		List<DomainObject> expectedResult = Arrays.asList(new DomainObjectImpl("42", "Douglas Adams"),
				new DomainObjectImpl("1984", "George Orwell"));
		Assert.assertEquals(expectedResult, loadedObjects);
	}
}
