package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * @author Aaron Perez
 */
@Path("book")
public class BookService {
    /*
    @return Response
     */
    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        Map<String, Book> bookMap = DataHandler.getBookMap();

        Response response = Response
                .status(200)
                .entity(bookMap)
                .build();
        return response;
    }

    /**
     *
     * @param bookUUID
     * @return
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String bookUUID
    ){
        Book book = null;
        int httpStatus;

        try {
            UUID.fromString(bookUUID);
            book = DataHandler.readBook(bookUUID);
            if (book.getTitle() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argEx){
            httpStatus = 400;
        }
        Response response = Response
                .status(200)
                .entity(book)
                .build();
        return response;
    }

}
