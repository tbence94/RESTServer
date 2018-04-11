package hu.pemik.dcs.restserver.models;

import hu.pemik.dcs.restserver.Console;
import hu.pemik.dcs.restserver.database.Database;
import hu.pemik.dcs.restserver.database.Model;

import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;

public class Log extends Model {

    String user;

    String message;

    Timestamp date;

    public Log() {
        date = new Timestamp(System.currentTimeMillis());
    }


    public static void create(SecurityContext sc, String message) {
        try {
            Log log = new Log();
            log.setUser(sc.getUserPrincipal().getName());
            log.setMessage(message);

            Database.query().logs.insert(log);

            Database.query().save();
        } catch (Exception e) {
            Console.handleException(e);
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Log [ id=" + id + ", user='" + user + "', message='" + Console.highlightString(message) + "', date='" + date + "']";
    }

}
