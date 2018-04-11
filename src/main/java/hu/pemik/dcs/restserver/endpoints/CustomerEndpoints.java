package hu.pemik.dcs.restserver.endpoints;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.pemik.dcs.restserver.database.Database;
import hu.pemik.dcs.restserver.models.Customer;
import hu.pemik.dcs.restserver.models.Log;
import hu.pemik.dcs.restserver.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("customers")
public class CustomerEndpoints {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@Context SecurityContext sc) {
        return Response.ok(Database.query().users.where("role", "customer")).build();
    }

    @PUT
    @Path("customer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response store(@Context SecurityContext sc, @PathParam("id") String id, ContractUpdate contractUpdate) {
        Database db = Database.query();

        Customer customer = (Customer) db.users.find(Integer.valueOf(id));

        int bookedCapacity = db.users.where("role", User.ROLE_CUSTOMER).sum("capacity");
        int neededCapacity = bookedCapacity - customer.capacity + contractUpdate.capacity;
        int oldCapacity = customer.capacity;

        if (db.warehouse.capacity < neededCapacity) {
            return Response.notModified().build();
        }

        customer.setCapacity(contractUpdate.capacity);

        Log.create(sc, "Change customer's capacity from: " + oldCapacity + " to:" + customer.capacity);
        db.save();

        return Response.ok().build();
    }

}

class ContractUpdate {

    int capacity;

    @JsonCreator
    public ContractUpdate(@JsonProperty("capacity") int capacity) {
        this.capacity = capacity;
    }
}