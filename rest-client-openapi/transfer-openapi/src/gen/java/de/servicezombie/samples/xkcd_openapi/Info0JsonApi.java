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

@Path("/info.0.json")
@Api(description = "the info.0.json API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-11-11T22:13:25.535093800+01:00[Europe/Berlin]")
public interface Info0JsonApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Return last review.", notes = "", tags={ "XkcdComicInfo" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A JSON array of user names", response = XkcdComicInfo.class) })
    XkcdComicInfo getLastReview();
}
