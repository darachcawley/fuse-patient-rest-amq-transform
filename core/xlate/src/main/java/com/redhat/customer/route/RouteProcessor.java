package com.redhat.customer.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class RouteProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// catch all exception handler
		onException(Exception.class)
			.to("log:fail")
			.handled(true);
		
		//the xlate route
		from("activemq:queue:q.empi.deim.in")
			.log(LoggingLevel.INFO, "com.customer.route.RouteProcessor", "Route: xlateRoute, received new message on queue :: Body: ${body}")
			.unmarshal("personFormat")
			.beanRef("transformToExecuteMatch", "convertTo")
			.log(LoggingLevel.INFO, "com.customer.route.RouteProcessor", "Route: xlateRoute, unmarshalled & transformed to transformToExecuteMatch :: Body: ${body}")
			.end()
       ;
	}

}
