package ch.bzz.bookshelf.data;

import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Publisher;
import ch.bzz.bookshelf.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Book> bookMap;
    private static Map<String, Publisher> publisherMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        bookMap = new HashMap<>();
        publisherMap = new HashMap<>();
        readJSON();
    }

    /**
     * reads a single book identified by its uuid
     * @param bookUUID  the identifier
     * @return book-object
     */
    public static Book readBook(String bookUUID) {
        Book book = new Book();
        if (getBookMap().containsKey(bookUUID)) {
            book = getBookMap().get(bookUUID);
        }
        return book;
    }

    /**
     * saves a book
     * @param book  the book to be saved
     */
    public static void saveBook(Book book) {
        getBookMap().put(book.getBookUUID(), book);
        writeJSON();
    }

    /**
     * reads a single publisher identified by its uuid
     * @param publisherUUID  the identifier
     * @return publisher-object
     */
    public static Publisher readPublisher(String publisherUUID) {
        Publisher publisher = new Publisher();
        if (getPublisherMap().containsKey(publisherUUID)) {
            publisher = getPublisherMap().get(publisherUUID);
        }
        return publisher;
    }

    /**
     * saves a publisher
     * @param publisher  the publisher to be saved
     */
    public static void savePublisher(Publisher publisher) {
        getPublisherMap().put(publisher.getPublisherUUID(), publisher);
        writeJSON();
    }

    /**
     * gets the bookMap
     * @return the bookMap
     */
    public static Map<String, Book> getBookMap() {
        return bookMap;
    }

    /**
     * gets the publisherMap
     * @return the publisherMap
     */
    public static Map<String, Publisher> getPublisherMap() {
        return publisherMap;
    }

    public static void setPublisherMap(Map<String, Publisher> publisherMap) {
        DataHandler.publisherMap = publisherMap;
    }

    /**
     * reads the books and publishers
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("bookJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Book[] books = objectMapper.readValue(jsonData, Book[].class);
            for (Book book : books) {
                String publisherUUID = book.getPublisher().getPublisherUUID();
                Publisher publisher;
                if (getPublisherMap().containsKey(publisherUUID)) {
                    publisher = getPublisherMap().get(publisherUUID);
                } else {
                    publisher = book.getPublisher();
                    getPublisherMap().put(publisherUUID, publisher);
                }
                book.setPublisher(publisher);
                getBookMap().put(book.getBookUUID(), book);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the books and publishers
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String bookPath = Config.getProperty("bookJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getBookMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
