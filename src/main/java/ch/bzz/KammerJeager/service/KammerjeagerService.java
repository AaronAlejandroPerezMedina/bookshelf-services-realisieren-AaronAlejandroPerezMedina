package ch.bzz.KammerJeager.service;

import ch.bzz.KammerJeager.data.DataHandler;
import ch.bzz.KammerJeager.model.Kammerjeager;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Path("kammerjeager")
public class KammerjeagerService {

    /**
     *
     * gets the list of kammerjeager
     * @return Response
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listKammerjeagers (@CookieParam("userRole") String userRole) {
        Map<String, Kammerjeager> kammerjeagerMap = null;
        int httpStatus;
        if (userRole == null||userRole.equals("guest")) {
            httpStatus = 403;
        }
        else{
            kammerjeagerMap   = DataHandler.getKammerjeagerMap();
            httpStatus = 200;
        }



        Response response = Response
                .status(httpStatus)
                .entity(kammerjeagerMap)
                .build();
        return response;
    }

    /**
     *
     * reads all kammerjeagers
     * @param kammerjeagerUUID the UUID from the kammerjeager
     * @return Response
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response ReadKammerjeagers(
            @Pattern(regexp = "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}")
            @QueryParam("kammerjeagerUUID") String kammerjeagerUUID,
            @CookieParam("userRole") String userRole){
        Kammerjeager kammerjeager = null;
        int httpStatus;
        if (userRole == null||userRole.equals("guest")) {
            httpStatus = 403;

        } else {
            kammerjeager = DataHandler.readKammerjeager(kammerjeagerUUID);

            if (kammerjeager.getKammerjeagerID() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
            try {
                UUID.fromString(kammerjeagerUUID);
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }

        Response response = Response
                .status(httpStatus)
                .entity(kammerjeager)
                .build();
        return  response;
    }


    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKammerjeager(
            @Valid @BeanParam Kammerjeager kammerjeager,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {
            kammerjeager.setKammerjeagerID(UUID.randomUUID().toString());
            kammerjeager.setWerkzeuge(new ArrayList<>());


            DataHandler.insertKammerjeager(kammerjeager);
            httpStatus = 200;
        } else {
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateKammerjeager(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}")
            @FormParam("kammerjeagerUUID") String kammerjeagerUUID,
            @Valid @BeanParam Kammerjeager kammerjeager,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if(userRole != null && userRole.equals("admin")) {
            kammerjeager.setKammerjeagerID(kammerjeagerUUID);

            if (DataHandler.updateKammerjeager(kammerjeager)) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } else {
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }



    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKammerjeager(
            @Pattern(regexp = "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}")
            @FormParam("kammerjeagerUUID") String kammerjeagerUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        try {
            if(userRole != null && userRole.equals("admin")){

                UUID.fromString(kammerjeagerUUID);
                int errorcode = DataHandler.deleteKammerjeager(kammerjeagerUUID);
                if (errorcode == 0) httpStatus = 200;
                else if (errorcode == -1) httpStatus = 409;
                else httpStatus = 404;
            } else {
                httpStatus = 403;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
}