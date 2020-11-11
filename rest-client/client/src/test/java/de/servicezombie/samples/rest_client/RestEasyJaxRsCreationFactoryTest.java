package de.servicezombie.samples.rest_client;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;
import de.servicezombie.samples.xkcd_transfer.XkcdComicInfo;

public class RestEasyJaxRsCreationFactoryTest {

	private RestEasyJaxRsCreationFactory testee;
	private SimpleClientConfiguration<XkcdComInfoEndpoint> configuration;
	
	@Before
	public void configure() throws FileNotFoundException, IOException {
				
		configuration = new SimpleClientConfiguration<>(XkcdComInfoEndpoint.class);
		configuration.putData("endpoint", "http://xkcd.com");
		
		// this way it is called by clients in the application, no mandatory ctor parameters are 
		// allowed
		testee = new RestEasyJaxRsCreationFactory();
		
	}
	
	@Test
	public void testCreateInstance() {		
		final XkcdComInfoEndpoint client = testee.createJaxRsClient(configuration);
		assertNotNull(client);		
	}
	

	/**
	 * manual to not put traffic on remote service
	 */
	@Test
	public void testInvoke() {
		
		final XkcdComInfoEndpoint client = testee.createJaxRsClient(configuration);
		XkcdComicInfo info = client.info();
		assertNotNull(info.getSafe_title());
	}
	
}
