package ch.bzz.KammerJeager.service;


import ch.bzz.KammerJeager.data.DataHandler;
import ch.bzz.KammerJeager.model.Kammerjaeger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
/**
 * @date 08.03.2021
 * @author Aaron Perez
 * @version 1.0
 */

@Path("Kammerjeager")
public class KammerjeagerService {

    /**
     *
     *gives response back
     * @return Response
     */

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listKammerjeagers () {
        Map<Integer, Kammerjaeger> kammerjeagerMap = DataHandler.getKammerjaegerMap();
        Response response = Response
                .status(200)
                .entity(kammerjeagerMap)
                .build();
        return response;
    }

    /**
     *
     * reads all the kammerjeagers
     * @param kammerjeagerID
     * @return Response
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response ReadKammerjeager(@QueryParam("kammerjeagerID") Integer kammerjeagerID){
        Kammerjaeger kammerjaeger = null;
        int httpStatus;
        try {
            kammerjaeger = DataHandler.readKammerjeager(kammerjeagerID);

            if(kammerjaeger.getKammerjeagerID() == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch(IllegalArgumentException e){
            httpStatus = 400;

        }
        Response response = Response
                .status(httpStatus)
                .entity(kammerjaeger)
                .build();
        return  response;
    }
}
