package ch.bzz.KammerJeager.data;

import ch.bzz.KammerJeager.model.Kammerjeager;
import ch.bzz.KammerJeager.model.Werkzeug;
import ch.bzz.KammerJeager.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * M133: Kammerjeager
 *
 * @author Aaron Perez
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Kammerjeager> KammerjeagerMap;
    private static Map<String, Werkzeug> werkzeugMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        KammerjeagerMap = new HashMap<String, Kammerjeager>();
        werkzeugMap = new HashMap<String, Werkzeug>();
        readJSON();
    }

    /**
     * reads a single kammerjeager identified by its id
     * @param kammerjeagerID  the identifier
     * @return kammerjeager-object
     */
    public static Kammerjeager readKammerjeager(String kammerjeagerID) {
        Kammerjeager kammerjeager = new Kammerjeager();
        if (getKammerjeagerMap().containsKey(kammerjeagerID)) {
            kammerjeager = getKammerjeagerMap().get(kammerjeagerID);
        }
        return kammerjeager;
    }

    /**
     * saves a kammerjeager
     * @param kammerjeager  the kammerjeager to be saved
     */
    public static void saveKammerjeager(Kammerjeager kammerjeager) {
        getKammerjeagerMap().put(kammerjeager.getKammerjeagerID(), kammerjeager);
        writeJSON();
    }



    /**
     * inserts a new kammerjeager into the kammerjeagermap
     *
     * @param kammerjeager the kammerjeager to be saved
     */
    public static void insertKammerjeager(Kammerjeager kammerjeager) {
        if(kammerjeager.getWerkzeuge() != getWerkzeugMap().get(kammerjeager.getWerkzeuge())){
            getKammerjeagerMap().put(kammerjeager.getKammerjeagerID(), kammerjeager);
            writeJSON();
        }
    }




    /**
     * updates the kammerjeagermap
     * @param kammerjeager a kammerjeager object
     * @return entry
     */
    public static boolean updateKammerjeager(Kammerjeager kammerjeager) {
        boolean found = false;
        Kammerjeager entry = findKammerjeagerByUUID(kammerjeager.getKammerjeagerID());
        if (entry != null) {
            found = true;
            entry.setMaxWerzeuge(kammerjeager.getMaxWerzeuge());
            writeJSON();
        }
        return found;
    }

    /**
     * removes a book from the kammerjeagermap
     *
     * @param kammerjeagerUUID the uuid of the kammerjeager to be removed
     * @return success
     */
    public static int deleteKammerjeager(String kammerjeagerUUID) {
        int errorcode = 1;
        Kammerjeager kammerjeager = findKammerjeagerByUUID(kammerjeagerUUID);
        if (kammerjeager == null) errorcode = 1;
        else if (kammerjeager.getWerkzeuge() == null  ||
                kammerjeager.getWerkzeuge().isEmpty()) {
            getKammerjeagerMap().remove(kammerjeager);
            writeJSON();
            errorcode = 0;
        } else errorcode = -1;

        return errorcode;
    }

    /**
     * reads a single werkzeug identified by its id
     * @param werkzeugID  the identifier
     * @return werkzeug-object
     */
    public static Werkzeug readWerkzeug(String werkzeugID) {
        Werkzeug werkzeug = new Werkzeug();

        if (getWerkzeugMap().containsKey(werkzeugID)) {
            werkzeug = getWerkzeugMap().get(werkzeugID);
        }
        return werkzeug;
    }

    /**
     * saves a werkzeug
     * @param werkzeug  the werkzeug to be saved
     */
    public static void saveWerkzeug(Werkzeug werkzeug) {
        getWerkzeugMap().put(werkzeug.getWerkzeugID(), werkzeug);
        writeJSON();
    }



    /**
     * inserts a book to the publisher
     *
     * @param werkzeug
     * @param kammerjeagerUUID
     * @return success
     */
    public static boolean insertWerkzeug(Werkzeug werkzeug, String kammerjeagerUUID) {
        Kammerjeager kammerjeager = findKammerjeagerByUUID(kammerjeagerUUID);
        if (kammerjeager == null) {
            return false;
        } else {
            kammerjeager.getWerkzeuge().add(werkzeug);
            writeJSON();
            return true;
        }

    }

    public static boolean updateWerkzeug(Werkzeug werkzeug,String werkzeugUUID, String kammerjeagerUUID) {
        werkzeug.setWerkzeugID(werkzeugUUID);
        if (deleteWerkzeug(werkzeug.getWerkzeugID()))
            return insertWerkzeug(werkzeug, kammerjeagerUUID);
        else return false;
    }

    /**
     * deletes a publisher, if it has no books
     * @param werkzeugUUID
     * @return boolean
     */
    public static boolean deleteWerkzeug(String werkzeugUUID) {
        for (Kammerjeager kammerjeager : getKammerjeagerMap().values()) {
            if (kammerjeager.getWerkzeuge() != null) {
                for (Werkzeug werkzeug : kammerjeager.getWerkzeuge()) {
                    if (werkzeug.getWerkzeugID().equals(werkzeugUUID)) {
                        kammerjeager.getWerkzeuge().remove(werkzeug);
                        writeJSON();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * gets the kammerjeagerMap
     * @return the kammerjeagerMap
     */
    public static Map<String, Kammerjeager> getKammerjeagerMap() {
        return KammerjeagerMap;
    }

    /**
     * gets the werkzeugMap
     * @return the werkzeugMap
     */
    public static Map<String, Werkzeug> getWerkzeugMap() {
        return werkzeugMap;
    }
    public static void setWerkzeugMap(Map<String, Werkzeug> werkzeugMap) {
        DataHandler.werkzeugMap = werkzeugMap;
    }
    public static void setKammerjeagerMap(Map<String, Kammerjeager> kammerjeagerMap) {
        DataHandler.KammerjeagerMap = kammerjeagerMap;
    }

    public static Kammerjeager findKammerjeagerByWerkzeug(String werkzeugUUID) {

        for (Kammerjeager kammerjeager : getKammerjeagerMap().values()) {
            for (Werkzeug werkzeug : kammerjeager.getWerkzeuge()) {
                if (werkzeug.getWerkzeugID().equals(werkzeugUUID))
                    return kammerjeager;
            }
        }
        return null;
    }


    /**
     * finds a kammerjeager by the uuid
     *
     * @param kammerjeagerUUID the kammerjeagerUUID
     * @return the kammerjeager
     */
    public static Kammerjeager findKammerjeagerByUUID(String kammerjeagerUUID) {
        Kammerjeager kammerjeager = null;
        kammerjeager = getKammerjeagerMap().get(kammerjeagerUUID);
        return kammerjeager;
    }

    /**
     * gets a book by its uuid
     *
     * @param uuid the uuid of the book
     * @return book-object
     */
    public static Werkzeug findWerkzeugByUUID(String uuid) {
        for (Werkzeug werkzeug : getWerkzeugMap().values()) {
            if (werkzeug != null && werkzeug.getWerkzeugID().equals(uuid))
                return werkzeug;
        }

        return null;
    }



    /**
     * reads the kammerjeagers and werkzeuge
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("kammerjeagerJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Kammerjeager[] kammerjeagers = objectMapper.readValue(jsonData, Kammerjeager[].class);
            for (Kammerjeager kammerjeager: kammerjeagers) {
                List<Werkzeug> werkzeuge = new ArrayList<>();
                if(kammerjeager.getWerkzeuge() != null){
                    for(Werkzeug werkzeug : kammerjeager.getWerkzeuge()){
                        String werkzeugID = werkzeug.getWerkzeugID();
                        if (getWerkzeugMap().containsKey(werkzeugID)) {
                            werkzeug = getWerkzeugMap().get(werkzeugID);
                        } else {
                            getWerkzeugMap().put(werkzeugID, werkzeug);
                        }
                        werkzeuge.add(werkzeug);

                    }}

                kammerjeager.setWerkzeuge(werkzeuge);
                getKammerjeagerMap().put(kammerjeager.getKammerjeagerID(), kammerjeager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the kammerjeagers and werkzeuge
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String kammerjeagerPath = Config.getProperty("kammerjeagerJSON");
        try {
            fileOutputStream = new FileOutputStream(kammerjeagerPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, getKammerjeagerMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}