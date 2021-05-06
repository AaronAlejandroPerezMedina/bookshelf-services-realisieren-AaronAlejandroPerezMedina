package ch.bzz.KammerJeager.service;

import ch.bzz.KammerJeager.data.DataHandler;
import ch.bzz.KammerJeager.model.Werkzeug;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

@Path("werkzeug")
public class WerkzeugService {

    /**
     *
     * gets the list of werkzeuge
     * @return Response
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWerkzeuges (@CookieParam("userRole") String userRole) {
        Map<String, Werkzeug> werkzeugMap = null;
        int httpStatus;
        if (userRole == null||userRole.equals("guest")) {
            httpStatus = 403;

        } else {
            httpStatus =200;
            werkzeugMap = DataHandler.getWerkzeugMap();
        }
        Response response = Response
                .status(httpStatus)
                .entity(werkzeugMap)
                .build();
        return response;
    }

    /**
     *
     * reads all werzeuge
     * @param werkzeugUUID
     * @return Response
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response Readwerkzeugs(
            @Pattern(regexp = "[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}")
            @QueryParam("werkzeugUUID") String werkzeugUUID,
            @CookieParam("userRole") String userRole){
        Werkzeug werkzeug = null;
        int httpStatus;

        werkzeug = DataHandler.readWerkzeug(werkzeugUUID);

        if (userRole == null||userRole.equals("guest")) {
            httpStatus = 403;

        } else {
            httpStatus =200;
            if(werkzeug.getWerkzeugID() == null){
                httpStatus = 404;
            }
            try {
                UUID.fromString(werkzeugUUID);
            }catch (IllegalArgumentException argEx){
                httpStatus = 400;
            }
        }





        Response response = Response
                .status(httpStatus)
                .entity(werkzeug)
                .build();
        return  response;
    }



    /**
     * creates a new werkzeug
     *
     * @param werkzeug          a valid werkzeug
     * @param kammerjeagerUUID the uuid of the werkzeug
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createWerkzeug(
            @Valid @BeanParam Werkzeug werkzeug,
            @FormParam("kammerjeagerUUID") String kammerjeagerUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;


        try {
            if (userRole != null && userRole.equals("admin")) {
                UUID.fromString(kammerjeagerUUID);
                werkzeug.setWerkzeugID(UUID.randomUUID().toString());

                DataHandler.insertWerkzeug(werkzeug, kammerjeagerUUID);


            } else {
                httpStatus = 403;
            }
            Response response = Response
                    .status(httpStatus)
                    .entity("")
                    .build();
            return response;

        } catch (IllegalArgumentException e) {
            Response response = Response
                    .status(400)
                    .entity("")
                    .build();
            return response;

        }
    }


    /**
     * updates an existing werkzeug
     *
     * @param werkzeug          a valid werkzeug
     * @param kammerjeagerUUID the uuid of the kammerjeager
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateWerkzeug(
            @Valid @BeanParam Werkzeug werkzeug,
            @FormParam("werkzeugUUID") String werkzeugUUID,
            @FormParam("kammerjeagerUUID") String kammerjeagerUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole != null && userRole.equals("admin")) {
            Werkzeug oldWerkzeug = DataHandler.findWerkzeugByUUID(werkzeugUUID);
            if (oldWerkzeug != null) {
                oldWerkzeug.setColour(werkzeug.getColour());
                oldWerkzeug.setName(werkzeug.getName());
                oldWerkzeug.setEffectiveness(werkzeug.getEffectiveness());
                oldWerkzeug.setReleaseDate(werkzeug.getReleaseDate());
                oldWerkzeug.setType(werkzeug.getType());
                oldWerkzeug.setValues(werkzeug.getValues());
                if (DataHandler.updateWerkzeug(werkzeug,werkzeugUUID, kammerjeagerUUID)) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } else {
                httpStatus = 404;
            }
        }else {
            httpStatus =403;}


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing werkzeug identified by its uuid
     *
     * @param werkzeugUUID the unique key for the werkzeug
     * @return Response
     */
    @POST
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteWerkzeug(
            @QueryParam("werkzeugUUID") String werkzeugUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;


        if (userRole != null && userRole.equals("admin")) {
            if (DataHandler.deleteWerkzeug(werkzeugUUID)) {
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
}
