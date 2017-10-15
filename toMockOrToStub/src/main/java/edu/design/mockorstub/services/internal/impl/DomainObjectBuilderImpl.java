package edu.design.mockorstub.services.internal.impl;

import edu.design.mockorstub.services.internal.DomainObject;
import edu.design.mockorstub.services.internal.DomainObjectBuilder;

public class DomainObjectBuilderImpl implements DomainObjectBuilder {

	public DomainObject build(String domianObjectAsString) {
		String[] values = domianObjectAsString.split(":");
		return new DomainObjectImpl(values[0], values[1]);
	}

}
