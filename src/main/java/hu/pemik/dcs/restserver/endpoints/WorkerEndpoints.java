package hu.pemik.dcs.restserver.endpoints;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.pemik.dcs.restserver.Console;
import hu.pemik.dcs.restserver.database.Database;
import hu.pemik.dcs.restserver.models.Log;
import hu.pemik.dcs.restserver.models.User;
import hu.pemik.dcs.restserver.models.Worker;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("workers")
public class WorkerEndpoints {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@Context SecurityContext sc) {
        return Response.ok(Database.query().users.where("role", User.ROLE_WORKER)).build();
    }

    @POST
    @Path("worker")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response store(@Context SecurityContext sc, WorkerCreation w) {
        Database db = Database.query();
        Worker worker = new Worker(w.name, w.email);

        try {
            db.users.insert(worker);
        } catch (Exception e) {
            Console.handleException(e);
            Log.create(sc, "Failed to create worker: " + worker.toString());
            return Response.notModified().build();
        }

        Log.create(sc, "Worker created: " + worker.name);
        db.save();

        return Response.ok().build();
    }

}

class WorkerCreation {

    String email;

    String name;

    @JsonCreator
    public WorkerCreation(@JsonProperty("email") String email, @JsonProperty("name") String name) {
        this.email = email;
        this.name = name;
    }
}