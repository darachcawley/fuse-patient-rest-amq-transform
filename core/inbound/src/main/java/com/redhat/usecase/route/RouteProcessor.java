package com.redhat.usecase.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class RouteProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// catch all exception handler
		onException(Exception.class)
	        .to("log:fail")
			.handled(true);
		
		//the inbound route
		from("direct:integrateRoute")
	       	.log(LoggingLevel.INFO, "com.usecase.route.RouteProcessor", "Route: direct:integrateRoute :: Body: ${body}")
			.setExchangePattern(ExchangePattern.InOnly)
			.marshal("personFormat")
	       	.to("activemq:queue:q.empi.deim.in")
	       	.log(LoggingLevel.INFO, "com.usecase.route.RouteProcessor", "Route: direct:integrateRoute :: Marshalled XML & added to Queue: ${body}")
	       	.transform(constant(2))
	       	.end()
       ;
	}

}
