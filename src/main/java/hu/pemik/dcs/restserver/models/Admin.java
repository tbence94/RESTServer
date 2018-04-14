package hu.pemik.dcs.restserver.models;

public class Admin extends Worker {

    public Admin() {
    }

    public Admin(String name, String email) {
        super(name, email);
        this.setRole(User.ROLE_ADMIN);
    }

}
