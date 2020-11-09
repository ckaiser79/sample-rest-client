package de.servicezombie.samples.xkcd_openapi;

import de.servicezombie.samples.xkcd_openapi.XkcdComicInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/")
@Api(description = "the  API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-11-09T08:28:15.031970700+01:00[Europe/Berlin]")
public interface DefaultApi {

    @GET
    @Path("/{comicId}/info.0.json")
    @Produces({ "application/json" })
    @ApiOperation(value = "Return review by id.", notes = "", tags={ "XkcdComicInfo" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A JSON array of user names", response = XkcdComicInfo.class),
        @ApiResponse(code = 404, message = "Review was not found", response = Void.class) })
    XkcdComicInfo getReviewById(@PathParam("comicId") @Min(1L) @ApiParam("&lt;strong&gt;ID&lt;/strong&gt; of comicReview") Long comicId);
}
