package hu.pemik.dcs.restserver.models;

import hu.pemik.dcs.restserver.database.Model;

public class Product extends Model {

    public String name;

    public String description;

    public int quantity;

    public boolean cooled;

    public int customerId;

    public int locationId;

    public Product() {
    }

    public Product(String name, String description, int quantity, boolean cooled, int customerId, int locationId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cooled = cooled;
        this.customerId = customerId;
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCooled() {
        return cooled;
    }

    public void setCooled(boolean cooled) {
        this.cooled = cooled;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Product [ id=" + id + ", name='" + name + "', description='" + description + "', quantity=" + quantity + " cooled=" + cooled + ", customerId=" + customerId + ", locationId=" + locationId + " ]";
    }

}
