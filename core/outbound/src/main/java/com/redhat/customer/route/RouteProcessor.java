package com.redhat.customer.route;

import javax.ws.rs.HttpMethod;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class RouteProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// catch all exception handler
		onException(Exception.class)
			.log("ERROR ${body}")
			.handled(true)
		;
		
		//the outbound route
		from("activemq:queue:q.empi.nextgate.out")
			.log(LoggingLevel.INFO, "com.customer.route.RouteProcessor", "Route: outboundRoute, received new message on queue :: Body: ${body}")
			.unmarshal("nextgateFormat")
			.to("direct:callWSEndpoint")
			.end()
		;
		   
		// SOAP call using CXF component
		from("direct:callWSEndpoint")
			.log(LoggingLevel.INFO, "com.customer.route.RouteProcessor", "transforming...: ${body}")
			.beanRef("transformToExecuteMatch", "convertTo")
			.log(LoggingLevel.INFO, "com.customer.route.RouteProcessor", "sending to soap as...: ${body}")
			// use the cxf component (JAX-RS wrapper) to marshal the Account object into a soap message,
			// ensure it conforms to a wsdl, wrap it into a soap message and send the WS call.
			// this is using a bean described in the /src/main/resources/spring/camel-context.xml
			.to("cxf:bean:nextgateService?defaultOperationName=executeMatchUpdate")
			.log("Response : ${body}");
		;
	}

}
