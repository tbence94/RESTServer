package hu.pemik.dcs.restserver.endpoints;

import hu.pemik.dcs.restserver.database.Database;

import javax.ws.rs.*;
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

    @PUT
    @Path("customer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response store(@Context SecurityContext sc, @PathParam("id") String id) {
        Database db = Database.query();

//        User user = db.users.find(Integer.valueOf(id));

        return Response.ok().build();
    }

}