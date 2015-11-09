package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Role;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("demouser")
public class User {

    UserFacade userFacade = new UserFacade();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String createUser(String jsonUser) {

        JsonObject userAsJson = new JsonParser().parse(jsonUser).getAsJsonObject();
        String username = userAsJson.get("username").getAsString();
        String password = userAsJson.get("password").getAsString();

        List<Role> rolesList = new ArrayList();

        rolesList.add(new Role("User"));

        entity.User user = new entity.User(username, password, rolesList);

        userFacade.createUserInDataBase(user);

        return "{\"message\": \"Created\"}";
    }
    
 

}
