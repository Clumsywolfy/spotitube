package oose.dea.DAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("Spotitube")
public class Spotitube {

    @GET
    public String helloWorld() {
        return "Hello world!";
    }
}
