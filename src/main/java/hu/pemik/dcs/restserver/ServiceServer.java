package hu.pemik.dcs.restserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class ServiceServer {

    private final static String SERVICE_BASE_URI = "http://localhost:33333/rest/";

    private final HttpServer server;

    public ServiceServer() {
        final ResourceConfig rc = new ResourceConfig().packages("hu.pemik.dcs.restserver.endpoints");

        rc.register(new ApplicationBinder());
        rc.register(JacksonFeature.class);

        this.server = GrizzlyHttpServerFactory.createHttpServer(URI.create(SERVICE_BASE_URI), rc);

        System.out.println(String.format("\n\nServer started at: %s", SERVICE_BASE_URI));
    }

}
