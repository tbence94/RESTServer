package hu.pemik.dcs.restserver.endpoints;

import hu.pemik.dcs.restserver.Console;
import hu.pemik.dcs.restserver.database.Database;
import hu.pemik.dcs.restserver.database.Repository;
import hu.pemik.dcs.restserver.models.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("products")
public class ProductEndpoints {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@Context SecurityContext sc) throws Exception {
        Database db = Database.query();

        User user = db.users.where("email", sc.getUserPrincipal().getName()).get();
        Repository<Product> productList = db.products;

        if (user.getRole().equals(User.ROLE_CUSTOMER)) {
            productList = db.products.where("customerId", user.id);
        }

        return Response.ok(productList).build();
    }

    @POST
    @Path("product")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response store(@Context SecurityContext sc, Product product) {
        Database db = Database.query();
        Warehouse warehouse = db.warehouse;

        try {
            Customer customer = (Customer) db.users.find(product.customerId);

            if (customer.capacity < customer.getUsedCapacity() + 1) {
                return Response.notModified().build();
            }

            db.products.insert(product);
            warehouse.storeProduct(product);

            Log.create(sc, "Stored product: " + product);

            db.save();

            Console.info("Stored product: " + product.toString());
            db.dump();
        } catch (Exception e) {
            throw new BadRequestException("Couldn't save product.");
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destory(@Context SecurityContext sc, @PathParam("id") String id) {
        Database db = Database.query();
        Warehouse warehouse = db.warehouse;
        int productId = Integer.parseInt(id);

        try {
            Product product = db.products.findOrFail(productId);

            warehouse.removeProduct(product);
            db.products.delete(product.id);

            Log.create(sc, "Removed product: " + product);

            db.save();

            Console.info("Removed product: " + product.toString());
            db.dump();
        } catch (Exception e) {
            throw new BadRequestException("Couldn't remove product.");
        }

        return Response.ok().build();
    }


}