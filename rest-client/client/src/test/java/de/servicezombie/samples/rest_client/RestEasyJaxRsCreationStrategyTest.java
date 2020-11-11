package de.servicezombie.samples.rest_client;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;
import de.servicezombie.samples.xkcd_transfer.XkcdComicInfo;

public class RestEasyJaxRsCreationStrategyTest {

	private RestEasyJaxRsCreationStrategy testee;
	private Properties configuration;
	
	@Before
	public void configure() throws FileNotFoundException, IOException {
				
		configuration = new Properties();
		configuration.load(new FileReader(new File("src/main/resources/endpoints.properties")));
		
		// this way it is called by clients in the application, no mandatory ctor parameters are 
		// allowed
		testee = new RestEasyJaxRsCreationStrategy();
		
	}
	
	@Test
	public void testCreateInstance() {		
		final XkcdComInfoEndpoint client = testee.createClient(XkcdComInfoEndpoint.class);
		assertNotNull(client);		
	}
	

	/**
	 * manual to not put traffic on remote service
	 */
	@Test
	public void testInvoke() {
		
		final XkcdComInfoEndpoint client = testee.createClient(XkcdComInfoEndpoint.class);
		XkcdComicInfo info = client.info();
		assertNotNull(info.getSafe_title());
	}
	
}
