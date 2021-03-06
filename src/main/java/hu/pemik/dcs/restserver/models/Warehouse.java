package hu.pemik.dcs.restserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import hu.pemik.dcs.restserver.database.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse extends Model {

    public String address;

    public int capacity;

    @JsonIgnore
    public List<Integer> storage = new ArrayList<>();

    @JsonIgnore
    public List<Integer> used = new ArrayList<>();

    @JsonIgnore
    public List<Integer> free = new ArrayList<>();

    public Warehouse(String address, int capacity) {
        this.id = 1; // This class acts as a singleton
        this.address = address;
        this.capacity = capacity;
        this.storage = new ArrayList<>(Collections.nCopies(capacity, 0));
        this.used = new ArrayList<>();

        for (int i = 1; i <= this.capacity; i++) {
            this.free.add(i);
        }
    }

    public void storeProduct(Product product) throws Exception {
        if (this.free.size() == 0) {
            throw new Exception("Warehouse is full");
        }

        // Get a free locationId and assign it to the product
        // Then add it to the used locations list
        product.locationId = this.free.remove(0);
        this.used.add(product.locationId);

        this.storage.set((product.locationId - 1), product.id);
    }

    public void removeProduct(Product product) {
        // Remove product location from the used list
        // Add it to the free list and assign 0 to product location

        this.used.remove(this.used.indexOf(product.locationId));
        this.free.add(product.locationId);
        this.storage.set((product.locationId - 1), 0);
        // product.locationId = 0;
    }

    @JsonInclude
    public int getFree() {
        return this.free.size();
    }


    @JsonInclude
    public int getUsed() {
        return this.used.size();
    }

    @Override
    public String toString() {
        return "Warehouse [ id=" + id + ", address='" + address + "', capacity=" + capacity + "]";
    }

}
