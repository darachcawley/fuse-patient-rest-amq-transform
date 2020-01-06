package com.redhat.customer.transform;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;

import com.sun.mdm.index.webservice.ExecuteMatchUpdate;

@Converter
public class TransformToExecuteMatch {

	@Converter
	public Object[] convertTo(Object value, Exchange exchange) throws TypeConversionException {
		
		Object objArray[] = new Object[2];
		ExecuteMatchUpdate executeMatchUpdate = (ExecuteMatchUpdate) value;

		objArray[0] = executeMatchUpdate.getCallerInfo();
		objArray[1] = executeMatchUpdate.getSysObjBean();

		if (exchange != null) {
			exchange.getOut().setBody(objArray);
		}
		return objArray;
	}

}