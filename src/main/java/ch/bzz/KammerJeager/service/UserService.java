package ch.bzz.KammerJeager.service;


import ch.bzz.KammerJeager.data.UserData;
import ch.bzz.KammerJeager.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * provides services for the user
 * <p>
 * M133: Kammerjeager
 *
 * @author Aaron Perez
 */
@Path("user")
public class UserService {


    /**
     * login a user with username/password
     *
     * @param username the username
     * @param password the password
     * @return Response-object with the userrole
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;

        User user = UserData.findUser(username, password);
        if (user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "username",     // key
                username,       // value
                "/",            // path
                "",             // domain
                "Login-Cookie", // comment
                600,            // max age in secods
                false           // secure
        );
        NewCookie cookie2 = new NewCookie(
                "userRole",     // key
                user.getRole(),       // value
                "/",            // path
                "",             // domain
                "Login-Cookie", // comment
                600,            // max age in secods
                false           // secure
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie, cookie2)
                .build();
        return response;
    }

    /**
     * logout current user
     *
     * @return Response object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {

        NewCookie cookie = new NewCookie(
                "username",
                "",
                "/",
                "",
                "Logoff-Cookie",
                1,
                false);

        NewCookie cookie2 = new NewCookie(
                "userRole",
                "",
                "/",
                "",
                "Logoff-Cookie",
                1,
                false);


        Response response = Response
                .status(200)
                .entity("")
                .build();
        return response;
    }
}