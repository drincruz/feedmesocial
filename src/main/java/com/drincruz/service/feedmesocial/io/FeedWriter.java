package com.drincruz.service.feedmesocial.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.MappedByteBuffer;

/**
 * Write json files for the feed
 *
 */
public class FeedWriter {
    // Private
    private String filename;
    private String file;

    /**
     * Constructor
     *
     * @param String f
     */
    public FeedWriter(String f) {
        filename = f;
        if ( ((boolean) (new File("/usr/local/tmp/")).exists()) ) {
            file = "/usr/local/tmp/"+filename+".json";
        }
        else if ( ((boolean) (new File("/tmp/")).exists()) ) {
            file = "/tmp/"+filename+".json";
        }
        else {
            file = filename+".json";
        }
    }

    /**
     * Constructor
     *
     * @param String f
     * @param String path
     */
    public FeedWriter(String f, String path) {
        filename = f;
        if ( ((boolean) (new File(path)).exists()) ) {
            file = addTrailingSlash(path)+filename+".json";
        }
        else if ( ((boolean) (new File("/usr/local/tmp/")).exists()) ) {
            file = "/usr/local/tmp/"+filename+".json";
        }
        else if ( ((boolean) (new File("/tmp/")).exists()) ) {
            file = "/tmp/"+filename+".json";
        }
        else {
            file = filename+".json";
        }
    }

    /**
     * Adds a trailing slash to String f
     *
     * @param String f
     * @return String f
     */
    private String addTrailingSlash(String f) {
        if (f.endsWith("/")) {
            return f;
        }
        else {
            return f+"/";
        }
    }

    /**
     * Reads data from filename
     *
     * @return String
     */
    public String readFile() throws IOException {
        FileInputStream stream = new FileInputStream(new File(file));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }

    /**
     * Writes data to filename
     *
     * @param String data
     * @return void
     */
    public void writeFile(String data) {
        try {
            FileWriter fs = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fs);
            out.write(data);
            out.close();
        }
        catch (Exception e) {
            System.err.printf("[Error] %s\n", e.getMessage());
        }
    }

    /**
     * Parses String input and casts to JSONObject
     *
     * @param String input
     * @return JSONObject json
     */
    public JSONObject toJsonObject(String input) {
        // parse the JSON string input
        Object o = JSONValue.parse(input);
        // typecast the object to JSONObject
        JSONObject json = (JSONObject) o;
        return json;
    }

    /**
     * Parses String input and casts to JSONArray
     *
     * @param String input
     * @return JSONObject json
     */
    public JSONArray toJsonArray(String input) {
        // parse the JSON string input
        Object o = JSONValue.parse(input);
        // typecast the object to JSONArray
        JSONArray json = (JSONArray) o;
        return json;
    }

    /**
     * Write an empty JSONArray
     *
     * @return void
     */
    public void initJsonFile() {
        JSONArray j = new JSONArray();
        writeFile(j.toJSONString());
    }

    /**
     * Check if file exists
     *
     * @return boolean 
     */
    public boolean fileExists() {
        if ( ((boolean) (new File(file)).exists()) ) {
            return true;
        }
        else {
            return false;
        }
    }
}
