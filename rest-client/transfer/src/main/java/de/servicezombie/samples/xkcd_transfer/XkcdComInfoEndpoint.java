package de.servicezombie.samples.xkcd_transfer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface XkcdComInfoEndpoint 
{
    
	@GET
	@Path("/info.0.json")
	@Produces("application/json")
	XkcdComicInfo info();
	
	@GET
	@Path("/{comicId}/info.0.json")
	@Produces("application/json")
	XkcdComicInfo info(@PathParam("comicId") final int id);
	
}
