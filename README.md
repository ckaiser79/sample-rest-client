This is an example of how rest endpoint definitions can be exchanged 
between a provider and consumer without need to have 3rd party libs on both sides.

* The `transfer` modules contains the models and endpoints.
* The client uses transfer to call a webservice.

Class `de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint` is JAX-RS Annotated and uses the proxy framework from the resteasy framework, where `de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpointDefiniton` is a plain interface, which can be used to create a preconfigured WebTarget.

Class `de.servicezombie.samples.rest_client.RestClientFascade` can be used by developers. It configured itself by System Properties and a Property file - similar to Log4j.

Most documentation is written in German.

# Content

rest-client
: The proof of concept.

rest-client-openapi
: Playing with openapi codegenerator

