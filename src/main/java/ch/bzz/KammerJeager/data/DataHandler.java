package ch.bzz.KammerJeager.data;

import ch.bzz.KammerJeager.model.Kammerjaeger;
import ch.bzz.KammerJeager.model.Werkzeuge;
import ch.bzz.KammerJeager.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 08.03.2021
 * @author Aaron Perez
 * @version 1.0
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<Integer, Kammerjaeger> KammerjaegerMap;
    private static Map<Integer, Werkzeuge> WerkzeugeMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        KammerjaegerMap = new HashMap<>();
        WerkzeugeMap = new HashMap<>();
        readJSON();
    }

    /**
     * reads single kammerjaeger identified by its id
     * @param KammerjeagerID  the identifier
     * @return kammerjaeger-object
     */
    public static Kammerjaeger readKammerjeager(Integer KammerjeagerID) {
        Kammerjaeger kammerjaeger = new Kammerjaeger();
        if (getKammerjaegerMap().containsKey(KammerjeagerID)) {
            kammerjaeger = getKammerjaegerMap().get(KammerjeagerID);
        }
        return kammerjaeger;
    }

    /**
     * saves kammerjaeger
     * @param kammerjaeger the kammerjaeger to be saved
     */
    public static void saveKammerjaeger(Kammerjaeger kammerjaeger) {
        getKammerjaegerMap().put(kammerjaeger.getKammerjeagerID(), kammerjaeger);
        writeJSON();
    }

    /**
     * reads single werkzeug identified by its id
     * @param WerkzeugID  the identifier
     * @return werkzeug-object
     */
    public static Werkzeuge readWerkzeug(String WerkzeugID) {
        Werkzeuge werkzeuge = new Werkzeuge();

        if (getWerkzeugMap().containsKey(WerkzeugID)) {
            werkzeuge = getWerkzeugMap().get(WerkzeugID);
        }
        return werkzeuge;
    }

    /**
     * saves werkzeug
     * @param werkzeuge  the werkzeug to be saved
     */
    public static void saveWerkzeug(Werkzeuge werkzeuge) {
        getWerkzeugMap().put(werkzeuge.getWerkzeugID(), werkzeuge);
        writeJSON();
    }

    /**
     * gets the kammerjaegerMap
     * @return the kammerjaegerMap
     */
    public static Map<Integer, Kammerjaeger> getKammerjaegerMap() {
        return KammerjaegerMap;
    }

    /**
     * gets the werkzeugMap
     * @return the werkzeugMap
     */
    public static Map<Integer, Werkzeuge> getWerkzeugMap() { return WerkzeugeMap;}
    public static void setWerkzeugMap(Map<Integer, Werkzeuge> werkzeugMap) { DataHandler.WerkzeugeMap= werkzeugMap; }
    public static void setKammerjaegerMap(Map<Integer, Kammerjaeger> kammerjaegerMap) { DataHandler.KammerjaegerMap = kammerjaegerMap; }

    /**
     * reads the kammerjaegers and werkzeuges
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("kammerjaegerJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Kammerjaeger[] kammerjaegers = objectMapper.readValue(jsonData, Kammerjaeger[].class);
            for (Kammerjaeger kammerjaeger : kammerjaegers) {
                Collection<Werkzeuge> werkzeuges = null;
                for(Werkzeuge werkzeuge : kammerjaeger.getWerkzeuge()){
                    int werkzeugID = werkzeuge.getWerkzeugID();
                    if (getWerkzeugMap().containsKey(werkzeugID)) {
                        werkzeuge = getWerkzeugMap().get(werkzeugID);
                    } else {
                        getWerkzeugMap().put(werkzeugID, werkzeuge);
                    }
                    werkzeuges.add(werkzeuge);

                }

                kammerjaeger.setWerkzeuge(werkzeuges);
                getKammerjaegerMap().put(kammerjaeger.getKammerjeagerID(), kammerjaeger);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes the kammerjaegers and werkzeuge
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String kammerjaegerPath = Config.getProperty("kammerjaegerJSON");
        try {
            fileOutputStream = new FileOutputStream(kammerjaegerPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getKammerjaegerMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

