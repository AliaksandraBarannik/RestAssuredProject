package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileReader {

    public static String getProperty(String file, String propertyName) {
        return getProperties(file).get(propertyName);
    }

    public static Map<String, String> getProperties(String file) {
        Properties prop = new Properties();
        Map<String, String> properties = new HashMap<>();
        InputStream stream = null;
        try {
            stream = FileReader.class.getClassLoader().getResourceAsStream(file);
            if (stream != null) {
                prop.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
                Enumeration<Object> keyValues = prop.keys();

                while (keyValues.hasMoreElements()) {
                    String key = (String) keyValues.nextElement();
                    String value = prop.getProperty(key);
                    properties.put(key, System.getProperty(key, value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static <T> T serializeJsonFromResource(Class<T> tClass, String jsonRelativePath) {
        File jsonPath = new File(FileReader.class.getClassLoader().getResource(jsonRelativePath).getFile());
        return serializeJson(tClass, jsonPath);
    }

    private static <T> T serializeJson(Class<T> tClass, File json) {
        T jsonClassObject;
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonClassObject = mapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonClassObject;
    }
}
