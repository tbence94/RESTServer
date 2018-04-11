package hu.pemik.dcs.restserver.endpoints;

import hu.pemik.dcs.restserver.database.Database;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("warehouse")
public class WarehouseEndpoints {

    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@Context SecurityContext sc) {
        return Response.ok(Database.query().warehouse).build();
    }

}