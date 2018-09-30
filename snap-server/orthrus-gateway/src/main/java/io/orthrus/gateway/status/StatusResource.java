package io.orthrus.gateway.status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = StatusResource.RESOURCE_PATH)
@Api(value = StatusResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class StatusResource {
   
   public static final String RESOURCE_PATH = "/v1/status";
   public static final String RESOURCE_NAME = "status";
   
   private final StatusService service;
   
   @Inject
   public StatusResource(StatusService service) {
      this.service = service;
   }
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Full status details")
   public List<StatusResult> status() {
      return service.status();
   }
   
   @GET
   @Path("/full")
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Full status details")
   public List<StatusResult> fullStatus() {
      return service.statusFull();
   }

}
