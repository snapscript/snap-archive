package io.orthrus.rest.status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = StatusResource.RESOURCE_PATH)
@Api(value = StatusResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class StatusResource {
   
   public static final String RESOURCE_PATH = "/v1/status";
   public static final String RESOURCE_NAME = "status";
   
   @GET
   @ApiOperation(value = "Check status")
   public Response ping() {
      return Response.noContent().build();
   }
   
}
