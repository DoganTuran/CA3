/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Ny
 */
@Path("company")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String getJson(String content) {
        
        JsonObject inJSON = new JsonParser().parse(content).getAsJsonObject();

        String search = inJSON.get("search").getAsString();
        String option = inJSON.get("option").getAsString();
        String country = inJSON.get("country").getAsString();

        String returnValue = "";

        try {
            String urlString = "http://cvrapi.dk/api" + "?" + option + "=" + search + "&country=" + country;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("FAIL code 200");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String data;
            while ((data = br.readLine()) != null) {
                
                returnValue += data;
                
            }

        } catch (MalformedURLException e) {

        } catch (IOException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnValue;

    }

}
