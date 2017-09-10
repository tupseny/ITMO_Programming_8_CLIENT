package com.dartin.project.util;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;

import java.io.*;
import java.util.*;

/**
 * @author root
 *         on 08/09/17.
 */

public class Preferences {

    private Properties properties = new Properties();
    private String filename;
    public static final String FILE_ENDING = ".preferences";

    public Preferences(String filename) throws IOException {

        this.filename = "./src/" + parseFilepath(filename);
        this.filename += FILE_ENDING;

        InputStream inputStream = new FileInputStream(this.filename);
        properties.load(inputStream);
        inputStream.close();
    }

    public void updateFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(filename);
        properties.list(pw);
        pw.close();
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public void setString(String key, String value) {
        properties.setProperty(key, value);
    }

    private String parseFilepath(String filepath) {
        return filepath.replace(".", "/");
    }
}