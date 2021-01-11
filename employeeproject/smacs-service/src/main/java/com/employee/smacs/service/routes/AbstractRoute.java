package com.employee.smacs.service.routes;

import java.nio.file.AccessDeniedException;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jetty9.JettyHttpComponent9;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.employee.smacs.service.exception.ResourceNotFoundException;

@Component
public class AbstractRoute extends RouteBuilder {

	@Value("${SMACS_SERVICE_PORT:9090}")
	private Integer port;

	@Value("${SMACS_SERVICE_HOST:0.0.0.0}")
	private String hostname;

	@Value("${SMACS_SERVICE_KEYSTOREFILEPATH:../var/smacs-service-keystore.jks}")
	private String keystoreLocation;

	@Value("${SMACS_SERVICE_KEYSTOREPASSWORD:Newuser123}")
	private String keystorePassword;

	@Value("${SMACS_SERVICE_ENABLESSL:false}")
	private String enableSSL;

	/**
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		
		// log.debug("configure() : Started.");

		String scheme = "http";
		if (enableSSL != null && "true".equals(enableSSL)) {
			JettyHttpComponent9 jettyComponent = getContext().getComponent("jetty", JettyHttpComponent9.class);
			if (jettyComponent != null) {
				jettyComponent.setSslContextParameters(sslComponent(keystoreLocation, keystorePassword));
			}
			scheme = "https";
		}

		restConfiguration().component("jetty").host(hostname).port(port).bindingMode(RestBindingMode.json)
				.contextPath("/smacs/service/").scheme(scheme).enableCORS(true).dataFormatProperty("prettyPrint", "true")
				.dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES");

		// Authentication failure
		onException(AccessDeniedException.class).logStackTrace(true).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401)).setBody(exceptionMessage()).end();

		onException(BadCredentialsException.class).logStackTrace(true).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401)).setBody(exceptionMessage()).end();

		// Resource Not Found Exception
		onException(ResourceNotFoundException.class).logStackTrace(true).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404)).end();

		// Condition to send Java Stacktrace in response
		Predicate ifStackTraceEnabled = PredicateBuilder
				.toPredicate(simple("${properties:logging.trace.exception.enabled} == 'true'"));

		// Unhandled exception
		onException(RuntimeException.class).logStackTrace(true).handled(true)
				.log("${body}")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
				.setHeader(Exchange.CONTENT_TYPE, constant("text/plain")).choice().when(ifStackTraceEnabled)
				.setBody(simple("${exception.stacktrace}")).otherwise().setBody(exceptionMessage()).end();
		
		// log.debug("configure() : End.");
	}

	/**
	 * Create SSL Parameters
	 * 
	 * @param keystorePassword
	 * @param keystoreLocation
	 * @return
	 */
	private SSLContextParameters sslComponent(String keystoreLocation, String keystorePassword) {

		if (keystoreLocation == null || keystorePassword == null) {
			return null;
		}
		KeyStoreParameters ksp = new KeyStoreParameters();
		ksp.setResource(keystoreLocation);
		ksp.setPassword(keystorePassword);

		KeyManagersParameters kmp = new KeyManagersParameters();
		kmp.setKeyStore(ksp);
		kmp.setKeyPassword(keystorePassword);

		SSLContextParameters scp = new SSLContextParameters();
		scp.setKeyManagers(kmp);
		return scp;
	}
}
