package utils;

public class Configuration {
    private static final String CONFIG_FILE_PATH = "config.properties";

    private Configuration() {
    }

    public static String getPassword() {
        return FileReader.getProperty(CONFIG_FILE_PATH, "password");
    }

    public static String getUser() {
        return FileReader.getProperty(CONFIG_FILE_PATH, "user");
    }
}
